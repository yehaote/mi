package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class LongSleep{
	public void sleep(){
		try {
			TimeUnit.SECONDS.sleep(60);
		} catch (InterruptedException e) {
			System.out.println("LongSleep.sleep() interrupted");
		}
	}
}

class DemoTask implements Runnable{
	private LongSleep sleep = new LongSleep();
	@Override
	public void run() {
		System.out.println("call LongSleep.sleep()");
		sleep.sleep();
		System.out.println("after LongSleep.sleep()");
	}
	
}
public class Ex18 {
	public static void main(String[] args) throws InterruptedException{
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new DemoTask());
		TimeUnit.SECONDS.sleep(3);
		System.out.println("call interrupt");
		exec.shutdownNow();
	}
}
