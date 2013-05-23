package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 这里读写都要加上synchronized
 */
class Ex11Resource {
	private int threeTime = 0;
	private int twoTime = 0;

	public synchronized void increment() {
		threeTime++;
		twoTime++;
		threeTime++;
		twoTime++;
		threeTime++;
	}

	public synchronized boolean sayResult() {
		if ((threeTime % 3 != 0) || (twoTime % 2 != 0)) {
			System.out.println("improper status threeTime = "+threeTime+", twoTime = "+twoTime);
			return false;
		}else{
//			System.out.println("all right");
			return true;
		}
	}
}

class Ex11Task implements Runnable{
	private Ex11Resource resource;
	public Ex11Task(Ex11Resource resource){
		this.resource=resource;
	}
	@Override
	public void run() {
		boolean goOn=true;
		while(goOn){
			resource.increment();
			goOn=resource.sayResult();
		}
	}
}

public class Ex11 {
	public static void main(String[] args){
		Ex11Resource resource = new Ex11Resource();
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0;i<20;i++){
			exec.execute(new Ex11Task(resource));
		}
		exec.shutdown();
	}
}
