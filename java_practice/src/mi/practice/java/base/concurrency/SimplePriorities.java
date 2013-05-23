package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 可以使用setPriority()设置优先级, 
 * 使用getPriority()获得优先级
 * 一般用Thread.MAX_PRIORITY, NORM_PRIORITY, MIN_PRIORITY来设置优先级
 * 使用Thread.currentThread()获取当前线程对象
 */
public class SimplePriorities implements Runnable{
	private int countDown=5;
	@SuppressWarnings("unused")
	private volatile double d;//No optimization
	private int priority;
	public SimplePriorities(int priority){
		this.priority=priority;
	}
	@Override
	public String toString(){
		return Thread.currentThread()+" : "+countDown;
	}
	@Override
	public void run(){
		Thread.currentThread().setPriority(priority);
		while(true){
			for(int i=0;i<100000;i++){
				d+=(Math.PI+Math.E)/(double)i;
				if(i%1000 ==0){
					// 暂停线程, 让出执行权
					Thread.yield();
				}
			}
			System.out.println(this);
			if(countDown-- == 0){
				return;
			}
		}
	}
	public static void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0;i<5;i++){
			exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
		}
		exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
		exec.shutdown();
	}
}
