package mi.practice.java.base.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static mi.practice.java.base.util.Print.*;

/**
 * ExecutorService.awaitTermination()等待任务完成,
 * 如果在等待的时间里都终止了返回true,
 * 没有的话就返回false
 * 因为canceled是volatile而且只有被赋值和读取,
 * 所以synchronized是不必要的
 */
class Count {
	private int count = 0;
	private Random rand = new Random(47);

	// 这里没有同步的话会导致线程间的异常
	public synchronized int increment() {
		int temp = count;
		if (rand.nextBoolean()) {
			Thread.yield();
		}
		return (count = ++temp);
	}

	public synchronized int value() {
		return count;
	}
}

class Entrance implements Runnable {
	// 共有计数器
	private static Count count = new Count();
	// 引用集合
	private static List<Entrance> entrances = new ArrayList<Entrance>();
	private static volatile boolean canceled = false;

	public static void cancel() {
		canceled = true;
	}

	public static int getTotalCount() {
		return count.value();
	}

	public static int sumEntracnes() {
		int sum = 0;
		for (Entrance entrance : entrances) {
			sum += entrance.getValue();
		}
		return sum;
	}

	// 私有计数器
	private int number = 0;
	private final int id;

	public Entrance(int id) {
		this.id = id;
		entrances.add(this);
	}

	@Override
	public void run() {
		while (!canceled) {
			synchronized (this) {
				++number;
			}
			print(this + " Total: " + count.increment());
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				print("sleep interrupted");
			}
		}
		print("Stopping " + this);
	}

	public synchronized int getValue() {
		return number;
	}

	@Override
	public String toString() {
		return "Entrance " + id + ": " + getValue();
	}
}

public class OrnamentalGarden {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new Entrance(i));
		}
		TimeUnit.SECONDS.sleep(3);
		Entrance.cancel();
		exec.shutdown();
		if (!exec.awaitTermination(150, TimeUnit.MILLISECONDS)) {
			print("Some task were not terminated!");
		}
		print("Total: " + Entrance.getTotalCount());
		print("sum of Entrances: " + Entrance.sumEntracnes());

	}
}
