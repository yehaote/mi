package mi.practice.java.base.typeinfo;

import java.lang.reflect.Method;

import mi.practice.java.base.typeinfo.interfacea.A;
import mi.practice.java.base.typeinfo.packageaccess.HiddenC;
/**
 * 包访问来限制客户端程序人员在使用反射的时候,
 * 还是没有束缚力, 只要知道方法名跟参数就可以直接调用
 * 而且可以通过javap -private 来反编译得到类的信息
 * @author waf
 */
public class HiddenImplementation {
	public static void main(String[] args) throws Exception{
		A a=HiddenC.makeA();
		a.f();
		System.out.println(a.getClass().getName());
		//
//		if(a instanceof C){
//			C c= (C)a;
//			c.g();
//		}
		//可是使用反射依然可以调用这些方法s
		callHiddenMethod(a, "g");
		//甚至包括这些没有访问权限的方法
		callHiddenMethod(a, "u");
		callHiddenMethod(a, "v");
		callHiddenMethod(a, "w");
	}
	
	static void callHiddenMethod(Object a, String methodName)
			throws Exception{
		Method g =a.getClass().getDeclaredMethod(methodName);
		g.setAccessible(true);
		g.invoke(a);
	}
}
