package mi.practice.java.base.generics;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import mi.practice.java.base.util.TwoTuple;

import static mi.practice.java.base.util.Tuple.*;
/**
 * 可以通过动态代理的模式来实现Mixin,
 * 这样实现的不足:
 * 1. 是个动态类型, 不是静态类型
 * 2. 跟C++的mixin相比还是不那么好, 因为必须强转成那个类型才能进行调用
 * @author waf
 */
class MixinProxy implements InvocationHandler{
	Map<String, Object> delegatesByMethod;
	private MixinProxy(TwoTuple<Object, Class<?>>... pairs){
		delegatesByMethod = new HashMap<String, Object>();
		for(TwoTuple<Object, Class<?>> pair: pairs){
			for(Method method : pair.second.getMethods()){
				String methodName= method.getName();
				if(!delegatesByMethod.containsKey(methodName)){
					delegatesByMethod.put(methodName, pair.first);
				}
			}
		}
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String methodName=method.getName();
		Object delegate=delegatesByMethod.get(methodName);
		return method.invoke(delegate, args);
	}
	
	//调用这个方法进行实例化
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object newInstance(TwoTuple... pairs){
		Class[] interfaces = new Class[pairs.length];
		for(int i=0;i<pairs.length;i++){
			interfaces[i]=(Class) pairs[i].second;
		}
		ClassLoader cl = pairs[0].getClass().getClassLoader();
		return Proxy.newProxyInstance(cl, interfaces, new MixinProxy(pairs));
	}
}
public class DynamicProxyMixin {
	public static void main(String[] args){
		Object mixin = MixinProxy.newInstance(tuple(new BasicImp(), Basic.class), 
				tuple(new TimeStampImpl(), TimeStamp.class),
				tuple(new SerialNumberedImpl(), SerialNumbered.class));
		Basic b =(Basic)mixin;
		TimeStamp t = (TimeStamp)mixin;
		SerialNumbered s = (SerialNumbered)mixin;
		b.set("hello");
		System.out.println(b.get());
		System.out.println(t.getStamp());
		System.out.println(s.getSerialNumber());
	}
}
