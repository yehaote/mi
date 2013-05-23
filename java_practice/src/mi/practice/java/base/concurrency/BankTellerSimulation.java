package mi.practice.java.base.concurrency;

import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Bank Teller(银行支票员模拟)模拟
 * 每一位客户所消耗的时间是随机的,
 * 一段间隔时间内来的客户数量也是随机的.
 * 当一个系统会反应过快的时候, 往往很有可能是不稳定的.
 */
/**
 * 顾客对象, 因为是只读, 所以不需要synchronized
 */
class Customer {
	// 需要服务的时间
	private final int serviceTime;

	public Customer(int tm) {
		serviceTime = tm;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	@Override
	public String toString() {
		return "[" + serviceTime + "]";
	}
}

/**
 * 顾客排的线, 所有的顾客都排在这条线上,
 * 一个Customer的BloclingQueue.
 */
class CustomerLine extends ArrayBlockingQueue<Customer> {
	private static final long serialVersionUID = -4270797082637451796L;

	public CustomerLine(int maxLineSize) {
		super(maxLineSize);
	}

	@Override
	public String toString() {
		if (this.size() == 0) {
			return "[Empty]";
		}
		StringBuilder result = new StringBuilder();
		for (Customer customer : this) {
			result.append(customer);
		}
		return result.toString();
	}
}

/**
 * 产生顾客的执行器,
 * 0-299ms产生一位顾客,
 * 顾客的serverTime为0-999ms.
 */
class CustomerGenerator implements Runnable {
	private CustomerLine customers;
	private static Random rand = new Random(47);

	public CustomerGenerator(CustomerLine cq) {
		customers = cq;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
				customers.put(new Customer(rand.nextInt(1000)));
			}
		} catch (InterruptedException e) {
			System.out.println("CustomerGenerator interrupted");
		}
		System.out.println("CustomerGenerator terminating");
	}

}

/**
 * 支票员
 */
class Teller implements Runnable, Comparable<Teller> {
	private static int counter = 0;
	private final int id = counter++; // 当前支票员的id
	// Customers served during this shift
	private int customersServed = 0; // 服务过的客户数
	private CustomerLine customers; // 客户排队的队列
	private boolean servingCustomerLine = true; // 是否正在服务

	public Teller(CustomerLine cq) {
		customers = cq;
	}

	// Used by priority queue:
	// 服务地越多, 优先级越高?
	@Override
	public synchronized int compareTo(Teller other) {
		return customersServed < other.customersServed ? -1
				: customersServed == other.customersServed ? 0 : 1;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 从队列里拿出一位客户
				Customer customer = customers.take();
				// 服务客户
				TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
				synchronized (this) {
					// 服务完的客户数++
					customersServed++;
					// 如果不需要继续服务的话, 进行等待
					while (!servingCustomerLine) {
						wait();
					}
				}
			}
		} catch (InterruptedException e) {
			System.out.println(this + "interrupted");
		}
		System.out.println(this + "terminating");
	}

	/**
	 * 叫他去做点其他的事情, 不在服务状态.
	 * 这样他的run()方法就会进入wait的状态
	 * 清空服务数.
	 */
	public synchronized void doSomethingElse() {
		customersServed = 0;
		servingCustomerLine = false;
	}

	/**
	 * 叫他去服务客户, 如果已经在服务了断言提示.
	 * 没有在服务的话, 唤醒.
	 * notifyAll()的时候所有在wait状态的teller都会被唤醒,
	 * 只有状态已经切换回来的才会真正去做事情,
	 * 没有的继续进入wait状态.
	 */
	public synchronized void serveCustomerLine() {
		assert !servingCustomerLine : "Already serving: " + this;
		servingCustomerLine = true;
		notifyAll();
	}

	@Override
	public String toString() {
		return "Teller " + id + " ";
	}

	public String shortString() {
		return "T" + id;
	}
}

/**
 * Teller的管理器
 * 通过当前的等待人数和服务人数调整工作人员的状态和数目
 */
