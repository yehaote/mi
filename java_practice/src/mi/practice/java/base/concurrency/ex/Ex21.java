package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class WaitTask implements Runnable {

	@Override
	public void run() {
		synchronized (this) {
			try {
				System.out.println("Wait task wait()");
				wait();
				System.out.println("Wait task awaken from wait()");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class NotifyTask implements Runnable {
	private WaitTask task;

	public NotifyTask(WaitTask waitTask) {
		this.task = waitTask;
	}

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized(task){
			System.out.println("Notify wait task");
			task.notifyAll();
		}
	}
}

public class Ex21 {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		WaitTask waitTask = new WaitTask();
		exec.execute(waitTask);
		exec.execute(new NotifyTask(waitTask));
		exec.shutdown();
	}
}
