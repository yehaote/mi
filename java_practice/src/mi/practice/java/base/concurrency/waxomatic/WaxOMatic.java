package mi.practice.java.base.concurrency.waxomatic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * wait() 暂停当前线程, 需要notify()或者notifyAll()才能唤醒
 * sleep(), yield()不会释放synchronized锁, 而wait()会释放.
 * 只有在synchronized块里面才能调用wait, notify, notifyAll
 */
class Car {
	private boolean waxOn = false;

	public synchronized void waxed() {
		waxOn = true;// Ready to buff
		notifyAll();
	}

	public synchronized void buffed() {
		waxOn = false;// Ready for another coat of car
		notifyAll();
	}
	
	/**
	 * 检查参数的时候需要用loop包含起来, 很重要
	 * 1. 可能有多个线程都在等待这一个状态. 或许已经被其他线程抢先一步.
	 * 2. wait()被唤醒的情况有很多, 可能不是这个变量发生了变化.
	 * @throws InterruptedException
	 */
	public synchronized void waitForWaxing() throws InterruptedException {
		while (waxOn == false) {
			wait();
		}
	}

	public synchronized void waitForBuffing() throws InterruptedException {
		while (waxOn == true) {
			wait();
		}
	}
}

/**
 * 任务专门负责给car Wax(打蜡)
 */
class WaxOn implements Runnable {
	private Car car;

	public WaxOn(Car car) {
		this.car = car;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("Wax On!");
				TimeUnit.MILLISECONDS.sleep(200);
				car.waxed();
				// 等待擦亮结束, 没有擦亮的就一直等待
				car.waitForBuffing();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("Ending Wax On tash");
	}
}

class WaxOff implements Runnable {
	private Car car;

	public WaxOff(Car car) {
		this.car = car;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 等待, 直到打蜡完成
				car.waitForWaxing();
				System.out.println("Wax Off!");
				TimeUnit.MILLISECONDS.sleep(200);
				car.buffed();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting via interrupt");
		}
		System.out.println("Ending Wax Off task");
	}
}

public class WaxOMatic {
	public static void main(String[] args) throws InterruptedException {
		Car car = new Car();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOff(car));
		exec.execute(new WaxOn(car));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}
