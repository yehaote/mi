package mi.practice.java.base.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 为什么在多核机器上会失败呢?
 */
class ExplicitPairManager1 extends PairManager {
	private Lock lock = new ReentrantLock();

	@Override
	public void increment() {
		lock.lock();
		try {
			p.incrementX();
			p.incrementY();
			// 注意值复制, 如果是引用复制的话, 到时候List里面存的都是一个引用
			store(getPair()); 
		} finally {
			lock.unlock();
		}
	}
}

class ExplicitPairManager2 extends PairManager {
	private Lock lock = new ReentrantLock();

	@Override
	public void increment() {
		Pair temp;
		lock.lock();
		try {
			p.incrementX();
			p.incrementY();
			// 不是引用复制, 是值拷贝
			temp = new Pair(p.getX(), p.getY());
		} finally {
			lock.unlock();
		}
		store(temp);
	}
}

public class ExplicitCriticalSection {
	public static void main(String[] args) {
		PairManager pman1 = new ExplicitPairManager1(), pman2 = new ExplicitPairManager2();
		CriticalSection.testApproaches(pman1, pman2);
	}
}
