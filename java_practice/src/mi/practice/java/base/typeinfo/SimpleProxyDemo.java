package mi.practice.java.base.typeinfo;

import static mi.practice.java.base.util.Print.*;
/**
 * 代理模式
 * 在执行跟实际调用之中加一层, 
 * 用于做一些额外的事情
 * 相当与一个go-between
 * @author waf
 */
interface Interface{
	void doSomething();
	void somethingElse(String arg);
}

class RealObject implements Interface{
	@Override
	public void doSomething() {
		print("doSomething");
	}
	@Override
	public void somethingElse(String arg) {
		print("somethingElse "+arg);
	}
}

class SimpleProxy implements Interface{
	private Interface proxied;
	public SimpleProxy(Interface proxied){
		this.proxied=proxied;
	}
	@Override
	public void doSomething() {
		print("SimpleProxy doSomething");
		proxied.doSomething();
	}

	@Override
	public void somethingElse(String arg) {
		print("SimpleProxy somethingElse "+arg);
		proxied.somethingElse(arg);
	}
}
public class SimpleProxyDemo {
	public static void consumer(Interface iface){
		iface.doSomething();
		iface.somethingElse("bonobo");
	}
	
	public static void main(String[] args){
		consumer(new RealObject());
		consumer(new SimpleProxy(new RealObject()));
	}
}