package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Executors采用静态方法创建ExecutorService
 * ExecutorService.shutdown()是防止新的任务被提交上来
 */
public class CachedThreadPool {
	public static void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0;i<5;i++){
			exec.execute(new LiftOff());
		}
		exec.shutdown(); //也是马上执行的, 不会等LiftOff执行结束
	}
}
