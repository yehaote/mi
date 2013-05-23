package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在主线程里是没有办法处理其他进程里抛出的runtimeException的.
 * 后面有演示可以捕获到的方法.
 */
public class NaiveExceptionHandling {
	public static void main(String[] args) {
		try {
			ExecutorService exec = Executors.newCachedThreadPool();
			exec.execute(new ExceptionThread());
		} catch (RuntimeException ue) {
			// This statement will NOT execute!
			System.out.println("Exception has been handled!");
		}
	}
}
