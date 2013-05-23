package mi.practice.java.base.concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * 计数锁, 
 * 调用CountDownLatch.await()会等待直到count变成0,
 * countDown()会让count减1.
 * 最适合当多个线程去执行不同的任务,
 * 当他们的执行完的时候再接下去做什么这样的情况.
 * 
 * Java的很多方法你在使用的时候或许是不清楚他是否是线程安全的,
 * 除了在网上查询相关的资料以外.
 * 比如Random.nextInt()
 */
class TaskPortion implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private static Random rand = new Random(47);
	private final CountDownLatch latch;

	public TaskPortion(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			doWork();
			// 完成一个任务, 计数锁减1
			latch.countDown();
		} catch (InterruptedException e) {
			// acceptable way to exit
		}
	}

	public void doWork() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
		System.out.println(this + "completed");
	}

	@Override
	public String toString() {
		return String.format("%1$-3d ", id);
	}

}

class WaitingTask implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final CountDownLatch latch;

	public WaitingTask(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			latch.await();
			System.out.println("Latch barrier passed for " + this);
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
	}

	@Override
	public String toString() {
		return String.format("WaitingTask %1$-3d ", id);
	}

}

public class CountDownLatchDemo {
	static final int SIZE = 100;

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		// 所有的任务使用相同的CountDownLatch
		CountDownLatch latch = new CountDownLatch(SIZE);
		// 启动等待的进程
		for (int i = 0; i < 10; i++) {
			exec.execute(new WaitingTask(latch));
		}
		// 启动任务的部分, 使CountDownLatch递减
		for (int i = 0; i < SIZE; i++) {
			exec.execute(new TaskPortion(latch));
		}
		System.out.println("Launched all tasks");
		exec.shutdown();
	}
}
