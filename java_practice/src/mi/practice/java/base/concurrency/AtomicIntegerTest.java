package mi.practice.java.base.concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * java提供了一些基本类型的atomic封装类,
 * AtomicInteger等等
 * 当性能需要优化的时候, 可以使用这些类来代替.
 * 具体的时候是调用Unsafe类的一些native的方法.
 */
public class AtomicIntegerTest implements Runnable{
	private AtomicInteger i = new AtomicInteger(0);
	public int getValue(){
		return i.get();
	}
	private void evenIncrement(){
		i.addAndGet(2);
	}
	@Override
	public void run() {
		while(true){
			evenIncrement();
		}
	}
	public static void main(String[] args){
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.err.print("Aborting");
				System.exit(0);
			}
		}, 5000);
		ExecutorService exec = Executors.newCachedThreadPool();
		AtomicIntegerTest ait = new AtomicIntegerTest();
		exec.execute(ait);
		while(true){
			int val=ait.getValue();
			if(val%2!=0){
				System.out.println(val);
				System.exit(0);
			}
		}
	}

}
