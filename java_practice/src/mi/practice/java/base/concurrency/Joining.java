package mi.practice.java.base.concurrency;
import static mi.practice.java.base.util.Print.*;
/**
 * 在一个线程中调用第二个线程的join()方法,
 * 等待第二个线程执行完毕,再去执行当前线程
 * (当第二个线程的isAlive()返回false)
 * 使用interrupt()方法可以打断当前线程
 * 当Interrupt的异常被捕获以后,
 * 线程的interrupt状态为false.
 * 
 * join的时候, interrupt也算一种执行完毕.
 */
class Sleeper extends Thread{
	private int duration;
	public Sleeper(String name, int sleepTime){
		super(name);
		duration=sleepTime;
		start();
	}
	@Override
	public void run(){
		try{
			sleep(duration);
		}catch(InterruptedException e){
			print(getName()+" was interrupted. isInterrupted(): "+isInterrupted());
			return;
		}
		print(getName()+" has awakened");
	}
}

class Joiner extends Thread{
	private Sleeper sleeper;
	public Joiner(String name, Sleeper sleeper){
		super(name);
		this.sleeper=sleeper;
		start();
	}
	@Override
	public void run(){
		try{
			// 等待sleeper线程执行完毕
			sleeper.join();
		}catch(InterruptedException e){
			print("Interrupted");
		}
		print(getName()+" join completed");
	}
}

public class Joining {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Sleeper sleepy = new Sleeper("Sleepy", 1500),
				grumpy= new Sleeper("grumpy", 1500);
		Joiner dopey = new Joiner("Dopey", sleepy),
				doc = new Joiner("Doc",grumpy);
		grumpy.interrupt();
	}
}
