package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Test {
	volatile boolean flag = false;
	volatile long waitFlay = 0;
}

class TaskOne implements Runnable {
	private Test test;

	public TaskOne(Test test) {
		this.test = test;
	}

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Set value to true");
		synchronized (test) {
			test.flag = true;
		}
	}
}

class TaskTwo implements Runnable {
	private Test test;

	public TaskTwo(Test test) {
		this.test = test;
	}

	@Override
	public void run() {
		long begin = System.currentTimeMillis();
		// busy waiting
		while (test.flag != true) {
			System.out.println("Busy waiting");
			test.waitFlay++;
			// waiting
		}
		long cost = System.currentTimeMillis() - begin;
		synchronized (test) {
			test.flag = false;
			System.out
					.println("Busy waiting is finished, set the flag to false now.");
			System.out.println("Wast time of " + cost + "millisecond, waitFlag = "+test.waitFlay);
		}
	}
}

class TaskThree implements Runnable {
	private Test test;

	public TaskThree(Test test) {
		this.test = test;
	}

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Set value to true");
		synchronized (test) {
			test.flag = true;
			test.notifyAll();
		}
	}
}

class TaskFour implements Runnable {
	private Test test;

	public TaskFour(Test test) {
		this.test = test;
	}

	@Override
	public void run() {
		synchronized(test){
			while (test.flag != true) {
				test.waitFlay++;
				try {
					test.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			test.flag = false;
			System.out
					.println("Waiting is finished, set the flag to false now.");
			System.out.println("WaitFlag = "+test.waitFlay);
		}
	}
}

public class Ex22 {
	public static void main(String[] args) {
		Test test = new Test();
		ExecutorService exec = Executors.newCachedThreadPool();
//		exec.execute(new TaskOne(test));
//		exec.execute(new TaskTwo(test));
		exec.execute(new TaskThree(test));
		exec.execute(new TaskFour(test));
		exec.shutdown();
	}
}
