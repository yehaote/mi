package mi.practice.java.base.typeinfo;

import mi.practice.java.base.typeinfo.interfacea.A;
/**
 * 有的时候使用接口是为了让代码解耦,
 * 而当使用强转的时候耦合又回来了
 * @author waf
 */
class B implements A{
	@Override
	public void f() {}
	public void g() {}
	
}
public class InterfaceViolation {
	public static void main(String[] args){
		A a = new B();
		a.f();
//		a.g(); 编译异常
		System.out.println(a.getClass().getName());
		if(a instanceof B){
			B b = (B) a;
			b.g();
		}
	}
}
