package mi.practice.java.base.concurrency;
/**
 * Thread类的基本用法
 * Thread.start()初始化线程并执行Runable的run()方法
 * Thread.start()相当与开始了另外一个线程去执行动作, 
 * 这个方法马上返回结果
 * 在一个线程里可以开始创建其他的线程
 */
public class BasicThreads {
	public static void main(String[] arg){
		Thread t = new Thread(new LiftOff());
		t.start();
		System.out.println("Waiting for LiftOff");
	}
}
