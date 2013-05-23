package mi.practice.java.base.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 普通的锁, locks synchronized 每次都只允许一个人任务去访问资源.
 * 如果有一组资源的时候, 它们实现起来就会相对比较麻烦一些,
 * Counting Semaphore允许n个任务在同一时间去访问这个资源.
 * 下面这个例子据使用Semaphore来管理一组资源,
 * 获取一个资源使用checkOut(), 使用完以后使用checkIn()处理它(释放).
 */
public class Pool<T> {
	private int size;
	private List<T> items = new ArrayList<T>();
	private volatile boolean[] checkedOut;
	private Semaphore available;

	public Pool(Class<T> classObject, int size) {
		this.size = size;
		checkedOut = new boolean[size];
		// fair为true代表什么? 公平锁跟不公平锁
		available = new Semaphore(size, true);
		// Load pool with object that can be checked out
		for (int i = 0; i < size; i++) {
			// Assumes a default constructor:
			try {
				items.add(classObject.newInstance());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public T checkOut() throws InterruptedException {
		// 先通过Semaphore获得锁
		// 可以简单得理解成计数器加一, 如果超出限制就等待
		available.acquire();
		return getItem();
	}

	public void checkIn(T x) {
		if (releaseItem(x)) {
			// 可以简单得理解成计数器减一
			available.release();
		}
	}

	private synchronized T getItem() {
		// 循环数据数组, 如果取出没有checkOut的对象返回
		for (int i = 0; i < size; i++) {
			if (!checkedOut[i]) {
				checkedOut[i] = true;
				return items.get(i);
			}
		}
		return null; // semaphore prevents reaching here
		// 因为有semaphore来限制获取的数量, 所以一般不会返回null
	}

	private synchronized boolean releaseItem(T item) {
		// 根据对象查出在list中的index
		int index = items.indexOf(item);
		if (index == -1) {
			return false; // Not in the list
		}
		// 找到以后把checkedOut设置成false, 并返回true
		if (checkedOut[index]) {
			checkedOut[index] = false;
			return true;
		}
		return false;
	}
}