class TellerManager implements Runnable {
	private ExecutorService exec; // 线程执行器
	private CustomerLine customers; // 客户等待的队列
	// 当前在工作的员工数, 如果服务过越多的员工越优先得到
	private PriorityQueue<Teller> workingTellers = new PriorityQueue<Teller>();
	// 当前在做其他事情的员工, 可以理解成是在休息的员工数
	private Queue<Teller> tellersDoingOtherThings = new LinkedList<Teller>();
	// 调整的周期
	private int adjustmentPeriod;

	public TellerManager(ExecutorService exec, CustomerLine customers,
			int adjustmentPeriod) {
		this.exec = exec;
		this.customers = customers;
		this.adjustmentPeriod = adjustmentPeriod;
		// Start with a single teller:
		// 开始的时候先放一个Teller到服务的队列中
		Teller teller = new Teller(customers);
		// 抛出一个线程去执行Teller的动作
		exec.execute(teller);
		workingTellers.add(teller);
	}

	/**
	 * 进行调整
	 */
	public void adjustTellerNumber() {
		// This is actually a control system. By adjusting
		// this numbers, you can reveal stability issue in
		// the control mechanism.
		// If line is too long, add another teller:
		// 如果当前客户数大于工作人员数两倍
		if (customers.size() / workingTellers.size() > 2) {
			// If tellers are on break or doing
			// another job, bring one back:
			// 如果有正在休息的员工, 叫最早休息的那位过来工作
			if (tellersDoingOtherThings.size() > 0) {
				// 因为是linkedList所以remove是删除并返回第一个Teller
				Teller teller = tellersDoingOtherThings.remove();
				// 设置成服务状态
				teller.serveCustomerLine();
				// 添加到服务队列中去
				workingTellers.offer(teller);
				return;
			}
			// Else create (hire) a new teller
			// 如果没有正在休息的员工的话, 新建一个, 并添加到服务队列当中去
			Teller teller = new Teller(customers);
			exec.execute(teller);
			workingTellers.add(teller);
			return;
		}
		// If line is short enough, remove a teller:
		// 如果有工作人员, 并且客户数目小于当前两倍的工作人员的话进行调整
		if (workingTellers.size() > 1
				&& (customers.size() / workingTellers.size() < 2)) {
			reassignOneTeller();
		}
		// If there is no line, we only need one teller:
		// 如果当前没有顾客的话, 留下一个Teller让其他的都去休息
		if (customers.size() == 0) {
			while (workingTellers.size() > 1) {
				reassignOneTeller();
			}
		}
	}

	/**
	 * 让一个工作人员去干点别的事情(休息)
	 */
	private void reassignOneTeller() {
		// 因为是priorityQueue所以会取出那位服务了最多的人去休息
		Teller teller = workingTellers.poll();
		// 让teller去休息
		teller.doSomethingElse();
		// 加到休息的人员队列中去
		tellersDoingOtherThings.add(teller);
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 休息一个周期
				TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
				// 进行调整
				adjustTellerNumber();
				// 输出当前的顾客和Teller的状态
				System.out.print(customers + " { ");
				for (Teller teller : workingTellers) {
					System.out.print(teller.shortString() + " ");
				}
				System.out.println("}");
			}
		} catch (InterruptedException e) {
			System.out.println(this + "interrupted");
		}
		System.out.println(this + "terminating");
	}

	@Override
	public String toString() {
		return "TellerManager ";
	}
}

public class BankTellerSimulation {
	static final int MAX_LINE_SIZE = 50; // 最大的等待数目
	static final int ADJUSTMENT_PERIOD = 1000; // 调整的周期ms

	public static void main(String[] args) throws NumberFormatException,
			InterruptedException, IOException {
		ExecutorService exec = Executors.newCachedThreadPool();
		// If line is too long, customers will leave
		CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
		// 一个线程负责产生客户数据
		exec.execute(new CustomerGenerator(customers));
		// Manager will add and remove tellers as necessary:
		// 一个线程负责管理调度
		exec.execute(new TellerManager(exec, customers, ADJUSTMENT_PERIOD));
		if (args.length > 0) { // optional arguments
			TimeUnit.SECONDS.sleep(new Integer(args[0]));
		} else {
			System.out.println("Press 'Enter' to quit");
			System.in.read();
		}
		exec.shutdownNow();
	}
}
