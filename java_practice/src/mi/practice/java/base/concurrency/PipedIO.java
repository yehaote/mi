package mi.practice.java.base.concurrency;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多线程通过管道进行通讯, 
 * PipedWriter, PipedReader.
 * 它们和BlockingQueue差不多, 
 * 在Java中默认还没有提供BlockingQueue的时候, 
 * 它们就已经存在了.
 * BlockingQueue相对于它们来说显得更加强壮和容易使用.
 * 
 * 它们跟传统的IO相比较来说是它的阻塞是可以被打断的.
 */
/**
 * 负责输出内容到管道
 */
class Sender implements Runnable {
	private Random rand = new Random(47);
	private PipedWriter out = new PipedWriter();

	public PipedWriter getPipedWriter() {
		return out;
	}

	@Override
	public void run() {
		try {
			// 永远执行除非捕获到异常
			while (true) {
				// A-Z[\]^_`a-z
				for (char c = 'A'; c <= 'z'; c++) {
					out.write(c);
					// 每写一个字符随机休眠
					TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				}
			}
		} catch (IOException e) {
			System.out.println(e + " Sender write exception");
		} catch (InterruptedException e) {
			System.out.println(e + " Sender sleep interrupted");
		}
	}

}

/**
 * 负责从管道里读出数据并且打印
 */
class Receiver implements Runnable {
	private PipedReader in;

	public Receiver(Sender sender) throws IOException {
		// 根据一个PipedWriter初始化一个PipedReader
		in = new PipedReader(sender.getPipedWriter());
	}

	@Override
	public void run() {
		try {
			while (true) {
				// Blocks until characters are there:
				// in.read()这个方法自动阻塞
				System.out.println("Read: " + (char) in.read() + ", ");
			}
		} catch (IOException e) {
			System.out.println(e + " Receiver");
		}
	}
}

public class PipedIO {
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
