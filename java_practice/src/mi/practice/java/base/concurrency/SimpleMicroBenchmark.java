package mi.practice.java.base.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 性能是差不多的?
 * 使用这样的测试来测试性能的是要注意,
 * 很有可能编译器已经帮你完成了很多事情了,
 * 做了相应的编译优化所以是看不到真正的代码是怎么样的.
 * 如果需要做一个性能测试的时候, 还是需要让程序变得复杂一些.
 * 1. 首先需要多任务
 * 2. 需要读和写(不然很容易编译器会帮你做优化)
 * 3. 计算需要变得相对复杂, 不然编译器会帮你预先计算好
 */
abstract class Incrementable {
	protected long counter = 0;

	public abstract void increment();
}

/**
 * 使用synchronized来完成自增
 */
class SynchronizingTest extends Incrementable {
	@Override
	public synchronized void increment() {
		++counter;
	}
}

/**
 * 使用锁来完成自增
 */
class LockingTest extends Incrementable {
	private Lock lock = new ReentrantLock();

	@Override
	public void increment() {
		lock.lock();
		try {
			++counter;
		} finally {
			lock.unlock();
		}
	}
}

public class SimpleMicroBenchmark {
	static long test(Incrementable incr) {
		long start = System.nanoTime();
		for (int i = 0; i < 10000000L; i++) {
			incr.increment();
		}
		return System.nanoTime() - start;
	}

	public static void main(String[] args) {
		long synchTime = test(new SynchronizingTest());
		long lockTime = test(new LockingTest());
		System.out.printf("Synchronized: %1$10d\n", synchTime);
		System.out.printf("Lock:       : %1$10d\n", lockTime);
		System.out.printf("Lock/synchronized = %1$.3f", (double) lockTime
				/ (double) synchTime);
	}
}
