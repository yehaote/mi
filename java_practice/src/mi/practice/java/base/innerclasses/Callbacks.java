package mi.practice.java.base.innerclasses;
import static mi.practice.java.base.util.Print.*;
/**
 * 闭包是一个可以调用的对象, 它包含了创建它地方的信息
 */
interface Incrementable{
	void increment();
}
class Callee1 implements Incrementable{
	private int i=0;
	public void increment(){
		i++;
		print(i);
	}
}

class MyIncrement{
	public void increment(){
		print("Other operation");
	}
	static void f(MyIncrement mi){
		mi.increment();
	}
}

class Callee2 extends MyIncrement{
	private int i=0;
	public void increment(){
		super.increment();
		i++;
		print(i);
	}
	private class Closure implements Incrementable{
		public void increment(){
			Callee2.this.increment();
		}
	}
	Incrementable getCallbackReference(){
		return new Closure();
	}
	
}

class Caller{
	private Incrementable callbackReference;
	Caller(Incrementable cbh){
		callbackReference=cbh;
	}
	void go(){
		callbackReference.increment();
	}
}
public class Callbacks {
	public static void main(String[] args){
		Callee1 c1 = new Callee1();
		Callee2 c2 = new Callee2();
		MyIncrement.f(c2);
		Caller caller1 = new Caller(c1);
		Caller caller2 = new Caller(c2.getCallbackReference());
		caller1.go();
		caller1.go();
		caller2.go();
		caller2.go();
	}
}
