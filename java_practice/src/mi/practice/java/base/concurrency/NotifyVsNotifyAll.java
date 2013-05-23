package mi.practice.java.base.concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 当task都是使用相同的condition的时候,
 * 才能使用notify()代替notifyAll().
 * notify()只唤醒一个进程可是你不知道它是不是你想要的,
 * 除非你能确认都是一样的标准的.
 * 
 * notify和notifyAll的机制都是基于对象锁,
 * 也就是实体锁, 只对当前对象的wait()起作用.
 */
class Blocker {
	synchronized void waitingCall() {
		try {
			while (!Thread.interrupted()) {
				wait();
				System.out.println(Thread.currentThread() + " ");
			}
		} catch (InterruptedException e) {
			// OK to exit this way
		}
	}

	synchronized void prod() {
		notify();
	}

	synchronized void prodAll() {
		notifyAll();
	}
}

class Task implements Runnable {
	static Blocker blocker = new Blocker();

	@Override
	public void run() {
		blocker.waitingCall();
	}
}

class Task2 implements Runnable {
	// A separate Blocker object
	static Blocker blocker = new Blocker();

	@Override
	public void run() {
		blocker.waitingCall();
	}
}

public class NotifyVsNotifyAll {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new Task());
		}
		exec.execute(new Task2());
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			boolean prod = true;

			@Override
			public void run() {
				if (prod) {
					System.out.println("\nnotify()");
					Task.blocker.prod();
					prod = false;
				} else {
					System.out.println("\nnotifyAll()");
					Task.blocker.prodAll();
					prod = true;
				}
			}
		}, 400, 400);
		TimeUnit.SECONDS.sleep(5);
		timer.cancel();
		System.out.println("\nTime canceled");
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("\nTask2.blocker.prodAll()");
		Task2.blocker.prodAll();
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("\nShutting down");
		exec.shutdownNow();
	}
}
