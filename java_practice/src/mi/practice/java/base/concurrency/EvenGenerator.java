package mi.practice.java.base.concurrency;

/**
 * 在多线程同时调用next方法的时候会出错,
 * 当一个线程只加了一次, 而第二个线程加完得到返回值的时候,
 * 这个时候的currentEvenValue是奇数
 */
public class EvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;

	@Override
	public int next() {
		++currentEvenValue;// Danger point here!
		// Thread.yield(); // 更加容易出错
		++currentEvenValue;
		return currentEvenValue;
	}

	public static void main(String[] args) {
		EvenChecker.test(new EvenGenerator());
	}

}
