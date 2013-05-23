package mi.practice.java.base.concurrency;

/**
 * 每一个对象都有一个锁,
 * 当一个synchronized方法被调用的时候, 就会启用这个锁,
 * 其他对象在对其synchronized进行调用的时候要等待前面获得锁的task完成
 * 注意:把重要的field设置成private, 不能别的对象可以通过直接访问这个域而跳过锁
 * 当一个task包含多个synchronized方法的时候, 这个时候锁的值会自增,
 * 当任务完成后锁再自减到0, 表示释放了锁
 */
public class SynchronizedEvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	
	@Override
	public synchronized int next() {
		++currentEvenValue;
		Thread.yield();
		++currentEvenValue;
		return currentEvenValue;
	}

	public static void main(String[] args) {
		EvenChecker.test(new SynchronizedEvenGenerator());
	}

}
