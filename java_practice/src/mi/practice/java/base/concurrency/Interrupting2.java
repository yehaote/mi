package mi.practice.java.base.concurrency;

import static mi.practice.java.base.util.Print.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示通过打断一个线程, 打断显示锁.
 * @author waf
 */
class BlockedMutex {
	private Lock lock = new ReentrantLock();

	public BlockedMutex() {
		// 获得锁并不释放, 演示打断一个显示的锁
		lock.lock();
	}

	public void f() {
		// 永远获取不到锁
		try {
			// 直接使用Lock是不能被线程打断的
//			lock.lock();
			// 使用这个是可以被打断的
			lock.lockInterruptibly();
			print("locked acquired in f()");
		} catch (InterruptedException e) {
			print("Interrupted from lock acquisition in f()");
		}
	}
}

class Blocked2 implements Runnable {
	// 包含一个带互斥锁的成员
	BlockedMutex blocked = new BlockedMutex();

	@Override
	public void run() {
		print("Waiting for f() in BlockedMutex");
		blocked.f();
		print("Broken out of blocked call");
	}
}

public class Interrupting2 {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Blocked2());
		t.start();
		TimeUnit.SECONDS.sleep(1);
		print("Issuing t.interrupt()");
		t.interrupt();
	}
}
