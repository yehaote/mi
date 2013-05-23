package mi.practice.java.base.typeinfo.packageaccess;

import mi.practice.java.base.typeinfo.interfacea.A;
/**
 * 可以使用包访问限制类来限制客户端程序员直接对类的使用,
 * 不能在包之外使用C
 * @author waf
 */
class C implements A{
	@Override
	public void f() {
		System.out.println("public C.f()");
	}
	public void g() {
		System.out.println("public C.g()");
	}
	void u(){
		System.out.println("package C.u()");
	}
	protected void v(){
		System.out.println("protected C.v()");
	}
	@SuppressWarnings("unused")
	private void w(){
		System.out.println("private C.w()");
	}
}

public class HiddenC{
	public static A makeA(){
		return new C();
	}
}