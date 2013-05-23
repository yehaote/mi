package mi.practice.java.base.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * SingleThreadExecutor像FixedThreadPool大小为1
 * 它可以被用来监听Socket请求, SocketServer
 * 如果有很多任务被提交到SingleThreadExecutor的话, 
 * 这些任务会被存在一个队列当中, 执行一个完毕, 再去执行下一个
 */
public class SingleThreadExecutor {
	public static void main(String[] args){
		ExecutorService exec = Executors.newSingleThreadExecutor();
		for(int i=0;i<5;i++){
			exec.execute(new LiftOff());
		}
		exec.shutdown();
		//shutdown以后就不能再执行其他的任务了
	}
}
