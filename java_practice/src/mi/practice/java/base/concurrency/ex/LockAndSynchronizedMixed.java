package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 实验结果: synchronized和Lock是互相不影响的,
 * 也解答了p1175的问题
 */
class Demo {
	private Lock lock = new ReentrantLock();

	synchronized void f() {
		for (int i = 0; i < 5; i++) {
			System.out.println("f() I'm locked by synchronized");
			Thread.yield();
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("Iterrupted");
			}
		}
	}

	synchronized void g() {
		for (int i = 0; i < 5; i++) {
			System.out.println("g() I'm locked by synchronized");
			Thread.yield();
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("Iterrupted");
			}
		}
	}

	void h() {
		lock.lock();
		try {
			for (int i = 0; i < 5; i++) {
				System.out.println("h() I'm locked by lock");
				Thread.yield();
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					System.out.println("Iterrupted");
				}
			}
		} finally {
			lock.unlock();
		}
	}

	void i() {
		lock.lock();
		try {
			for (int i = 0; i < 5; i++) {
				System.out.println("i() I'm locked by lock");
				Thread.yield();
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					System.out.println("Iterrupted");
				}
			}
		} finally {
			lock.unlock();
		}
	}
}

public class LockAndSynchronizedMixed {
	public static void main(String[] args) {
		final Demo demo = new Demo();
		new Thread() {
			@Override
			public void run() {
				demo.f();
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				demo.g();
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				demo.h();
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				demo.i();
			}
		}.start();
	}
}
