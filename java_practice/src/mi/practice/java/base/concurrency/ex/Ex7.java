package mi.practice.java.base.concurrency.ex;

import static mi.practice.java.base.util.Print.printnb;

import java.util.concurrent.TimeUnit;

class Daemon implements Runnable {
	private Thread[] t = new Thread[10];

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
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

class DaemonSpawn implements Runnable {
	@Override
	public void run() {
		while (true) {
			Thread.yield();
		}
	}
}

public class Ex7 {
	public static void main(String[] args) throws InterruptedException {
		Thread d = new Thread(new Daemon());
		d.setDaemon(true);
		d.start();
		printnb("d.isDaemon() = " + d.isDaemon() + ", ");
//		TimeUnit.SECONDS.sleep(1);
		TimeUnit.MILLISECONDS.sleep(100);
	}
}
