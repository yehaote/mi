package mi.practice.java.base.util;

import java.util.concurrent.ThreadFactory;
/**
 * 这个工程把生产的Thread都设置成daemon,
 * ThreadFactory可以在初始化ExecutorService的时候使用.
 * 比如:
 * ExecutorService exec = Executors
		.newCachedThreadPool(new DaemonThreadFactory());
 */
public class DaemonThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}
	
}
