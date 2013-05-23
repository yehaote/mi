package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class LiftOff implements Runnable {
	protected int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;

	public LiftOff() {
	}

	public LiftOff(int countDown) {
		this.countDown = countDown;
	}

	public String status() {
		return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff") + "), ";
	}

	@Override
	public void run(){
		while(countDown-- >0){
			System.out.print(status());
		}
		while(countDown < 0){
			try{
				System.out.println(this+"sleep()");
				TimeUnit.SECONDS.sleep(1);
			}catch(InterruptedException e){
				System.out.println(this+"Breaking from sleep()");
				break;
			}
		}
	}
}

public class Ex20 {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new LiftOff());
		}
		exec.shutdown();
		TimeUnit.SECONDS.sleep(10);
		exec.shutdownNow();
		exec.awaitTermination(2, TimeUnit.SECONDS);
	}
}
