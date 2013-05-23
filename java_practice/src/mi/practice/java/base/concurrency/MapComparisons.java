package mi.practice.java.base.concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import mi.practice.java.base.util.CountingGenerator;
import mi.practice.java.base.util.MapData;

/**
 * 增加写入的权重对ConcurrentHashMap的冲击想对于Collections.synchronizedMap来说
 * 要小很多.
 */
abstract class MapTest extends Tester<Map<Integer, Integer>> {

	MapTest(String testId, int nReaders, int nWriters) {
		super(testId, nReaders, nWriters);
	}

	class Reader extends TestTask {
		long result = 0;

		@Override
		void test() {
			for (int i = 0; i < testCycles; i++) {
				// 迭代读取数据
				for (int index = 0; index < containerSize; index++) {
					// 把读取的结果累加到result中
					result += testContainer.get(index);
				}
			}
		}

		@Override
		void putResults() {
			// 把累加和再次累加到Tester的readResult中
			readResult += result;
			// 记录下读取时间, 累加到Tester的readTime中
			readTime += duration;
		}

	}

	class Writer extends TestTask {

		@Override
		void test() {
			for (int i = 0; i < testCycles; i++) {
				// 迭代容器写入数据
				for (int index = 0; index < testContainer.size(); index++) {
					testContainer.put(index, writeData[index]);
				}
			}
		}

		@Override
		void putResults() {
			// 把消耗的时间累加到Tester的写入时间开销中去
			writeTime += duration;
		}

	}

	@Override
	void startReadersAndWriters() {
		for (int i = 0; i < nReaders; i++) {
			exec.execute(new Reader());
		}
		for (int i = 0; i < nWriters; i++) {
			exec.execute(new Writer());
		}
	}

}

class SynchronizedHashMapTest extends MapTest {

	SynchronizedHashMapTest(int nReaders, int nWriters) {
		super("Synched HashMap", nReaders, nWriters);
	}

	@Override
	Map<Integer, Integer> containerInitializer() {
		return Collections.synchronizedMap(new HashMap<Integer, Integer>(
				MapData.map(new CountingGenerator.Integer(),
						new CountingGenerator.Integer(), containerSize)));
	}

}

class ConcurrentHashMapTest extends MapTest {

	ConcurrentHashMapTest(int nReaders, int nWriters) {
		super("ConcurrentHashMap", nReaders, nWriters);
	}

	@Override
	Map<Integer, Integer> containerInitializer() {
		return new ConcurrentHashMap<Integer, Integer>(MapData.map(
				new CountingGenerator.Integer(),
				new CountingGenerator.Integer(), containerSize));
	}

}

public class MapComparisons {
	public static void main(String[] args) {
		Tester.initMain(args);
		new SynchronizedHashMapTest(10, 0);
		new SynchronizedHashMapTest(9, 1);
		new SynchronizedHashMapTest(5, 5);
		new ConcurrentHashMapTest(10, 10);
		new ConcurrentHashMapTest(9, 1);
		new ConcurrentHashMapTest(5, 5);
		Tester.exec.shutdown();
	}
}