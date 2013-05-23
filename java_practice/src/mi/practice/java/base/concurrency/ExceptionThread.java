package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 在线程中抛出异常
 */
public class ExceptionThread implements Runnable{
	@Override
	public void run(){
		throw new RuntimeException();
	}
	public static void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new ExceptionThread());
	}
}
