package mi.practice.java.base.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 使用显示的锁,而不是使用synchronized,
 * 有一个好处就是如果synchronized抛出异常的话,不方便清理
 * 而finally可以很方便得清理数据
 */
public class MutexEvenGenerator extends IntGenerator{
	private int currentEvenValue;
	private Lock lock = new ReentrantLock();
	@Override
	public int next() {
		lock.lock();
		try{
			++currentEvenValue;
			Thread.yield();
			++currentEvenValue;
			return currentEvenValue;//必须在释放之前return
		}finally{
			lock.unlock();
		}
	}
	public static void main(String[] args){
		EvenChecker.test(new MutexEvenGenerator());
	}
}
