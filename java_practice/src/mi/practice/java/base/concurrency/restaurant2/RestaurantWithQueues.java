package mi.practice.java.base.concurrency.restaurant2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import mi.practice.java.base.enumerated.menu.Course;
import mi.practice.java.base.enumerated.menu.Food;
/**
 * 
 */

/**
 * 单子
 * 顾客点的单子
 */
class Order {
	private static int counter = 0;
	private final int id = counter++;
	private final Customer customer; // 顾客
	private final WaitPerson waitPerson; // 服务员
	private final Food food; // 食物

	public Order(Customer cust, WaitPerson wp, Food f) {
		customer = cust;
		waitPerson = wp;
		food = f;
	}

	/**
	 * 得到单子上的食物
	 */
	public Food item() {
		return food;
	}

	/**
	 * 下单的顾客
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * 服务员
	 */
	public WaitPerson getWaitPerson() {
		return waitPerson;
	}

	@Override
	public String toString() {
		return "Order: " + id + " item: " + food + " served by: " + waitPerson;
	}
}

/**
 * ]
 * 盘子
 */
class Plate {
	private final Order order; // 哪个单子
	private final Food food; // 食物

	public Plate(Order order, Food food) {
		this.order = order;
		this.food = food;
	}

	public Order getOrder() {
		return order;
	}

	public Food getFood() {
		return food;
	}

	@Override
	public String toString() {
		return food.toString();
	}
}

/**
 * SynchronousQueue, 没有容量,
 * 每一次take()必须等待put(), put()也一样, 是相互阻塞等待的. cv
 * 1：它一种阻塞队列，其中每个 put 必须等待一个 take，反之亦然。
 * 同步队列没有任何内部容量，甚至连一个队列的容量都没有。
 * 2：它是线程安全的，是阻塞的。
 * 3:不允许使用 null 元素。
 * 4：公平排序策略是指调用put的线程之间，或take的线程之间。
 * 公平排序策略可以查考ArrayBlockingQueue中的公平策略
 */
/**
 * 顾客
 */
class Customer implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final WaitPerson waitPerson; // 服务员
	private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<Plate>(); // 一个盘子的队列

	/**
	 * 初始化顾客的时候需要给他分配一个服务员
	 * 
	 * @param waitPerson
	 */
	public Customer(WaitPerson waitPerson) {
		this.waitPerson = waitPerson;
	}

	/**
	 * 把做好的食物放在盘子中拿给客户
	 */
	public void deliver(Plate p) throws InterruptedException {
		// Only blocks if customer is still
		// eating the previous course:
		placeSetting.put(p);
	}

	@Override
	public void run() {
		for (Course course : Course.values()) {
			// 随机选择一种食物
			Food food = course.randomSelection();
			try {
				// 给客户下单
				waitPerson.placeOrder(this, food);
				// Blocks until course has been delivered:
				// 阻塞直到盘子上有东西
				System.out.println(this + "eating " + placeSetting.take());
			} catch (InterruptedException e) {
				System.out.println(this + "waiting for " + course
						+ " interrupted");
				break;
			}
		}
		System.out.println(this + "finished meal, leaving");
	}

	@Override
	public String toString() {
		return "Customer " + id + " ";
	}
}

class WaitPerson implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final Restaurant restaurant; // 餐厅
	// 盘子的队列, 放着厨师做出来的食物, 每一个服务员一个队列
	BlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<Plate>();

	public WaitPerson(Restaurant rest) {
		restaurant = rest;
	}

	/**
	 * 给客户下单
	 */
	public void placeOrder(Customer cust, Food food) {
		try {
			// Shouldn't actually block because this is
			// a LinkedBlockingQueue with no size limit:
			// 把单子, 记录的食物和顾客放入餐厅的order队列中去
			// 一般不会阻塞, 因为队列是一个LinkedBlockingQueue
			restaurant.orders.put(new Order(cust, this, food));
		} catch (InterruptedException e) {
			System.out.println(this + " placeOrder interrupted");
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until a course is ready
				// 阻塞知道有客户下单
				// 拿到一个盘子
				Plate plate = filledOrders.take();
				System.out.println(this + "received " + plate
						+ " delivering to " + plate.getOrder().getCustomer());
				// 给用户盘子
				plate.getOrder().getCustomer().deliver(plate);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
		System.out.println(this + " off duty");
	}

	@Override
	public String toString() {
		return "WaitPerson " + id + " ";
	}
}

/**
 * 厨师
 */
class Chef implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private final Restaurant restaurant;
	private static Random rand = new Random(47);

	public Chef(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Blocks until an order appears:
				// 阻塞直到有顾客下单
				Order order = restaurant.orders.take();
				// 根据单子制造食物
				Food requestedItem = order.item();
				// Time to prepare order:
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
				// 把做好的食物放到盘子中去
				Plate plate = new Plate(order, requestedItem);
				// 叫服务员来拿走盘子
				order.getWaitPerson().filledOrders.put(plate);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " interrupted");
		}
		System.out.println(this + " off duty");
	}

	@Override
	public String toString() {
		return "Chef " + id + " ";
	}
}

class Restaurant implements Runnable {
	private List<WaitPerson> waitPersons = new ArrayList<WaitPerson>(); // 服务员
	private List<Chef> chefs = new ArrayList<Chef>(); // 厨师
	private ExecutorService exec;
	private static Random rand = new Random(47);
	BlockingQueue<Order> orders = new LinkedBlockingQueue<Order>(); // 单子的队列

	public Restaurant(ExecutorService exec, int nWaitPerson, int nChefs) {
		this.exec = exec;
		// 启动好服务员
		for (int i = 0; i < nWaitPerson; i++) {
			WaitPerson waitPerson = new WaitPerson(this);
			waitPersons.add(waitPerson);
			exec.execute(waitPerson);
		}
		// 启动好厨师
		for (int i = 0; i < nChefs; i++) {
			Chef chef = new Chef(this);
			chefs.add(chef);
			exec.execute(chef);
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// A new customer arrives; assign a WaitPerson:
				// 随机拿一个服务员
				WaitPerson wp = waitPersons
						.get(rand.nextInt(waitPersons.size()));
				// 生成一个客户
				Customer c = new Customer(wp);
				// 然顾客去下单
				exec.execute(c);
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
			System.out.println("Restaurant interrupted");
		}
		System.out.println("Restaurant closing");
	}

}

public class RestaurantWithQueues {
	public static void main(String[] args) throws NumberFormatException,
			InterruptedException, IOException {
		ExecutorService exec = Executors.newCachedThreadPool();
		Restaurant restaurant = new Restaurant(exec, 5, 2);
		exec.execute(restaurant);
		if (args.length > 0) {
			TimeUnit.SECONDS.sleep(new Integer(args[0]));
		} else {
			System.out.println("Press 'Enter' to quit");
			System.in.read();
		}
		exec.shutdownNow();
	}
}