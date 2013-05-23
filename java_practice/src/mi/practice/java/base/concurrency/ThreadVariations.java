package mi.practice.java.base.concurrency;

import static mi.practice.java.base.util.Print.*;

import java.util.concurrent.TimeUnit;

/***
 * 演示Thread的几种实现办法
 */
class InnerThread1 {
	private int countDown = 5;
	@SuppressWarnings("unused")
	private Inner inner;

	private class Inner extends Thread {
		Inner(String name) {
			super(name);
			start();
		}

		@Override
		public void run() {
			try {
				while (true) {
					print(this);
					if (--countDown == 0) {
						return;
					}
					sleep(10);
				}
			} catch (InterruptedException e) {
				print("Interrupted");
			}
		}

		@Override
		public String toString() {
			return getName() + " : " + countDown;
		}
	}

	public InnerThread1(String name) {
		inner = new Inner(name);
	}

}

class InnerThread2 {
	private int countDown = 5;
	private Thread t;

	public InnerThread2(String name) {
		t = new Thread(name) {
			@Override
			public void run() {
				try {
					while (true) {
						print(this);
						if (--countDown == 0) {
							return;
						}
						sleep(10);
					}
				} catch (InterruptedException e) {
					print("sleep() Interrupted");
				}
			}

			@Override
			public String toString() {
				return getName() + ": " + countDown;
			}
		};
		t.start();
	}
}

class InnerRunnable1 {
	private int countDown = 5;
	@SuppressWarnings("unused")
	private Inner inner;

	private class Inner implements Runnable {
		Thread t;

		Inner(String name) {
			t = new Thread(this, name);
			t.start();
		}

		@Override
		public void run() {
			while (true) {
				try {
					print(this);
					if (--countDown == 0) {
						return;
					}
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					print("sleep() interrupted");
				}
			}
		}

		@Override
		public String toString() {
			return t.getName() + ": " + countDown;
		}
	}

	public InnerRunnable1(String name) {
		inner = new Inner(name);
	}
}

class InnerRunnable2 {
	private int countDown = 5;
	private Thread t;

	public InnerRunnable2(String name) {
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						print(this);
						if (--countDown == 0) {
							return;
						}
						TimeUnit.MILLISECONDS.sleep(10);
					}
				} catch (InterruptedException e) {
					print("sleep() interrupted");
				}
			}

			@Override
			public String toString() {
				return Thread.currentThread().getName() + ": " + countDown;
			}
		}, name);
		t.start();
	}
}

class ThreadMethod {
	private int countDown = 5;
	private Thread t;
	private String name;

	public ThreadMethod(String name) {
		this.name = name;
	}

	public void runTask() {
		if (t == null) {
			t = new Thread(name) {
				@Override
				public void run() {
					try {
						while (true) {
							print(this);
							if (--countDown == 0) {
								return;
							}
							TimeUnit.MILLISECONDS.sleep(10);
						}
					} catch (InterruptedException e) {
						print("sleep() interrupted");
					}
				}

				@Override
				public String toString() {
					return getName() + ": " + countDown;
				}
			};
		}
		t.start();
	}
}

public class ThreadVariations {
	public static void main(String[] args) {
		new InnerThread1("InnerThread1");
		new InnerThread2("InnerThread2");
		new InnerRunnable1("InnerRunnable1");
		new InnerRunnable2("InnerRunnable2");
		new ThreadMethod("ThreadMethod").runTask();
	}
}
