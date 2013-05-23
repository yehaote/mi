package mi.practice.java.base.concurrency;

/**
 * 通过实现Runnable或者直接继承Thread都可以实现线程
 */
public class SimpleThread extends Thread {
	private int countDown = 5;
	private static int threadCount = 0;

	public SimpleThread() {
		super(Integer.toString(++threadCount));
		start();
	}

	@Override
	public String toString() {
		return "#" + getName() + "(" + countDown + "), ";
	}

	@Override
	public void run() {
		while (true) {
			System.out.print(this);
			if (--countDown == 0) {
				return;
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new SimpleThread();
		}
		System.out.println("Waiting for SimpleThread");
	}
}
