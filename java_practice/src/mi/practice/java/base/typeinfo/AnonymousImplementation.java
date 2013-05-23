package mi.practice.java.base.typeinfo;

import mi.practice.java.base.typeinfo.interfacea.A;
/**
 * 使用匿名类也一样阻挡不了反射
 * @author waf
 */
class AnonymousA{
	@SuppressWarnings("unused")
	public static A makeA(){
		return new A(){
			@Override
			public void f() {
				System.out.println("public Anonymous.f()");
			}
			public void g() {
				System.out.println("public Anonymous.g()");
			}
			void u(){
				System.out.println("package Anonymous.u()");
			}
			protected void v(){
				System.out.println("protected Anonymous.v()");
			}
			private void w(){
				System.out.println("private Anonymous.w()");
			}
		};
	}
}
public class AnonymousImplementation {
	public static void main(String[] args) throws Exception{
		A a = AnonymousA.makeA();
		a.f();
		System.out.println(a.getClass().getName());
		HiddenImplementation.callHiddenMethod(a, "g");
		HiddenImplementation.callHiddenMethod(a, "u");
		HiddenImplementation.callHiddenMethod(a, "v");
		HiddenImplementation.callHiddenMethod(a, "w");
	}
}
