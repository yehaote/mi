package mi.practice.java.base.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * CheckoutTask, checkOut元素等待一秒, 然后checkIn
 */
class CheckoutTask<T> implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private Pool<T> pool;

	public CheckoutTask(Pool<T> pool) {
		this.pool = pool;
	}

	@Override
	public void run() {
		try {
			T item = pool.checkOut();
			System.out.println(this + "checked out " + item);
			TimeUnit.SECONDS.sleep(1);
			System.out.println(this + "checking in " + item);
			pool.checkIn(item);
		} catch (InterruptedException e) {
			// Acceptable way to terminate
		}
	}

	@Override
	public String toString() {
		return "CheckoutTask " + id + " ";
	}

}

public class SemaphoreDemo {
	final static int SIZE = 25;

	public static void main(String[] args) throws InterruptedException {
		// 定义一个大小为25的Pool, Pool使用Semaphore管理数量
		final Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < SIZE; i++) {
			exec.execute(new CheckoutTask<Fat>(pool));
		}
		System.out.println("All CheckoutTasks created");
		// 取出所有的Fat, 执行操作并不checkIn
		// 这里可以发现, 下面的checkOut跟上面的checkIn是可能会有交替的
		List<Fat> list = new ArrayList<Fat>();
		for (int i = 0; i < SIZE; i++) {
			Fat f = pool.checkOut();
			System.out.print(i + ": main() thread checked out");
			f.operation();
			list.add(f);
		}
		// 因为资源已经被checkOut光了, 所以再去checkOut是会直接block的
		Future<?> blocked = exec.submit(new Runnable() {
			@Override
			public void run() {
				try {
					// Semaphore prevents additional checkOut,
					// so call is blocked:
					pool.checkOut();
				} catch (InterruptedException e) {
					System.out.println("checkOut() interrupted");
				}
			}
		});
		// 等待两秒打断无法获取到资源的blocked
		TimeUnit.SECONDS.sleep(2);
		blocked.cancel(true); // Break out of blocked call
		// 把checkOut的对象checkIn
		System.out.println("Checking in objects in " + list);
		for (Fat f : list) {
			pool.checkIn(f);
		}
		// 因为已经checkIn过一次, 所以不会有效果
		for (Fat f : list) { // Second checkIn ignored
			pool.checkIn(f);
		}
		exec.shutdown();
	}
}
