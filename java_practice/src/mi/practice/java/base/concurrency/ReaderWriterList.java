package mi.practice.java.base.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock, 包含一个读锁和一个写锁.
 * 读锁, 在尝试获取锁的时候会去查看当前是否有写锁锁着了,
 * 如果有写锁被其他线程锁着的话就等待直到获取锁.
 * 写锁. 在尝试获取写锁的时候, 如果当前其他线程都没有占用读锁或者写锁的话,
 * 直接返回, 并将写锁的count设置为1. 如果当前线程已经占用写锁的情况下, count加1,
 * 直接返回. 如果其他线程占用着写锁或者读锁的话, 等待直到获取锁, 并将count置为1.
 * 简单一点说:
 * 读锁: 在当前没有写锁的状态下可以获取, 跟写锁冲突, 跟读锁不冲突
 * 写锁: 在当前没有写锁或者读锁的状态下可以获取, 跟读锁写锁都冲突
 * ReadWriteLocks优化的是相对于写入操作, 读取操作要多很多的情况.
 * 采用读写锁对性能上的影响, 最终可能只有测试过才知道是否有益.
 */

public class ReaderWriterList<T> {
	private ArrayList<T> lockedList;
	// Make the ordering fair;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	public ReaderWriterList(int size, T initialValue) {
		lockedList = new ArrayList<T>(Collections.nCopies(size, initialValue));
	}

	public T set(int index, T element) {
		Lock wlock = lock.writeLock();
		wlock.lock();
		try {
			return lockedList.set(index, element);
		} finally {
			wlock.unlock();
		}
	}

	public T get(int index) {
		Lock rlock = lock.readLock();
		rlock.lock();
		try {
			// 可以同时多次获得读锁
			if (lock.getReadLockCount() > 1) {
				System.out.println(lock.getReadLockCount());
			}
			return lockedList.get(index);
		} finally {
			rlock.unlock();
		}
	}

	public static void main(String[] args) {
		new ReaderWriterListTest(30, 1);
	}
}

class ReaderWriterListTest {
	public ReaderWriterListTest(int nReaders, int nWriters) {
		for (int i = 0; i < nReaders; i++) {
			exec.execute(new Reader());
		}
		for (int i = 0; i < nWriters; i++) {
			exec.execute(new Writer());
		}
	}

	ExecutorService exec = Executors.newCachedThreadPool();
	private final static int SIZE = 100;
	private static Random rand = new Random(47);
	private ReaderWriterList<Integer> list = new ReaderWriterList<Integer>(
			SIZE, 0);

	private class Writer implements Runnable {
		@Override
		public void run() {
			try {
				for (int i = 0; i < 20; i++) {
					list.set(i, rand.nextInt());
					// 每次写入完休息100毫秒
					TimeUnit.MILLISECONDS.sleep(100);
				}
			} catch (InterruptedException e) {
				// 被打断的方式是可以接受的
			}
			System.out.println("Writer finished, shutting down");
			exec.shutdownNow();
		}
	}

	private class Reader implements Runnable {
		@Override
		public void run() {
			try {

				while (!Thread.interrupted()) {
					for (int i = 0; i < SIZE; i++) {
						list.get(i);
						// 每次读取完休息1毫秒
						TimeUnit.MILLISECONDS.sleep(1);
					}
				}
			} catch (InterruptedException e) {
				// 可以接受的打断
			}
		}
	}
}