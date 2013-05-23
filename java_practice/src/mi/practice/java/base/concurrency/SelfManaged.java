package mi.practice.java.base.concurrency;

/**
 * 还有一种办法是实现Runnable接口,
 * 然后在类中包含私有Thread对象.
 * 并在构造函数里进行启动.
 */
public class SelfManaged implements Runnable {
	private int countDown = 5;
	private Thread t = new Thread(this);

	public SelfManaged() {
		t.start();
	}

	@Override
	public String toString() {
		return Thread.currentThread().getName() + "(" + countDown + "), ";
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
			new SelfManaged();
		}
	}
}
