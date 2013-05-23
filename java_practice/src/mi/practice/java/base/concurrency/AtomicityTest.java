package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 虽然return是原子操作,
 * 但是还是可以发现getValue()的时候可以得到奇数的值,
 * 所以不能盲目以来原子性
 * java synchronization的缺点是允许值在不稳定的中间状态的时候被读取
 * 所以最好读写都加上synchronized
 */
public class AtomicityTest implements Runnable {
	private int i = 0;
	
	/**
	 * 虽说evenIncrement()是synchronized, 
	 * 但是getValue()可以把evenIncrement()在执行的时候的中间状态,
	 * 进行返回.
	 */
	public int getValue() {
		return i;
	}
	
	private synchronized void evenIncrement() {
		i++;
		i++;
	}

	@Override
	public void run() {
		while (true) {
			evenIncrement();
		}
	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		AtomicityTest at = new AtomicityTest();
		exec.execute(at);
		while (true) {
			int val = at.getValue();
			if (val % 2 != 0) {
				System.out.println(val);
				System.exit(0);
			}
		}
	}
}
