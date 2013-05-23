package mi.practice.java.base.innerclasses;
/**
 * 无论嵌入的层次有多深,
 * 都可以调用
 */
class MNA{
	private void f(){}
	class A{
		private void g(){}
		public class B{
			void h(){
				f();
				g();
			}
		}
	}
}

public class MultiNestingAccess {
	public static void main(String[] args){
		MNA mna = new MNA();
		MNA.A mnaa = mna.new A();
		MNA.A.B mnaab = mnaa.new B();
		mnaab.h();
	}
}
