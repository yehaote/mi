package mi.practice.java.base.concurrency;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * 死锁在并发编程中是会经常碰到的一个bug.
 * 简单可以理解成当多个线程之间都在互相等待对方的时候就是一个死锁.
 * 最麻烦的时候很多时候死锁这种bug是隐藏在程序之中的,
 * 你不知道它什么时候会发作, 如果让你的用户体验到这种bug,
 * 那将是一件多么不和谐的事情, 而且这种错误有的时候真的很难重现.
 * 
 * 示例:有很多哲学家他们一会儿思考, 一会儿吃东西, 
 * 可是吃东西的时候需要两只筷子, 左手一支, 右手一支.
 * 但是筷子是不够的, 当筷子不够的时候就等其他的哲学家吃完, 
 * 然后从他手中拿到筷子. 
 * 
 * 这里会出现死锁的情况是哲学家都拿了一只筷子, 
 * 然后等待对方吃完.....  无限等待......
 * 
 * 如果哲学家思考的时间很短越容易出现死锁.
 * 
 * 这个程序很好玩的地方是如果他们思考的时间的参数够大的话,
 * 其实是很难出现死锁的, 让我们觉得它能正常运行一样,
 * 虽说其实是有可能会出现死锁的. 
 */
public class DeadlockingDiningPhilosophers {
	public static void main(String[] args) throws InterruptedException,
			IOException {
		int ponder = 5;
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
			exec.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1)
					% size], i, ponder));
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
