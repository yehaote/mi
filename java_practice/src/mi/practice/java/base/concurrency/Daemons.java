package mi.practice.java.base.concurrency;

import static mi.practice.java.base.util.Print.*;

import java.util.concurrent.TimeUnit;

/**
 * 由Daemon线程创造出来的线程也是Daemon的
 */
class Daemon implements Runnable {
	private Thread[] t = new Thread[10];

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			// 在一个Thread中抛出另一个Thread
			t[i] = new Thread(new DaemonSpawn());
			t[i].start();
			printnb("DaemonSpawn " + i + " started, ");
		}
		for (int i = 0; i < t.length; i++) {
			printnb("t[" + i + "].isDaemon = " + t[i].isDaemon() + ", ");
		}
		while (true) {
			Thread.yield();
		}
	}
}
/**
 * 无限执行的一个任务
 */
class DaemonSpawn implements Runnable {
	@Override
	public void run() {
		while (true) {
			Thread.yield();
		}
	}
}

public class Daemons {
	public static void main(String[] args) throws InterruptedException {
		Thread d = new Thread(new Daemon());
		d.setDaemon(true);
		d.start();
		printnb("d.isDaemon() = " + d.isDaemon() + ", ");
		TimeUnit.SECONDS.sleep(1);

		// ExecutorService exec = Executors.newCachedThreadPool(new
		// DaemonThreadFactory());
		// ExecutorService exec = Executors.newCachedThreadPool();
		// exec.execute(new Daemon());
		// TimeUnit.SECONDS.sleep(2);
		// exec.shutdown();
	}
}
