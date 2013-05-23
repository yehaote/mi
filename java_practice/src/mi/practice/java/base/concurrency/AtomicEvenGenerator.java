package mi.practice.java.base.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 只有当特殊情况下才考虑去使用Atomic类,
 * 而且当你明白这么多做不会产生其他问题的情况的时候.
 */
public class AtomicEvenGenerator extends IntGenerator {
	private AtomicInteger currentEvenValue = new AtomicInteger(0);

	@Override
	public int next() {
		return currentEvenValue.addAndGet(2);
	}

	public static void main(String[] args) {
		EvenChecker.test(new AtomicEvenGenerator());
	}
}