package mi.practice.java.base.concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import static mi.practice.java.base.util.Print.*;

/**
 * 进入Blocked状态的几种可能:
 * 1.在线程里调用sleep();
 * 2.在线程里调用wait(),然后在没有被唤醒以前(notify(), notifyALL(), signal(), signalAll())
 * 3.IO阻塞
 * 4.synchronized阻塞
 * ExecutorService可以调用shutdownNow(), 它会调用各个线程的interrupt()方法对线程进行打断
 * 也可以使用submit()方法得到Future对象, 再调用Future对象的cancel()方法对其进行打断
 * SleepBlocked是可以被打断的blocked, 而IO和Synchronized Blocked是不能被interrupted打断的
 */
class SleepBlocked implements Runnable {
	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(100);// 休息100秒进入Blocked
		} catch (InterruptedException e) {
			print("InterruptedException");
		}
		print("Exiting SleepBlocked.run()");
	}
}

class IOBlocked implements Runnable {
	private InputStream in;

	public IOBlocked(InputStream is) {
		in = is;
	}

	@Override
	public void run() {
		try {
			print("Waiting for read()");
			in.read();// 读取IO进入Blocked
		} catch (IOException e) {
			if (Thread.currentThread().isInterrupted()) {
				print("Interrupted from blocked I/O");
			} else {
				throw new RuntimeException(e);
			}
		}
		print("Exiting IOBlocked.run()");
	}
}

class SynchronizedBlocked implements Runnable {
	public synchronized void f() {
		while (true) {
			Thread.yield();
		}
	}

	// 在构造函数里抛出一个线程执行f(), 对当前对象进行阻塞
	public SynchronizedBlocked() {
		// 启动一个新的线程锁死当前对象
		new Thread() {
			@Override
			public void run() {
				f();// 一直锁着不释放
			}
		}.start();
	}

	@Override
	public void run() {
		print("Trying to call f()");
		f();// 获取对象锁进入Blocked
		print("Exiting SynchronizedBlock.run()");
	}
}

public class Interrupting {
	private static ExecutorService exec = Executors.newCachedThreadPool();
	
	/**
	 * 调用ExecutorService执行当前的Runnable,
	 * 休息0.1秒, 当然打断当前线程.
	 * 这里的打断是通过Future进行操作的.
	 * @param r
	 * @throws InterruptedException
	 */
	static void test(Runnable r) throws InterruptedException {
		Future<?> f = exec.submit(r);
		TimeUnit.MILLISECONDS.sleep(100);
		print("Iterrupting " + r.getClass().getName());
		f.cancel(true);// 可以用这个方法对线程进行打断, true代表哪怕在执行的时候也打断
		print("Interrupt sent to " + r.getClass().getName());
	}

	public static void main(String[] args) throws InterruptedException {
		test(new SleepBlocked());
		test(new IOBlocked(System.in));
		test(new SynchronizedBlocked());
		TimeUnit.SECONDS.sleep(3);
		
		// 通过exec.shutDownNow() 还是不能打断IOBlocked和synchronized block
//		exec.execute(new SleepBlocked());
//		exec.execute(new IOBlocked(System.in));
//		exec.execute(new SynchronizedBlocked());
//		exec.shutdownNow();
//		TimeUnit.SECONDS.sleep(3);
		
		print("Aborting with System.exit(0)");
		System.exit(0);// 因为后两个打断失败,所以要这样推出
	}
}
