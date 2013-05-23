package mi.practice.java.base.typeinfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static mi.practice.java.base.util.Print.*;
/**
 * 动态代理的时候还可以对method进行判断
 * @author waf
 */
class MethodSelector implements InvocationHandler {
	private Object proxied;

	public MethodSelector(Object proxied) {
		this.proxied = proxied;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (method.getName().equals("interesting")) {
			print("Proxy detected the interesting method");
		}
		return method.invoke(proxied, args);
	}
}

interface SomeMethods {
	void boring1();

	void boring2();

	void boring3();

	void interesting(String arg);
}

class Implemention implements SomeMethods {
	@Override
	public void boring1() {
		print("boring1");
	}

	@Override
	public void boring2() {
		print("boring2");
	}

	@Override
	public void boring3() {
		print("boring3");
	}

	@Override
	public void interesting(String arg) {
		print("interesting " + arg);
	}
}

public class SelectingMethods {
	public static void main(String[] args) {
		SomeMethods proxy = (SomeMethods) Proxy.newProxyInstance(
				SomeMethods.class.getClassLoader(),
				new Class[] { SomeMethods.class }, new MethodSelector(
						new Implemention()));
		proxy.boring1();
		proxy.boring2();
		proxy.boring3();
		proxy.interesting("bonobo");
	}
}
