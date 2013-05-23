package mi.practice.java.base.concurrency;

import static mi.practice.java.base.util.Print.*;

import java.util.concurrent.TimeUnit;
/**
 * Daemon线程跟随其他非线程的全部结束会自动结束, 
 * 甚至不会执行finally里的动作.
 */
class ADaemon implements Runnable{
	@Override
	public void run(){
		try{
			print("Starting ADaemon");
			TimeUnit.SECONDS.sleep(1);
		}catch(InterruptedException e){
			print("Exiting via InterruptedException");
		}finally{
			print("This should always run?");
		}
	}
}
public class DaemonsDontRunFinally {
	public static void main(String[] args){
		Thread t = new Thread(new ADaemon());
		t.setDaemon(true);
		t.start();
	}
}
