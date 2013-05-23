package mi.practice.java.base.concurrency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 * Car对象在这个例子中其实并不需要把操作都设置成synchronized,
 * 不过因为是多线程访问, 为了方便以后的更改设置成线程安全的比较好.
 * 除非在性能上因为这个碰到问题的时候再说.
 */
class Car {
	private final int id;
	private boolean engine = false, driveTrain = false, wheels = false;

	public Car(int idn) {
		id = idn;
	}

	// Empty cat object
	public Car() {
		id = -1;
	}

	public synchronized int getId() {
		return id;
	}

	public synchronized void addEngine() {
		engine = true;
	}

	public synchronized void addDriveTrain() {
		driveTrain = true;
	}

	public synchronized void addWheels() {
		wheels = true;
	}

	@Override
	public synchronized String toString() {
		return "Car " + id + " [" + " engine:" + engine + " drivenTrain: "
				+ driveTrain + " wheels: " + wheels + " ]";
	}
}

class CarQueue extends LinkedBlockingQueue<Car> {
	private static final long serialVersionUID = 6030607151276803496L;
}

class ChassisBuilder implements Runnable {
	private CarQueue carQueue;
	private int counter = 0;

	public ChassisBuilder(CarQueue carQueue) {
		this.carQueue = carQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(500);
				// Make chassis:
				Car car = new Car(counter++);
				System.out.println("ChassisBuilder created " + car);
				// Insert into queue
				carQueue.put(car);
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted: ChassisBuilder");
		}
		System.out.println("ChassisBuilder off");
	}
}

class Assembler implements Runnable {
	private CarQueue chassisQueue, finishingQueue;
	private Car car;
	// 同时需要做4步, 自己加上三个机器人
	private CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
	private RobotPool robotPool;

	public Assembler(CarQueue chassisQueue, CarQueue finishingQueue,
			RobotPool robotPool) {
		this.chassisQueue = chassisQueue;
		this.finishingQueue = finishingQueue;
		this.robotPool = robotPool;
	}

	public Car car() {
		return car;
	}

	public CyclicBarrier barrier() {
		return cyclicBarrier;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				// Block untils chassis is available:
				// 从已经装好地盘的队列中拿到车子
				car = chassisQueue.take();
				// Hire robots to perform work:
				// 雇佣机器人去执行工作
				robotPool.hire(EngineRobot.class, this);
				robotPool.hire(DriveTrainRobot.class, this);
				robotPool.hire(WheelRobot.class, this);
				// Until the robots finish
				// 等待知道机器人都完成工作
				cyclicBarrier.await();
				// Put cat into finishingQueue for further work
				// 把车子放到已经完成的队列中去
				finishingQueue.put(car);
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting Assembler via interrupt");
		} catch (BrokenBarrierException e) {
			// This one we want to know about
			throw new RuntimeException(e);
		}
		System.out.println("Assembler off");
	}
}

class Reporter implements Runnable {
	private CarQueue carQueue;

	public Reporter(CarQueue carQueue) {
		this.carQueue = carQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println(carQueue.take());
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting Reporter via interrupt");
		}
		System.out.println("Reporter off");
	}
}

abstract class Robot implements Runnable {
	private RobotPool robotPool;

	public Robot(RobotPool robotPool) {
		this.robotPool = robotPool;
	}

	protected Assembler assembler;

	public Robot assignAssembler(Assembler assembler) {
		this.assembler = assembler;
		return this;
	}

	private boolean engage = false; // 启动

	// 启动机器人
	public synchronized void engage() {
		engage = true;
		// 启动
		// 唤醒, 继续执行run()
		notifyAll();
	}

	// The part of run() that's different for each robot:
	// 每一个机器提供的服务都不一样
	abstract protected void performService();

	@Override
	public void run() {
		try {
			// 等待直到需要启动
			powerDown();
			while (!Thread.interrupted()) {
				performService();
				assembler.barrier().await(); // 等待一个装配工的机器人都完成工作x
				// We're done with that job...
				// 完成工作, 关闭
				powerDown();
			}
		} catch (InterruptedException e) {
			System.out.println("Exiting " + this + " via interrupt");
		} catch (BrokenBarrierException e) {
			// This one we want to know about
			throw new RuntimeException(e);
		}
		System.out.println(this + " off");
	}

	// 关闭机器人
	private synchronized void powerDown() throws InterruptedException {
		engage = false;
		// 跟装配工断开
		assembler = null;
		// Put ourselves back in the available pool
		// 放回机器人池中去
		robotPool.release(this);
		while (engage == false) {
			// 一直休息, 直到再被取出来
			wait();
		}
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}

class EngineRobot extends Robot {
	public EngineRobot(RobotPool robotPool) {
		super(robotPool);
	}

	@Override
	protected void performService() {
		System.out.println(this + " installing engine");
		// 因为assembler是protected所以子类可以访问
		assembler.car().addEngine();
	}
}

class DriveTrainRobot extends Robot {
	public DriveTrainRobot(RobotPool robotPool) {
		super(robotPool);
	}

	@Override
	protected void performService() {
		System.out.println(this + " installing DriveTrain");
		assembler.car().addDriveTrain();
	}
}

class WheelRobot extends Robot {
	public WheelRobot(RobotPool robotPool) {
		super(robotPool);
	}

	@Override
	protected void performService() {
		System.out.println(this + " installing wheels");
		assembler.car().addWheels();
	}
}

class RobotPool {
	// Quietly prevents identical entries:
	private Set<Robot> pool = new HashSet<Robot>();

	public synchronized void add(Robot robot) {
		pool.add(robot);
		// 因为hire方法会导致等待, 所以需要唤醒
		notifyAll();
	}

	public synchronized void hire(Class<? extends Robot> robotType,
			Assembler assembler) throws InterruptedException {
		for (Robot robot : pool) {
			if (robot.getClass().equals(robotType)) {
				pool.remove(robot);
				robot.assignAssembler(assembler);
				robot.engage();
				return;
			}
		}
		// 如果没有找到的话, 等待直到有新的robot增加进来的时候唤醒
		wait();
		hire(robotType, assembler);
	}

	/**
	 * 释放机器人, 添加回队列
	 */
	public synchronized void release(Robot robot) {
		add(robot);
	}
}

public class CarBuilder {
	public static void main(String[] args) throws InterruptedException {
		CarQueue chassisQueue = new CarQueue(), finishingQueue = new CarQueue();
		ExecutorService exec = Executors.newCachedThreadPool();
		RobotPool robotPool = new RobotPool();
		exec.execute(new EngineRobot(robotPool));
		exec.execute(new DriveTrainRobot(robotPool));
		exec.execute(new WheelRobot(robotPool));
		exec.execute(new Assembler(chassisQueue, finishingQueue, robotPool));
		exec.execute(new Reporter(finishingQueue));
		// Start everything running by producing chassis:
		exec.execute(new ChassisBuilder(chassisQueue));
		TimeUnit.SECONDS.sleep(7);
		exec.shutdownNow();
	}
}
