package mi.practice.java.base.typeinfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * Java的动态代理, 
 * 负责动态创建对象和动态调用方法
 * Proxy.newProxyInstance()需要一个ClassLoader,
 * 一个interface的List, 和一个InvocationHandler的实现
 * @author waf
 */
class DynamicProxyHandler implements InvocationHandler{
	private Object proxied;
	public DynamicProxyHandler(Object proxied){
		this.proxied=proxied;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("*** proxy: "+proxy.getClass()+", method: "+method+", args: "+args);
		if(args!=null){
			for(Object arg:args){
				System.out.println(" "+arg);
			}
		}
		return method.invoke(proxied,args);//动态调用
	}
}

public class SimpleDynamicProxy {
	public static void consumer(Interface iface){
		iface.doSomething();
		iface.somethingElse("bonobo");
	}
	
	public static void main(String[] args){
		RealObject real = new RealObject();
		consumer(real);
		Interface proxy = (Interface) Proxy.newProxyInstance(
				Interface.class.getClassLoader(),
				new Class[] { Interface.class }, new DynamicProxyHandler(real));
		consumer(proxy);
	}
}
