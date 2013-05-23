package mi.practice.java.base.generics;

import java.util.Date;
/**
 * 在Java中没有办法像c++一样实现mixin, 
 * 因为java中的泛型是通过擦写实现的,
 * 也不像C++一样支持多重集成.
 * mixin看起来有一点AOP的味道, 
 * 面很多时候也是用来处理mixin的问题.
 * 在java可以通过接口来实现类似的效果.
 * 这里的Mixin使用了delegation
 * @author waf
 */
interface TimeStamp{
	long getStamp();
}

class TimeStampImpl implements TimeStamp{
	private final long timeStamp;
	public TimeStampImpl() {
		this.timeStamp=new Date().getTime();
	}
	@Override
	public long getStamp() {
		return timeStamp;
	}
}

interface SerialNumbered{
	long getSerialNumber();
}

class SerialNumberedImpl implements SerialNumbered{
	private static long counter=1;
	private final long serialNumber = counter++;
	@Override
	public long getSerialNumber() {
		return serialNumber;
	}
}

interface Basic{
	void set(String val);
	String get();
}

class BasicImp implements Basic{
	private String value;
	@Override
	public void set(String val) {
		value=val;
	}
	@Override
	public String get() {
		return value;
	}
}

class Mixin extends BasicImp implements TimeStamp, SerialNumbered{
	private TimeStamp timeStamp=new TimeStampImpl();
	private SerialNumbered serialNumber = new SerialNumberedImpl();
	@Override
	public long getSerialNumber() {
		return serialNumber.getSerialNumber();
	}
	@Override
	public long getStamp() {
		return timeStamp.getStamp();
	}
	
}

public class Mixins {
	public static void main(String[] args){
		Mixin mixin1 = new Mixin(),
				mixin2 = new Mixin();
		mixin1.set("test string 1");
		mixin2.set("test string 2");
		System.out.println(mixin1.get()+" "+mixin1.getSerialNumber()+" "+mixin1.getStamp());
		System.out.println(mixin2.get()+" "+mixin2.getSerialNumber()+" "+mixin2.getStamp());
	}
}
