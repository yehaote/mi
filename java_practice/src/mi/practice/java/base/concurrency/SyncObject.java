package mi.practice.java.base.concurrency;

import static mi.practice.java.base.util.Print.*;

/**
 * synchronized还可以给别的对象加锁,
 * synchronized给别的对象加的锁跟本地对象的锁无关.
 * 所以输出的时候, f(), g()可以是交替显示的.
 */
class DualSynch {
	private Object syncObject = new Object();

	public synchronized void f() {
		for (int i = 0; i < 5; i++) {
			print("f()");
			Thread.yield();
		}
	}

	public void g() {
		// 跟syncObject的对象锁同步
		synchronized (syncObject) {
			for (int i = 0; i < 5; i++) {
				print("g()");
				Thread.yield();
			}
		}
	}
}

public class SyncObject {
	public static void main(String[] args) {
		final DualSynch ds = new DualSynch();
		new Thread() {
			@Override
			public void run() {
				ds.f();
			}
		}.start();
		ds.g();
	}
}