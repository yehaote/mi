package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 因为boolean是原子的(不可分割的),所以不能看到值在改变的时候的中间量
 */
public class EvenChecker implements Runnable {
	private IntGenerator generator;
	@SuppressWarnings("unused")
	private final int id;

	public EvenChecker(IntGenerator g, int ident) {
		generator = g;
		id = ident;
	}

	@Override
	public void run() {
		// 当generator没有被canceled的时候一直执行
		while (!generator.isCanceled()) {
			// 返回next取回下一个int
			int val = generator.next();
			if (val % 2 != 0) {
				System.out.println(val + " not even!");
				generator.cancel(); // Cancels all EvenCheckers
			}
		}
	}

	public static void test(IntGenerator gp, int count) {
		System.out.println("Press Control-C to exit");
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++) {
			// 起多个EvenChecker
			// 多线程共用一个IntGenerator
			exec.execute(new EvenChecker(gp, i));
		}
		exec.shutdown();
	}

	public static void test(IntGenerator gp) {
		test(gp, 10);
	}
}
