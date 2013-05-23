package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可以通过Thread.setDefaultUncaughtExeceptionHandler()来设置默认的,
 * 它在Thread里面静态存储
 * 如果线程没有自己的UncaughtExeceptionHandler(),
 * 则使用默认的handler来处理
 */
public class SettingDefaultHandler {
	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new ExceptionThread());
	}
}
