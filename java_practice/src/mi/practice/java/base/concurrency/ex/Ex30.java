package mi.practice.java.base.concurrency.ex;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Sender implements Runnable {
	private Random rand = new Random(47);
	private BlockingQueue<Character> queue = new LinkedBlockingQueue<Character>();

	public BlockingQueue<Character> getQueue() {
		return queue;
	}

	@Override
	public void run() {
		try {
			// 永远执行除非捕获到异常
			while (true) {
				// A-Z[\]^_`a-z
				for (char c = 'A'; c <= 'z'; c++) {
					queue.put(c);
					// 每写一个字符随机休眠
					TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				}
			}
		} catch (InterruptedException e) {
			System.out.println(e + " Sender sleep interrupted");
		}
	}
}

/**
 * 负责从管道里读出数据并且打印
 */
class Receiver implements Runnable {
	private BlockingQueue<Character> queue;

	public Receiver(Sender sender) throws IOException {
		queue = sender.getQueue();
	}

	@Override
	public void run() {
		try {
			while (true) {
				// Blocks until characters are there:
				// in.read()这个方法自动阻塞
				System.out.println("Read: " + queue.take() + ", ");
			}
		} catch (InterruptedException e) {
			System.out.println(e + " Receiver");
		}
	}
}

public class Ex30 {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		Sender sender = new Sender();
		Receiver receiver = new Receiver(sender);
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(sender);
		exec.execute(receiver);
		TimeUnit.SECONDS.sleep(3);
		exec.shutdownNow();
	}
}
