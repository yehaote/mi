package mi.practice.java.base.concurrency;

import java.util.concurrent.TimeUnit;
import static mi.practice.java.base.util.Print.*;
/**
 * Daemon进程不是程序最本质的组成部分, 
 * 所以当所有非Daemon进程执行结束以后,
 * 程序会自动关闭所有的Daemon进程并关闭程序
 * 如果想运行Daemon线程, 在Thread开始之前必须设置为Daemon
 */
public class SimpleDaemons implements Runnable{
	@Override
	public void run(){
		try{
			while(true){
				TimeUnit.MILLISECONDS.sleep(100);
				print(Thread.currentThread()+" "+this);
			}
		}catch(InterruptedException e){
			print("sleep() interrupted");
		}
	}
	public static void main(String[] args)throws InterruptedException{
		for(int i=0;i<10;i++){
			Thread daemon = new Thread(new SimpleDaemons());
			daemon.setDaemon(true);// Must call before start()
			daemon.start();
		}
		print("All daemons started");
		TimeUnit.MILLISECONDS.sleep(175);
	}
}
