package mi.practice.java.base.concurrency;
/**
 * 这里没有Thread的引用不会被垃圾回收, 
 * 线程会进行自己注册, 直到线程执行完毕
 */
public class MoreBasicThreads {
	public static void main(String[] args){
		for(int i=0;i<5;i++){
			new Thread(new LiftOff()).start();
		}
		System.out.println("Waiting for LiftOff");
	}
}
