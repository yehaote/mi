package mi.practice.java.base.concurrency.ex;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import mi.practice.java.base.concurrency.Chopstick;
/**
 * 当哲学家把用完的筷子放到一个池子中的时候,
 * 如果是大家都一只拿的话也会出现死锁的.(当chopstickNum<=chopstickNum)
 */
class Philosopher implements Runnable {
	private Chopstick left;
	private Chopstick right;
	private final int id;
	private int ponderFactor;
	private Random rand = new Random(47);
	private BlockingQueue<Chopstick> bin;

	public Philosopher(int ident, int ponder, BlockingQueue<Chopstick> bin) {
		id = ident;
		ponderFactor = ponder;
		this.bin = bin;
	}

	private void pause() throws InterruptedException {
		if (ponderFactor == 0) {
			return;
		}
		TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println(this + " " + "thinking");
				pause();
				// Philosopher becomes hungry
				System.out.println(this + " " + "grabbing right");
				// 从池子中拿其左手的筷子
				right = bin.take();
				right.take();
				System.out.println(this + " " + "grabbing left");
				// 从池子中拿右手的筷子
				left = bin.take();
				left.take();
				System.out.println(this + " " + "eating");
				pause();
				right.drop();
				// 放回池子中去
				bin.put(right);
				right=null;
				left.drop();
				// 放回池子中去
				bin.put(left);
				left=null;
			}
		} catch (InterruptedException e) {
			System.out.println(this + " " + "exiting via interrupt");
		}
	}

	@Override
	public String toString() {
		return "Philosopher " + id;
	}
}

public class Ex31 {
	public static void main(String[] args) throws InterruptedException,
			IOException {
		int ponder = 5;
		if (args.length > 0) {
			ponder = Integer.parseInt(args[0]);
		}
		int chopstickNum = 5;
		if (args.length > 1) {
			chopstickNum = Integer.parseInt(args[1]);
		}
		int philosopherNum = 5;
		if (args.length > 2) {
			philosopherNum = Integer.parseInt(args[1]);
		}
		ExecutorService exec = Executors.newCachedThreadPool();
		BlockingQueue<Chopstick> bin = new LinkedBlockingQueue<Chopstick>();
		for (int i = 0; i < chopstickNum; i++) {
			bin.put(new Chopstick());
		}
		for (int i = 0; i < philosopherNum; i++) {
			exec.execute(new Philosopher(i, ponder,bin));
		}
		if (args.length == 4 && args[3].equals("timeout")) {
			TimeUnit.SECONDS.sleep(5);
		} else {
			System.out.println("Press 'Enter' to quit");
			System.in.read();
		}
		exec.shutdownNow();
	}
}
