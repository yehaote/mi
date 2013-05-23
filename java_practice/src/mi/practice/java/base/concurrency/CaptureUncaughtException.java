package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 线程间的异常捕获可以使用Thread.UncaughtExceptionHandler来获取
 */
class ExceptionThread2 implements Runnable {
	@Override
	public void run() {
		Thread t = Thread.currentThread();
		System.out.println("run() by " + t);
		// 打印出本线程的异常处理类
		System.out.println("eh = " + t.getUncaughtExceptionHandler());
		// 抛出Runtime异常
		throw new RuntimeException();
	}

}

/**
 * 实现Thread.UncaughtExceptionHandler,
 * uncaughtException()方法捕获并处理异常
 */
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
	//
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("caught " + e);
	}
}

class HandlerThreadFactory implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		System.out.println(this + " creating new Thread");
		Thread t = new Thread(r);
		System.out.println("created " + t);
		// 给线程设置异常处理器
		t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		System.out.println("eh = " + t.getUncaughtExceptionHandler());
		return t;
	}
}

public class CaptureUncaughtException {
	public static void main(String[] args) {
		ExecutorService exec = Executors
				.newCachedThreadPool(new HandlerThreadFactory());
		exec.execute(new ExceptionThread2());
	}
}
