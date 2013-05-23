package mi.practice.java.base.innerclasses.controller;
/**
 * 应用框架是一个或一群类, 专门为了特定的问题设计的
 * 
 * @author waf
 *
 */
public abstract class Event {
	private long eventTime;
	protected final long delayTime;
	public Event(long delayTime){
		this.delayTime=delayTime;
		start();
	}
	public void start(){//可以重新开始
		eventTime=System.nanoTime() +delayTime;
	}
	public boolean ready(){
		System.out.println("当前时间: "+System.nanoTime());
		System.out.println("预定时间: "+eventTime);
		return System.nanoTime() >= eventTime;
	}
	public abstract void action();
}
