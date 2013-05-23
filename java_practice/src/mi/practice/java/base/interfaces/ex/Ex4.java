package mi.practice.java.base.interfaces.ex;
abstract class Ex4Super{
	abstract void print();
}
class Ex4Sub extends Ex4Super{
	void print(){
		System.out.println("Ex4Sub.print()");
	}
}
public class Ex4 {
	static void test(Ex4Super sp){
//		((Ex4Sub)sp).print();
		sp.print();
	}
	public static void main(String[] args){
		Ex4Super sp = new Ex4Sub();
		test(sp);
	}
}
