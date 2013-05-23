package mi.practice.java.base.concurrency;
/**
 * 为什么叫Fat?
 * 因为它的初始化开销很大.
 */
public class Fat {
	@SuppressWarnings("unused")
	private volatile double d; // Prevent optimization
	private static int counter = 0;
	private final int id = counter++;

	public Fat() {
		// Expensive, interruptible operation:
		for (int i = 1; i < 10000; i++) {
			d += (Math.PI + Math.E) / (double) i;
		}
	}

	public void operation() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "Fat id: " + id;
	}
}
