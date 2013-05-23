package mi.practice.java.base.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 大多数情况下都应该使用synchronized,
 * 使用Lock只有当特殊需求的时候,
 * 比如使用synchronized没有试着去获取锁,
 * 去获取锁等待一定量相应的时间
 */
public class AttemptLocking {
	private ReentrantLock lock = new ReentrantLock();

	public void untimed() {
		// 可以使用tryLock来尝试获取锁
		boolean captured = lock.tryLock();
		try {
			System.out.println("tryLock() " + captured);
		} finally {
			if (captured) {
				lock.unlock();
			}
		}
	}

	public void timed() {
		boolean captured = false;
		try {
			// tryLock还可以加上最大的等待时间
			captured = lock.tryLock(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		try {
			System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
		} finally {
			if (captured) {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final AttemptLocking al = new AttemptLocking();
		al.untimed();
		al.timed();
		new Thread() {
			{
				setDaemon(true);
			}

			@Override
			public void run() {
				al.lock.lock();
				System.out.println("acquired");
			}
		}.start();
		Thread.yield();
//		TimeUnit.SECONDS.sleep(3);
		al.untimed();
		al.timed();
	}
}
