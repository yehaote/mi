package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import mi.practice.java.base.util.DaemonThreadFactory;
import static mi.practice.java.base.util.Print.*;

/**
 * 当使用Executors来建立线程的时候, 可以用过传入一个
 * ThreadFactory对象来给线程设置属性, 比如设置为Daemon
 */
public class DaemonFromFactory implements Runnable {
	@Override
	public void run() {
		try {
			while (true) {
				TimeUnit.MILLISECONDS.sleep(100);
				print(Thread.currentThread() + " " + this);
			}
		} catch (InterruptedException e) {
			print("Interrupted");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors
				.newCachedThreadPool(new DaemonThreadFactory());
		for (int i = 0; i < 10; i++) {
			exec.execute(new DaemonFromFactory());
		}
		print("All daemons started");
		TimeUnit.MILLISECONDS.sleep(500);
	}
}
