package mi.practice.java.base.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mi.practice.java.base.util.Generated;
import mi.practice.java.base.util.RandomGenerator;

/**
 * Java同步集合类的进化
 * 最初:
 * 在Java中Vector和HashTable提供了很多的同步的方法,
 * 因为这个原因当不是多线程的情况下去使用这两个集合的是会有效率的问题.
 * 接着:
 * 在Java 1.2中提供的一些新的集合类都不是同步的, 比如HashMap, ArrayList等.
 * 但是在Collections中提供了很多静态的方法可以返回不同类型的同步的集合类.
 * 继续进化:
 * Java 1.5中又另外提供了一些同步的集合类, 并提供了一些聪明的手段来降低因为锁机制而造成的开销.
 * 主要的策略是让修改和读取可以同时发生, 因此只有在读取仅仅能看到已经修改完成的结果.
 * 修改不是直接在元数据上进行的, 而是在对应的一个副本上进行操作.
 * 这个副本有的时候是一个完全的拷贝, 有的时候是一个部分的拷贝.
 * 这个副本在在修改的时候对于读取来说是不可见的, 当修改完成以后再通过一个原子操作把副本跟元数据进行交换,
 * 这个时候才能被看到这些修改.
 * 1.5 例子:
 * CopyOnWriteArrayList就是这样一个实现,
 * 而且当多个迭代器同时进行读写的时候也不会抛出ConcurrentModificationException,
 * CopyOnWriteArraySet使用CopyOnWriteArrayList当作为一个储存.
 * ConcurrentHashMap和ConcurrentLinkedQueue也使用类似的技术来允许同时进行读写,
 * 不过只有一部分数值会被拷贝成副本而不是全部.
 * 不足:
 * 因为少了获取锁的开销在相对数量比较小的时候, 这个Lock-free形式的会开一些.
 * 下面是一个测试容器类读写性能Demo的例子,
 * 其中C代表容器的实现类.
 */
public abstract class Tester<C> {
	static int testReps = 5; // 重复实验的次数?
	static int testCycles = 10; // 每次实验中循环的次数, 一般在读写任务中使用
	static int containerSize = 10000; // 实验的容器的大小

	/**
	 * 抽象方法
	 * 容器初始化, 返回初始化好的容器
	 * 随后在testContainer中包含对它的引用
	 * 
	 * @return
	 */
	abstract C containerInitializer();

	/**
	 * 抽象方法
	 * 开启读写测试的任务
	 */
	abstract void startReadersAndWriters();

	C testContainer; // 被测试的容器
	String testId; // 当前测试的id
	int nReaders; // 多少个读的任务
	int nWriters; // 多少个写的任务
	volatile long readResult = 0; // 记录读取的结果
	volatile long readTime = 0; // 读消耗的时间
	volatile long writeTime = 0; // 写消耗的时间
	CountDownLatch endLatch;
	static ExecutorService exec = Executors.newCachedThreadPool();
	Integer[] writeData;

	Tester(String testId, int nReaders, int nWriters) {
		this.testId = testId + " " + nReaders + "r " + nWriters + "w";
		this.nReaders = nReaders;
		this.nWriters = nWriters;
		// 生成写入的数据
		writeData = Generated.array(Integer.class,
				new RandomGenerator.Integer(), containerSize);
		// 重复testReps次runTest()
		for (int i = 0; i < testReps; i++) {
			runTest();
			readTime = 0;
			writeTime = 0;
		}
	}

	void runTest() {
		// 等待一次实验所有的读和写的任务都完成
		endLatch = new CountDownLatch(nReaders + nWriters);
		testContainer = containerInitializer(); // 初始化测试容器
		startReadersAndWriters(); // 执行读写测试
		// 等待读写测试都完成
		try {
			endLatch.await();
		} catch (InterruptedException e) {
			System.out.println("endLatch interrupted");
		}
		System.out.printf("%-27s %14d %14d\n", testId, readTime, writeTime);
		if (readTime != 0 && writeTime != 0) {
			System.out.printf("%-27s %14d\n", "readTime +writeTime = ",
					readTime + writeTime);
		}
	}

	/**
	 * 每个读写任务都基于这个抽象类实现,
	 * 是一个内部类(inner-class)
	 * 保留对当前测试任务的引用
	 */
	abstract class TestTask implements Runnable {
		/**
		 * 测试的方法
		 */
		abstract void test();

		/**
		 * 写入结果
		 */
		abstract void putResults();

		// 消耗的时间
		long duration;

		@Override
		public void run() {
			// 记录开始的时间
			long startTime = System.nanoTime();
			// 进行测试
			test();
			// 获取消耗的时间
			duration = System.nanoTime() - startTime;
			// 同步, 记录结果
			synchronized (Tester.this) {
				putResults();
			}
			// 当前任务结束, 计数锁减1
			endLatch.countDown();
		}
	}

	public static void initMain(String[] args) {
		if (args.length > 0) {
			testReps = new Integer(args[0]);
		}
		if (args.length > 1) {
			testCycles = new Integer(args[1]);
		}
		if (args.length > 2) {
			containerSize = new Integer(args[2]);
		}
		System.out.printf("%-27s %14s %14s\n", "Type", "Read time",
				"Write time");
	}

}
