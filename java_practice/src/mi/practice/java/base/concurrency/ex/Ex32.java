package mi.practice.java.base.concurrency.ex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static mi.practice.java.base.util.Print.*;

class Ex32Count {
	private int count = 0;
	private Random rand = new Random(47);

	// 这里没有同步的话会导致线程间的异常
	public synchronized int increment() {
		int temp = count;
		if (rand.nextBoolean()) {
			Thread.yield();
		}
		return (count = ++temp);
	}

	public synchronized int value() {
		return count;
	}
}

class Ex32Entrance implements Runnable {
	// 共有计数器
	private static Ex32Count count = new Ex32Count();
	// 引用集合
	private static List<Ex32Entrance> entrances = new ArrayList<Ex32Entrance>();
	private static volatile boolean canceled = false;
	private final CountDownLatch latch;

	public static void cancel() {
		canceled = true;
	}

	public static int getTotalCount() {
		return count.value();
	}

	public static int sumEntracnes() {
		int sum = 0;
		for (Ex32Entrance entrance : entrances) {
			sum += entrance.getValue();
		}
		return sum;
	}

	// 私有计数器
	private int number = 0;
	private final int id;

	public Ex32Entrance(int id, CountDownLatch latch) {
		this.id = id;
		entrances.add(this);
		this.latch=latch;
	}

	@Override
	public void run() {
		while (!canceled) {
			synchronized (this) {
				++number;
			}
			print(this + " Total: " + count.increment());
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				print("sleep interrupted");
			}
		}
		print("Stopping " + this);
		latch.countDown();
	}

	public synchronized int getValue() {
		return number;
	}

	@Override
	public String toString() {
		return "Entrance " + id + ": " + getValue();
	}
}

public class Ex32 {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(5);
		for (int i = 0; i < 5; i++) {
			exec.execute(new Ex32Entrance(i, latch));
		}
		TimeUnit.SECONDS.sleep(3);
		Ex32Entrance.cancel();
		exec.shutdown();
		latch.await();
		// never need again
		if (!exec.awaitTermination(1, TimeUnit.MILLISECONDS)) {
			print("Some task were not terminated!");
		}
		print("Total: " + Ex32Entrance.getTotalCount());
		print("sum of Entrances: " + Ex32Entrance.sumEntracnes());

	}
}
