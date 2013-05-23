package mi.practice.java.base.concurrency;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Concurrent Object: 并发对象
 * 并发对象指的是这个对象同时在好几个线程中被调用.
 * active object 又叫做 actors
 * 每一个对象维持他自己的线程, 并且所有对这个对象的调用都被封装成消息,
 * 每个对象有一个自己的消息队列负责接受消息,
 * 这些消息被逐个执行.
 * 在Active object模式中:
 * 我们序列化消息, 而不是直接调用对象的方法. 从而避免当前的方法调用在中途被
 * 打断以后所产生的错误.
 * Active object的核心是:
 * 让方法的调用和运行解耦合, 让调用放生在调用的线程中, 而执行放到具体的执行线程中去.
 * 有一点需要注意的是, 下面这个请求中的参数, 必须是只读的, 或者是其他的active object,
 * 或者是没有被引用的独立对象.
 * 当两个对象通过消息来进行交流的时候, active object每次只处理一个请求, 这样就没有了资源竞争,
 * 当然也就没有死锁. 整体流程都不是阻塞的.
 */
public class ActiveObjectDemo {
	private ExecutorService exec = Executors.newSingleThreadExecutor();
	private Random rand = new Random(47);

	private void pause(int factor) {
		try {
			TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(factor));
		} catch (InterruptedException e) {
			System.out.println("sleep interrupted");
		}
	}

	public Future<Integer> caculateInt(final int x, final int y) {
		return exec.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("Staring " + x + " + " + y);
				pause(500);
				return x + y;
			}
		});
	}

	public Future<Float> caculateFloat(final float x, final float y) {
		return exec.submit(new Callable<Float>() {
			@Override
			public Float call() throws Exception {
				System.out.println("Starting " + x + " + " + y);
				pause(2000);
				return x + y;
			}
		});
	}

	public void shutdown() {
		exec.shutdown();
	}

	public static void main(String[] args) {
		ActiveObjectDemo demo = new ActiveObjectDemo();
		// 为了支持同时读写
		List<Future<?>> result = new CopyOnWriteArrayList<Future<?>>();
		for (float f = 0.0f; f < 1.0f; f += 0.2f) {
			result.add(demo.caculateFloat(f, f));
		}
		for (int i = 0; i < 5; i++) {
			result.add(demo.caculateInt(i, i));
		}
		// 异步调用执行完毕
		System.out.println("All asynch calls made");
		// 循环读取数据, 直到所有请求就执行完毕
		while (result.size() > 0) {
			for (Future<?> f : result) {
				if (f.isDone()) {
					try {
						System.out.println(f.get());
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					result.remove(f);
				}
			}
		}
		demo.shutdown();
	}
}
