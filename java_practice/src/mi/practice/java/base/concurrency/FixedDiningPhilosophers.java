package mi.practice.java.base.concurrency;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 产生死锁的四个条件:
 * 1. 有互斥的冲突, 当一个资源被使用的时候是不能被分享的.
 * 2. 至少有一个任务必须在获得一个资源以后还需要去等待其他的资源.
 * 3. 资源不能被其他的任务拿走, 在获得而没有进行操作(同时获取到两个资源后操作)之前.
 * 4. 第一个等待下一个, 下一个再到下一个, 然后有一个等待第一个.(可以理解成没有等待的环路)
 */
public class FixedDiningPhilosophers {
	public static void main(String[] args) throws InterruptedException,
			IOException {
		int ponder = 0;
		if (args.length > 0) {
			ponder = Integer.parseInt(args[0]);
		}
		int size = 5;
		if (args.length > 1) {
			size = Integer.parseInt(args[1]);
		}
		ExecutorService exec = Executors.newCachedThreadPool();
		Chopstick[] chopsticks = new Chopstick[size];
		for (int i = 0; i < size; i++) {
			chopsticks[i] = new Chopstick();
		}
		for (int i = 0; i < size; i++) {
			// 主要看这里, 当哲学家要拿起最后那一把筷子的时候告诉他先拿第一把,
			// 这样让环路消失
			if (i < (size - 1)) {
				exec.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1)
						% size], i, ponder));
			} else {
				exec.execute(new Philosopher(chopsticks[0], chopsticks[i], i,
						ponder));
			}

		}
		if (args.length == 3 && args[2].equals("timeout")) {
			TimeUnit.SECONDS.sleep(5);
		} else {
			System.out.println("Press 'Enter' to quit");
			System.in.read();
		}
		exec.shutdownNow();
	}
}
