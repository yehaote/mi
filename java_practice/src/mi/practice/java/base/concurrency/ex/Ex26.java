package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
				// 叫bugboy去清理
				synchronized (restaurant.busBoy) {
					restaurant.meal = null;
					restaurant.cleanUp = false;
					restaurant.busBoy.notifyAll();
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
					while (restaurant.meal != null
							&& restaurant.cleanUp == false) {
						wait(); // ... for the meal to be taken and clean up
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

class BusBoy implements Runnable {
	private Restaurant restaurant;

	public BusBoy(Restaurant r) {
		restaurant = r;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					// 如果是干净的就不用擦
					while (restaurant.cleanUp == true) {
						wait();
					}
					System.out.println("Clean up");
					synchronized (restaurant.chef) {
						restaurant.cleanUp = true;
						restaurant.chef.notifyAll();
					}
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Bugboy interrupt");
		}

	}

}

class Restaurant {
	Meal meal;
	boolean cleanUp = true;

	ExecutorService exec = Executors.newCachedThreadPool();
	WaitPerson waitPerson = new WaitPerson(this);
	Chef chef = new Chef(this);
	BusBoy busBoy = new BusBoy(this);

	public Restaurant() {
		exec.execute(chef);
		exec.execute(waitPerson);
		exec.execute(busBoy);
	}

}

public class Ex26 {
	public static void main(String[] args) {
		new Restaurant();
	}
}
