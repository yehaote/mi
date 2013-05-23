package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * sleep()方法会抛出一个InterruptedException
 * 因为在另外线程的run()中抛出不会传播给main()
 * 所以要在run()里面处理掉
 * @author waf
 *
 */
public class SleepingTask extends LiftOff{
	@Override
	public void run(){
		try{
			while(countDown-- >0){
				System.out.println(status());
				// Old-style:
				// Thread.sleep(100)
				// Java SE5/6-style:
				TimeUnit.MILLISECONDS.sleep(100);
			}
		}catch(InterruptedException e){
			System.err.println("Iterrupted");
		}
		
	}
	public static void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0;i<5;i++){
			exec.execute(new SleepingTask());
		}
		exec.shutdown();
	}
}
