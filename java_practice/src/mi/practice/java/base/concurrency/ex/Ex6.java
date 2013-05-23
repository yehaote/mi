package mi.practice.java.base.concurrency.ex;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class RandomSleepTask implements Runnable {
	private Random rand = new Random();
	@Override
	public void run(){
		long timeout = (rand.nextInt(10)+1);
		try{
			TimeUnit.SECONDS.sleep(timeout);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.println("Sleep "+timeout+" seconds");
	}
}
public class Ex6 {
	public static void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0;i<10;i++){
			exec.execute(new RandomSleepTask());
		}
		exec.shutdown();
	}
}
