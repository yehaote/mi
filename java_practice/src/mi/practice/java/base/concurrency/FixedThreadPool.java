package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * FixedThreadPool采用限制的线程数去执行被提交上来的任务
 * 线程的创建统一在ExecutorService初始化的时候, 
 * 优先考虑CachedThreadPool, 只有当它会出现问题的时候再去转换成FixedThreadPool
 */
public class FixedThreadPool {
	public static void main(String[] args){
		ExecutorService exec = Executors.newFixedThreadPool(5);
		for(int i=0;i<5;i++){
			exec.execute(new LiftOff());
		}
		exec.shutdown();
	}
}
