package mi.practice.java.base.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mi.practice.java.base.util.CountingIntegerList;

/**
 * List读写测试抽象类
 * 在大多数情况下CopyOnWriteArrayList要比SynchronizedArrayLsit要好一些,
 * 在数组达到一定的数量级的时候才会有区别
 */
abstract class ListTest extends Tester<List<Integer>> {
	ListTest(String testId, int nReaders, int nWriters) {
		super(testId, nReaders, nWriters);
	}

	/**
	 * 实现了StartReaderAndWriters但是没有实现initializeContainer,
	 * 因为这个方法是通用的, 而初始化容器会有不同
	 */
	@Override
	void startReadersAndWriters() {
		for (int i = 0; i < nReaders; i++) {
			exec.execute(new Reader());
		}
		for (int i = 0; i < nWriters; i++) {
			exec.execute(new Writer());
		}
	}

	class Reader extends TestTask {
		long result;

		@Override
		void test() {
			// 循环容器testCycles次,
			// 读取所有的元素并累加放入result中
			for (int i = 0; i < testCycles; i++) {
				for (int index = 0; index < containerSize; index++) {
					result += testContainer.get(index);
				}
			}
		}

		@Override
		void putResults() {
			// 把result中的数据放入Tester的readResult中
			readResult += result;
			// 记录总共消耗的时间
			readTime += duration;
		}
	}

	class Writer extends TestTask {
		@Override
		void test() {
			for (int i = 0; i < testCycles; i++) {
				for (int index = 0; index < containerSize; index++) {
					testContainer.set(index, writeData[index]);
				}
			}

		}

		@Override
		void putResults() {
			writeTime += duration;
		}

	}
}

/**
 * 使用同步机制的同步List,
 * Collections的静态方法返回,
 * Java 1.2
 */
class SynchronizedArrayListTest extends ListTest {
	SynchronizedArrayListTest(int nReaders, int nWriters) {
		super("Synched ArrayList", nReaders, nWriters);
	}

	@Override
	List<Integer> containerInitializer() {
		// CountingIntegerList提供数据, CountingIntegerList使用Index作为当前index的值
		// Collections.synchronizedList()根据提供的list是否支持随机访问返回不同的实现
		// SynchronizedRandomAccessList OR SynchronizedList
		return Collections.synchronizedList(new ArrayList<Integer>(
				new CountingIntegerList(containerSize)));
	}

}

/**
 * 不是使用锁来控制的同步List,
 * Java 1.5
 */
class CopyOnWriteListArrayListTest extends ListTest {

	CopyOnWriteListArrayListTest(int nReaders, int nWriters) {
		super("CopyOnWriteArrayList", nReaders, nWriters);
	}

	@Override
	List<Integer> containerInitializer() {
		return new CopyOnWriteArrayList<Integer>(new CountingIntegerList(
				containerSize));
	}

}

public class ListComparisons {
	public static void main(String[] args) {
		Tester.initMain(args);
		new SynchronizedArrayListTest(10, 0);
		new SynchronizedArrayListTest(9, 1);
		new SynchronizedArrayListTest(5, 5);
		new CopyOnWriteListArrayListTest(10, 0);
		new CopyOnWriteListArrayListTest(9, 1);
		new CopyOnWriteListArrayListTest(5, 5);
		Tester.exec.shutdown();
	}
}