package mi.practice.java.base.concurrency;

import static mi.practice.java.base.util.Print.*;

/**
 * 对象锁对于一个线程来说是可以反复获取的
 * 因为对于对象锁来说一个线程已经获得了这个对象的锁,
 * 理应可以使用其他的synchronized方法.
 */
public class MultiLock {

	public synchronized void f1(int count) {
		if (count-- > 0) {
			print("f1() calling f2() with count " + count);
			// 调用f2()的时候因为已经获得锁, 所以会一直执行
			f2(count);
		}
	}

	public synchronized void f2(int count) {
		if (count-- > 0) {
			print("f2() calling f1() with count " + count);
			// 同理: f1()调用f2()的情况
			f1(count);
		}
	}

	public static void main(String[] args) {
		final MultiLock multiLock = new MultiLock();
		new Thread() {
			@Override
			public void run() {
				multiLock.f1(10);
			}
		}.start();
	}
}
