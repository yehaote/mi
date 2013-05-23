package mi.practice.java.concurrency;

/**
 * 线程安全的,
 * 这里的synchronized是对象锁,
 * 并且是独占锁.
 */
public class Sequence {
	private int nextValue;

	public synchronized int getNext() {
		return nextValue++;
	}
}
