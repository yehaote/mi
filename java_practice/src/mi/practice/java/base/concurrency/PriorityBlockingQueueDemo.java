package mi.practice.java.base.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * PriorityQueue
 * PriorityBlockingQueue是Blocking版本的, 不需要考虑数据的synchronized,
 * 已经帮你实现了.
 * PriorityBlockingQueue对调用Comparable接口的方法去比较优先级, 
 * 越大的越先执行.
 */
class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
	private Random rand = new Random(47);
	private static int counter = 0;
	private final int id = counter++;
	private final int priority;
	protected static List<PrioritizedTask> sequence = new ArrayList<PrioritizedTask>();

	public PrioritizedTask(int priority) {
		this.priority = priority;
		sequence.add(this);
	}

	@Override
	public int compareTo(PrioritizedTask arg) {
		return priority < arg.priority ? 1 : (priority > arg.priority ? -1 : 0);
	}

	@Override
	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
		} catch (InterruptedException e) {
			// 可以接受的退出方式
		}
		System.out.println(this);
	}

	@Override
	public String toString() {
		return String.format("[%1$-3d]", priority) + " Task " + id;
	}

	public String summary() {
		return "(" + id + ":" + priority + ")";
	}

	public static class EndSentinel extends PrioritizedTask {
		private ExecutorService exec;

		public EndSentinel(ExecutorService exec) {
			// 设定为一个最低的优先级
			super(-1);
			this.exec = exec;
		}

		@Override
		public void run() {
			int count = 0;
			for (PrioritizedTask pt : sequence) {
				System.out.print(pt.summary());
				if (++count % 5 == 0) {
					System.out.println();
				}
			}
			System.out.println();
			System.out.println(this + " Calling shutdownNow()");
			exec.shutdownNow();
		}

	}

}

class PrioritizedTaskProducer implements Runnable {
	private Random random = new Random(47);
	private Queue<Runnable> queue;
	private ExecutorService exec;

	public PrioritizedTaskProducer(Queue<Runnable> queue, ExecutorService exec) {
		this.exec = exec;
		this.queue = queue;
	}

	@Override
	public void run() {
		// 给队列里添加随机优先级的任务
		for (int i = 0; i < 20; i++) {
			queue.add(new PrioritizedTask(random.nextInt(10)));
			Thread.yield();
		}
		try {
			// 增加高优先级任务
			for (int i = 0; i < 10; i++) {
				TimeUnit.MILLISECONDS.sleep(250);
				queue.add(new PrioritizedTask(10));
			}
			// 添加任务, 从优先级为0开始
			for (int i = 0; i < 10; i++) {
				queue.add(new PrioritizedTask(i));
			}
			queue.add(new PrioritizedTask.EndSentinel(exec));
		} catch (InterruptedException e) {

		}
		System.out.println("Finished PrioritizedTaskProducer");
	}
}

class PrioritizedTaskConsumer implements Runnable {
	private PriorityBlockingQueue<Runnable> q;

	public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q) {
		this.q = q;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 使用当前线程去执行任务
				q.take().run();
			}
		} catch (InterruptedException e) {
		}
		System.out.println("Finished PrioritizedTaskConsumer");
	}

}

public class PriorityBlockingQueueDemo {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
		exec.execute(new PrioritizedTaskProducer(queue, exec));
		exec.execute(new PrioritizedTaskConsumer(queue));
	}
}
