package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 一个厨师和一个服务员的故事:
 * 厨师负责做料理, 然后叫服务员去送, 等服务员拿走了料理才
 * 开始做下一个.
 * 服务员负责送料理, 拿走了以后通知厨师可以开始做下一个了.
 * 使用wait()最好使用无限循环的形式,
 * 因为你不知道这个进程下一次会被谁叫醒, 而导致程序出错.
 * while(conditionIsNotMet){
 * wait();
 * }
 * ExecutorService.shutdownNow()会给进行发送一个interrupt,
 * 不过进程不会直接打断, 直到进入可以被打断的块, 比如sleep(), wait();
 */
class Meal {
	private final int orderNum;

	public Meal(int orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public String toString() {
		return "Meal " + orderNum;
	}
}

class WaitPerson implements Runnable {
	private Restaurant restaurant;

	public WaitPerson(Restaurant r) {
		restaurant = r;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (restaurant.meal == null) {
						wait(); // ... for the chef to produce a meal
					}
				}
				System.out.println("WaitPerson got " + restaurant.meal);
				// 叫厨师先不要动, 等我送了菜叫你们
				synchronized (restaurant.chef) {
					restaurant.meal = null;
					restaurant.chef.notifyAll();
				}
			}
		} catch (InterruptedException e) {
			System.out.println("WaitPerson interrupted");
		}

	}

}

class Chef implements Runnable {
	private Restaurant restaurant;
	private int count;

	public Chef(Restaurant r) {
		restaurant = r;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (restaurant.meal != null) {
						wait(); // ... for the meal to be taken
					}
				}
				// only make ten meals
				if (++count == 10) {
					System.out.println("Out of food, closing");
					restaurant.exec.shutdownNow(); // interrupt
				}
				System.out.println("Order up!");
				// 叫那些服务员先不要动, 厨师开始做东西了
				synchronized (restaurant.waitPerson) {
					restaurant.meal = new Meal(count);
					restaurant.waitPerson.notifyAll();
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Chef interrupted");
		}
	}
}

public class Restaurant {
	Meal meal;
	ExecutorService exec = Executors.newCachedThreadPool();
	WaitPerson waitPerson = new WaitPerson(this);
	Chef chef = new Chef(this);

	public Restaurant() {
		exec.execute(chef);
		exec.execute(waitPerson);
	}

	public static void main(String[] args) {
		new Restaurant();
	}
}
