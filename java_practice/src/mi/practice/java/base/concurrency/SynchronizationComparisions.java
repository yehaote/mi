package mi.practice.java.base.concurrency;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程的程序, 最好永远保持一些怀疑的态度.
 * 说不定它什么时候会给你惊喜.
 * 
 * 一般情况下三种速度好像差不多,
 * 不过当数量达到一定的阀值的时候, 可以发现Lock会快一些,
 * 注意Atomic比较适合用于单简单变量的情况.
 * 
 * 一般情况下还是可以使用synchronized, 因为:
 * 1. 很多时候锁的开销相对与整个程序来说可能并不是那么重要, 
 *    很有可能是其他的地方出现问题
 * 2. 使用synchronized在代码可读性方面可能会比较好一点.
 */
abstract class Accumulator {
	public static long cycles = 50000L;
	// Number of Modifiers and Readers during each test:
	private static final int N = 4;
	public static ExecutorService exec = Executors.newFixedThreadPool(N * 2);
	private static CyclicBarrier barrier = new CyclicBarrier(N * 2 + 1);
	protected volatile int index = 0;
	protected volatile long value = 0;
	protected long duration = 0;
	protected String id = "error";
	protected final static int SIZE = 100000;
	protected static int[] preLoaded = new int[SIZE];
	static { // Load the array of random numbers;
		Random rand = new Random(47);
		for (int i = 0; i < SIZE; i++) {
			preLoaded[i] = rand.nextInt();
		}
	}

	public abstract void accumulate();

	public abstract long read();

	private class Modifier implements Runnable {
		@Override
		public void run() {
			for (long i = 0; i < cycles; i++) {
				accumulate();
			}
			try {
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class Reader implements Runnable {
		@SuppressWarnings("unused")
		private volatile long value;

		@Override
		public void run() {
			for (long i = 0; i < cycles; i++) {
				value = read();
			}
			try {
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}

	public void timedTest() {
		long start = System.nanoTime();
		for (int i = 0; i < N; i++) {
			exec.execute(new Modifier());
			exec.execute(new Reader());
		}
		try {
			barrier.await();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		duration = System.nanoTime() - start;
		System.out.printf("%-13s: %13d\n", id, duration);
	}

	public void report(Accumulator acc2) {
		{
			System.out.printf("%-22s: %.2f\n", this.id + "/" + acc2.id,
					(double) this.duration / (double) acc2.duration);
		}
	}
}

class SynchronizedTest extends Accumulator {
	{
		id = "synch";
	}

	@Override
	public synchronized void accumulate() {
		value += preLoaded[index++];
		if (index >= SIZE) {
			index = 0;
		}
	}

	@Override
	public long read() {
		return value;
	}

}

class LockTest extends Accumulator {
	{
		id = "Lock";
	}
	private Lock lock = new ReentrantLock();

	@Override
	public void accumulate() {
		lock.lock();
		try {
			value += preLoaded[index++];
			if (index >= SIZE) {
				index = 0;
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public long read() {
		lock.lock();
		try {
			return value;
		} finally {
			lock.unlock();
		}
	}

}

class AtomicTest extends Accumulator {
	{
		id = "Atomic";
	}
	private AtomicInteger index = new AtomicInteger(0); // 这个不是覆盖, 只是多了一个而已
	private AtomicLong value = new AtomicLong(0);

	// Relying on more than one Atomic at a time doesn't work, so we still have
	// to synchronize.
	// But it gives a performance indicator:
	@Override
	public synchronized void accumulate() {
		int i;
		i = index.getAndIncrement();
		value.getAndAdd(preLoaded[i]);
		if (++i >= SIZE) {
			index.set(0);
		}
	}

	@Override
	public synchronized long read() {
		return value.get();
	}

	@Override
	public void report(Accumulator acc2) {
		System.out.printf("%-22s: %.2f\n", "synch/(Atomic-synch)",
				(double) acc2.duration
						/ ((double) this.duration - (double) acc2.duration));
	}

}

public class SynchronizationComparisions {
	static SynchronizedTest synch = new SynchronizedTest();
	static LockTest lock = new LockTest();
	static AtomicTest atomic = new AtomicTest();

	static void test() {
		System.out.println("=================================");
		System.out.printf("%-12s : %13d\n", "Cycles", Accumulator.cycles);
		synch.timedTest();
		lock.timedTest();
		atomic.timedTest();
		synch.report(lock);
		atomic.report(synch);
	}

	public static void main(String[] args) {
		int iterations = 20;// Default
		if (args.length > 0) {
			iterations = new Integer(args[0]);
		}
		// The firest time fills the thread pool:
		System.out.println("Warmup");
		synch.timedTest();
		// Now the initial test doesn't include the cost of starting the thread
		// for the first time.
		// Product multiple data points:
		for (int i = 0; i < iterations; i++) {
			test();
			Accumulator.cycles *= 2;
		}
		Accumulator.exec.shutdown();
	}
}
