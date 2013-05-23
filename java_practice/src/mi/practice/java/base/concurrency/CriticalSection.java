package mi.practice.java.base.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Pair类是线程不安全的,
 * 当想要使用的时候创建了一个PairManager,
 * PairManager采用模板设计模式,
 * 有1个线程安全的counter来记录checker的访问次数,
 * 1个线程安全的List用来存储manipulator产生的新的Pair.
 * PairManager有一个线程安全的getPair(),
 * 还有一个抽象的increment()对私有的pair进行操作.
 * PairManager1采用synchronized整个increment()方法,
 * 但是store()是线程安全的, 而且很消耗时间, 这个就大大减少了checker1访问的次数
 * 而PairManager2只是部分代码(critical section)synchronized,
 * 所以checker2访问的次数要远远大于checker1
 * 这也是采用critical section一个很主要的原因
 * Collections.synchronizedList()
 * 会根据是否是randomAccess返回两种list,
 * SynchronizedRandomAccessList或者SynchronizedList.
 */
class Pair { // 线程不安全的
	private  volatile int x, y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Pair() {
		this(0, 0);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void incrementX() {
		x++;
	}

	public void incrementY() {
		y++;
	}

	@Override
	public String toString() {
		return "x: " + x + ", y: " + y;
	}

	@SuppressWarnings("serial")
	public class PairValuesNotEqualException extends RuntimeException {
		public PairValuesNotEqualException() {
			super("Pair values not equal: " + Pair.this);
		}
	}

	public void checkState() {
		if (x != y) {
			throw new PairValuesNotEqualException();
		}
	}
}

abstract class PairManager {
	AtomicInteger checkCounter = new AtomicInteger(0);
	protected Pair p = new Pair();
	// 初始化一个synchronizedList,
	// Collections.
	private List<Pair> storage = Collections
			.synchronizedList(new ArrayList<Pair>());

	public synchronized Pair getPair() {
		// Make a copy to keep the original safe:
		return new Pair(p.getX(), p.getY());
	}

	// Assume this is a time consuming operation
	protected void store(Pair p) {
		storage.add(p);
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
		}
	}

	public abstract void increment();
}

// synchronize the entire method:
class PairManager1 extends PairManager {
	@Override
	public synchronized void increment() {
		p.incrementX();
		p.incrementY();
		store(p);
	}
}

// Use a critical section:
class PairManager2 extends PairManager {
	@Override
	public void increment() {
		Pair temp;
		synchronized (this) {
			p.incrementX();
			p.incrementY();
			temp = getPair();
		}
		store(temp);
	}
}

class PairManipulator implements Runnable {
	private PairManager pm;

	public PairManipulator(PairManager pm) {
		this.pm = pm;
	}

	@Override
	public void run() {
		while (true) {
			pm.increment();
		}
	}

	@Override
	public String toString() {
		return "Pair: " + pm.getPair() + " checkCounter = "
				+ pm.checkCounter.get();
		// return "Pair: "+" checkCounter = "+pm.checkCounter.get();
		// //因为toString也会导致锁的问题, 所以有的时候释放很慢
	}
}

class PairChecker implements Runnable {
	private PairManager pm;

	public PairChecker(PairManager pm) {
		this.pm = pm;
	}

	@Override
	public void run() {
		while (true) {
			pm.checkCounter.incrementAndGet();
			pm.getPair().checkState();
		}
	}
}

public class CriticalSection {
	// Test the two different approaches:
	static void testApproaches(PairManager pman1, PairManager pman2) {
		ExecutorService exec = Executors.newCachedThreadPool();
		PairManipulator pm1 = new PairManipulator(pman1), pm2 = new PairManipulator(
				pman2);
		PairChecker pchecker1 = new PairChecker(pman1), pchecker2 = new PairChecker(
				pman2);
		exec.execute(pm1);
		exec.execute(pm2);
		exec.execute(pchecker1);
		exec.execute(pchecker2);
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			System.out.println("Sleep Interrupted");
		}
		System.out.println("pm1: " + pm1 + "\npm2: " + pm2);
		System.exit(0);
	}

	public static void main(String[] args) {
		PairManager pman1 = new PairManager1(), pman2 = new PairManager2();
		testApproaches(pman1, pman2);

	}
}
