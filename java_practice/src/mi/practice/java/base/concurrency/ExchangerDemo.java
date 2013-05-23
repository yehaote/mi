package mi.practice.java.base.concurrency;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import mi.practice.java.base.util.BasicGenerator;
import mi.practice.java.base.util.Generator;

/**
 * Exchanger,
 * 两个线程之间等待对方exchanger.
 * 当两个线程exchange()以后阻塞等待对方, 
 * 等待完以后大家互相交换对象.
 * #: 要使用同一个exchanger对象.
 * 
 * CopyOnWriteArrayList
 * 可以理解成可以边读边写的List, 
 * 读写的效率好像比ArrayList要高,
 * for (T x : holder) {
 * 	holder.remove(x);
 * }
 * 像这种形式在ArrayList中是不被允许的.
 */
/**
 * Exchanger产生器
 * 添加10个元素到holder,
 * 并通过exchanger交换holder
 */
class ExchangerProducer<T> implements Runnable {
	private Generator<T> generator;
	private Exchanger<List<T>> exchanger;
	private List<T> holder;

	ExchangerProducer(Exchanger<List<T>> exchg, Generator<T> gen, List<T> holder) {
		exchanger = exchg;
		generator = gen;
		this.holder = holder;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				for (int i = 0; i < ExchangerDemo.size; i++) {
					holder.add(generator.next());
				}
				// Exchange full for empty:
				// 交换线程间的holder, consumer的holder都是空的
				// exchanger执行exchanger的时候会等待另外的进程执行exchanger
				holder = exchanger.exchange(holder);
			}
		} catch (InterruptedException e) {
			// Ok to terminate this way.
		}
	}

}

class ExchangerConsumer<T> implements Runnable {
	private Exchanger<List<T>> exchanger;
	private List<T> holder;
	private volatile T value;

	ExchangerConsumer(Exchanger<List<T>> ex, List<T> holder) {
		exchanger = ex;
		this.holder = holder;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 交换holder, 其实当前holder都是空的
				holder = exchanger.exchange(holder);
				// 获取所有的信息
				for (T x : holder) {
					// 读取并删除所有的元素
					value = x;// Fetch out value
					holder.remove(x); // OK for CopyOnWriteArrayList
				}
				// 到最后的时候holder是空的
			}
		} catch (InterruptedException e) {
			// OK to terminate this way.
		}
		System.out.println("Final value: " + value);
	}

}

public class ExchangerDemo {
	static int size = 10;
	static int delay = 5; // Seconds

	public static void main(String[] args) throws InterruptedException {
		if (args.length > 0) {
			size = new Integer(args[0]);
		}
		if (args.length > 1) {
			delay = new Integer(args[1]);
		}
		ExecutorService exec = Executors.newCachedThreadPool();
		// 两个线程使用同一个Exchanger
		Exchanger<List<Fat>> xc = new Exchanger<List<Fat>>();
		// 不同的CopyOnWriteArrayList给不同的线程
		List<Fat> producerList = new CopyOnWriteArrayList<Fat>(), consumerList = new CopyOnWriteArrayList<Fat>();
		exec.execute(new ExchangerProducer<Fat>(xc, BasicGenerator
				.create(Fat.class), producerList));
		exec.execute(new ExchangerConsumer<Fat>(xc, consumerList));
		TimeUnit.SECONDS.sleep(delay);
		exec.shutdownNow();
	}
}
