package mi.practice.java.base.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.*;

/**
 * DelayQueue里面的元素根据delay的时间排序,
 * 如果等待的时间没有到的话, 从队列中是获取不到元素的,
 * 具体时间由getDelay()提供.
 * null是不能被添加到DelayQueue中的.
 * DelayQueue也算priorityQueue(优先级队列)的一种变种.
 */
class DelayedTask implements Runnable, Delayed {
	private static int counter = 0;
	private final int id = counter++;
	private final int delta;
	private final long trigger;
	protected static List<DelayedTask> sequence = new ArrayList<DelayedTask>();

	public DelayedTask(int delayInMilliseconds) {
		delta = delayInMilliseconds;
		trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);
		sequence.add(this);
	}

	@Override
	public int compareTo(Delayed arg) {
		DelayedTask that = (DelayedTask) arg;
		if (trigger < that.trigger) {
			return -1;
		}
		if (trigger > that.trigger) {
			return 1;
		}
		return 0;
	}

	@Override
	// 重要, 返回一个delay的时间
	public long getDelay(TimeUnit unit) {
		// 从nanoSecond转换到对应的单位
		return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
	}

	@Override
	public void run() {
		System.out.print(this + " ");
	}

	@Override
	public String toString() {
		return String.format("[%1$-4d]", delta) + " Task " + id;
	}

	public String summary() {
		return "(" + id + ": " + delta + ")";
	}

	public static class EndSentinel extends DelayedTask {
		private ExecutorService exec;

		public EndSentinel(int delay, ExecutorService exec) {
			super(delay);
			this.exec = exec;
		}

		@Override
		public void run() {
			for (DelayedTask pt : sequence) {
				System.out.print(pt.summary() + " ");
			}
			System.out.println();
			System.out.println(this + " Calling shutdownNow()");
			exec.shutdownNow();
		}
	}

}

class DelayedTaskConsumer implements Runnable {
	private DelayQueue<DelayedTask> q;

	public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
		this.q = q;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 从队列里取出任务, 并在当前线程执行
				q.take().run();
			}
		} catch (InterruptedException e) {
			// 可以接受的结束方式
		}
		System.out.println("Finished DelayedTaskConsumer");
	}

}

public class DelayQueueDemo {
	public static void main(String[] args) {
		Random rand = new Random(47);
		ExecutorService exec = Executors.newCachedThreadPool();
		DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();
		// 填充DelayQueue, 每个任务分配一个随机的delay时间
		for (int i = 0; i < 20; i++) {
			queue.put(new DelayedTask(rand.nextInt(5000)));
		}
		// 增加一个结束的任务
		queue.add(new DelayedTask.EndSentinel(5000, exec));
		exec.execute(new DelayedTaskConsumer(queue));
	}
}
