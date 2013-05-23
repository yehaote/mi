package mi.practice.java.concurrency;

/**
 * 不是线程安全的.
 * 因为value++实际上在java中是三个指令.
 * 取出, 加上值, 放回去.
 * 如果当多个线程同时调用的时候会有指令覆盖的情况,
 * 就是线程A,B同时取出值, 然后同时写回,
 * 这个后者就会将前者的修改覆盖掉.
 * race condition
 */
public class UnsafeSequence {
	private int value;

	/**
	 * 返回一个唯一的值
	 * 
	 * @return
	 */
	public int getNext() {
		return value++;
	}
}
