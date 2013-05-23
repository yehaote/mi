package mi.practice.java.base.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal对于每一个线程来说都是不一样的,
 * 可以理解为他是一个集合, 每个线程都只取自己id存进去的变量
 * TheadLocal对象通常是static的,
 * ThreadLocal用get(),set()来进行存取,
 * get()方法返回的是对象的一个副本
 * 不用给increment()和get()加上synchronized的, 因为他们是线程安全的.
 * 每一个线程都只取他自己的value.
 */
// 访问器, 去访问一个ThreadLocal对象
class Accessor implements Runnable {
	private final int id;

	public Accessor(int idn) {
		id = idn;
	}

	@Override
	public void run() {
		//当当前线程没有被打断的时候一直执行
		while (!Thread.currentThread().isInterrupted()) {
			// 
			ThreadLocalVariableHolder.increment();
			System.out.println(this);
			Thread.yield();
		}
	}

	@Override
	public String toString() {
		return "#" + id + ": " + ThreadLocalVariableHolder.get();
	}
}

public class ThreadLocalVariableHolder {
	// 线程私有对象, 每个线程都不一样
	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
		private Random rand = new Random(47);

		// 覆盖初始化值函数, 设置初值
		@Override
		protected synchronized Integer initialValue() {
			return rand.nextInt(10000);
		}
	};

	public static void increment() {
		value.set(value.get() + 1);
	}

	public static int get() {
		return value.get();
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new Accessor(i));
		}
		TimeUnit.MILLISECONDS.sleep(3);
		exec.shutdownNow();
	}
}
