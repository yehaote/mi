package mi.practice.java.base.concurrency;

/**
 * 筷子
 */
public class Chopstick {
	private boolean taken = false;

	/**
	 * 拿起筷子
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void take() throws InterruptedException {
		while (taken) {
			wait();
		}
		taken = true;
	}

	/**
	 * 放下筷子
	 */
	public synchronized void drop() {
		taken = false;
		notifyAll();
	}
}
