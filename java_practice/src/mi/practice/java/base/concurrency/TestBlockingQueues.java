package mi.practice.java.base.concurrency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Java在做生产者消费者模式的时候还可以使用BlockingQueue,
 * 很方便, 使用put放入元素, take获取元素.
 * 常用实现LinkedBlockingQueue没有大小限制,
 * ArrayBlockingQueue固定大小.
 * 注意LiftOffRunner忽略了同步的问题,
 * 把同步的问题都交由BlockingQueue来处理.
 */
/**
 * 包装后的LiftOff执行器,
 * 有一个BlockingQueue来放置需要执行的LiftOff.
 */
class LiftOffRunner implements Runnable {
	private BlockingQueue<LiftOff> rockets;

	public LiftOffRunner(BlockingQueue<LiftOff> queue) {
		rockets = queue;
	}

	public void add(LiftOff lo) {
		try {
			// 放入
			rockets.put(lo);
		} catch (InterruptedException e) {
			System.out.println("Interrupting during put()");
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 拿出一个
				LiftOff rocket = rockets.take();
				rocket.run(); // 使用当前线程执行
			}
		} catch (InterruptedException e) {
			System.out.println("Waking from take()");
		}
		System.out.println("Exiting LiftOffRunner");
	}
}

public class TestBlockingQueues {
	static void getKey() {
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static void getKey(String message) {
		System.out.println(message);
		getKey();
	}

	static void test(String msg, BlockingQueue<LiftOff> queue) {
		System.out.println(msg);
		LiftOffRunner runner = new LiftOffRunner(queue);
		Thread t = new Thread(runner);
		t.start();
		// 往runner队列里添加执行的元素
		for (int i = 0; i < 5; i++) {
			runner.add(new LiftOff(5));
		}
		getKey("Press 'Enter' (" + msg + ")");
		// 打断
		t.interrupt();
		System.out.println("Finished " + msg + " test");

	}

	public static void main(String[] args) {
		// 相当无限量大小
		test("LinkedBlockingQueue", new LinkedBlockingQueue<LiftOff>());
		// 固定大小
		test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>(3));
		// 测试同步的queue, 容量为1的queue
		test("SynchronousQueue", new SynchronousQueue<LiftOff>());
	}
}
