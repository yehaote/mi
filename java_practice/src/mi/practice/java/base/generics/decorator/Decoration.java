package mi.practice.java.base.generics.decorator;

import java.util.Date;
/**
 * Decorator
 * 装饰者模式
 * 装饰者模式到最后暴露出来的那一层是最后包装的那一层, 
 * 也只有那一层的方法有效.
 * 而Mixin混合在一起以后, 所有的方法都是有效的,
 * 所以相对Mixin来说, Decorator只能解决部分Mixin的问题
 * @author waf
 */
class Basic{
	private String value;
	public void set(String val){
		value=val;
	}
	public String get(){
		return value;
	}
}

class Decorator extends Basic{
	protected Basic basic;
	public Decorator(Basic basic){
		this.basic=basic;
	}
	public void set(String val){
		basic.set(val);
	}
	public String get(){
		return basic.get();
	}
}

class TimeStamped extends Decorator{
	private final long timeStamp;
	public TimeStamped(Basic basic){
		super(basic);
		timeStamp=new Date().getTime();
	}
	public long getStamp(){
		return timeStamp;
	}
}

class SerialNumbered extends Decorator{
	private static long counter=1;
	private final long serialNumber=counter++;
	public SerialNumbered(Basic basic){
		super(basic);
	}
	public long getSerialNumber(){
		return serialNumber;
	}
}

public class Decoration {
	public static void main(String[] args){
		TimeStamped t = new TimeStamped(new Basic());
		TimeStamped t2 = new TimeStamped(new SerialNumbered(new Basic()));
		t.getStamp();
		t2.getStamp();
//		t2.getSerialNumber(); // Not available
		SerialNumbered s1 = new SerialNumbered(new Basic());
		SerialNumbered s2 = new SerialNumbered(new TimeStamped(new Basic()));
		s1.getSerialNumber();
		s2.getSerialNumber();
//		s2.getStamp(); // Not available
	}
}
