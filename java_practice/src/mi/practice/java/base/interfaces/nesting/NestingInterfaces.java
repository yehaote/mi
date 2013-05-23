package mi.practice.java.base.interfaces.nesting;
/**
 * 接口可以被内嵌在类或者其他接口当中
 * 嵌套的接口跟一般的接口使用起来没有什么不同, 除了private接口
 * 我们实现一个接口, 不用管这个接口内内嵌的其他接口
 * 私有接口只能在类中定义, 而且只能在类中使用,
 * 我们不能在除了类以后的其他地方使用这个类型
 * 注意接口D, 这个时候用使用接口D这个类型只能在相同的对象之间
 * 因为在这个范围之外其他的类都不认识这个类型
 */
@SuppressWarnings("unused")
class A{
	interface B{
		void f();
	}
	
	public class BImp implements B{
		public void f(){}
	}
	
	private class BImp2 implements B{
		public void f(){}
	}
	public interface C{
		void f();
	}
	class CImp implements C{
		public void f(){}
	}
	private class CImp2 implements C{
		public void f(){}
	}
	private interface D{
		void f();
	}
	private class DImp implements D{
		public void f(){}
	}
	public class DImp2 implements D{
		public void f(){}
	}
	public D getD(){
		return new DImp2();
	}
	private D dRef;
	public void recevieD(D d){
		dRef = d;
		dRef.f();
	}
}
interface E{
	interface G{
		void f();
	}
	public interface H{
		void f();
	}
	void g();
}
	
public class NestingInterfaces {
	public class BImp implements A.B{
		public void f(){}
	}
	class CImp implements A.C{
		public void f(){}
	}
	// 不能实现一个私有的接口
//	class DImp implements A.D{
//		public void f(){}
//	}
	class EImp implements E{
		public void g(){}
	}
	class EGImp implements E.G{
		public void f(){}
	}
	class EImp2 implements E{
		public void g(){}
		class EG implements E.G{
			public void f(){}
		}
	}
	public static void main(String[] args){
		A a = new A();
//		A.D ad =a.getD();//不能声明A.D类型,但是可以调用a.getD();
//		A.DImp2 di2 = a.getD(); //不能返回其他的, 除了A.D
//		a.getD().f(); //不能访问这个接口
		//只有另一个A的实现能拿a.getD()来使用
		A a2 = new A();
		a2.recevieD(a.getD());
	}
}
