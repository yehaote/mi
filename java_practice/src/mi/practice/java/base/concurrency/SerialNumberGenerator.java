package mi.practice.java.base.concurrency;

/**
 * volatile是去除当前线程对field的缓存,
 * 如果没有volatile的话容易多个线程覆盖更新主内存,
 * 从而造成部分修改丢失的情况
 * 一般来说,如果一个field可能被多个线程同时访问的时候,
 * (至少有一个在更改)都应该设置成volatile.
 * 
 * volatile可以理解成Thread在写的时候,
 * 先写入thread的cache, 然后同步到住memory,
 * 本来这个动作是可能会有写入丢失的, 
 * 加了volatile以后多个线程进行写的时候,
 * 就不会产生写入数据被覆盖的时候.
 * 但是注意多线程同时写入的时候, 还有一种可能是
 * thread a写入的以后, 还没有进行读取,
 * thread b再写入, 然后这个时候thread a进行读出, 
 * 就会把thread a和thread b 的写入同时都读出来, 
 * 所以下面的方法多线程执行的时候不能保证产出的值是唯一的.
 */
public class SerialNumberGenerator {
	private static volatile int serialNumber = 0;
	
	// 这个方法会产生重复的值
	public static int nextSerialNumber() {
		return serialNumber++;// 非线程安全的
	}
}
