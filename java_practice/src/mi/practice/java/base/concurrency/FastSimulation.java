package mi.practice.java.base.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 优化锁
 * Atomic锁除了提供基本的集合类操作以外还可以用来做锁,
 * 一种叫"optimistic locking"的锁.
 * Atomic中有一个方法叫compareAndSet(), 可以先查看值是否已经发生变化,
 * 然后如果没有变化的再设置值.
 * 如果使用这个方法来代替synchronized或者Lock的话会有效率上的提升.
 * 不过当跟旧值比较失败的时候, 剩下的逻辑就要自己实现了.
 * 一般来说不需要使用这个方案, 只有当锁成为一个性能上的瓶颈的时候可以考虑一下.
 */
public class FastSimulation {
	static final int N_ELEMENTS = 100000;
	static final int N_GENES = 30;
	static final int N_EVOLVERS = 50;
	static final AtomicInteger[][] GRID = new AtomicInteger[N_ELEMENTS][N_GENES];
	static Random rand = new Random(47);

	static class Evolver implements Runnable {
		@Override
		public void run() {
			while (!Thread.interrupted()) {
				// 随机选择一个元素去操作
				int element = rand.nextInt(N_ELEMENTS);
				for (int i = 0; i < N_GENES; i++) {
					// 前一个
					int previous = element - 1;
					if (previous < 0) {
						previous = N_ELEMENTS - 1;
					}
					// 后一个
					int next = element + 1;
					if (next >= N_ELEMENTS) {
						next = 0;
					}
					int oldvalue = GRID[element][i].get();
					// 进行一些计算
					int newvalue = oldvalue + GRID[previous][i].get()
							+ GRID[next][i].get();
					newvalue = newvalue / 3;
					// 对比并设置值, 替代一般的锁技术
					if (!GRID[element][i].compareAndSet(oldvalue, newvalue)) {
						// 如果值已经发生变化, 设置失败的时候
						// 这里直接忽略它
						System.out
								.println("Old value changed from " + oldvalue);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < N_ELEMENTS; i++) {
			for (int j = 0; j < N_GENES; j++) {
				GRID[i][j] = new AtomicInteger(rand.nextInt(100));
			}
		}
		for (int i = 0; i < N_EVOLVERS; i++) {
			exec.execute(new Evolver());
		}
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}
