package mi.practice.java.base.innerclasses.ex;

class Ex5Outer {
	class Ex5Inner {
		class Ex5InnerInner{}
	}
}

public class Ex5 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Ex5Outer outer = new Ex5Outer();
		Ex5Outer.Ex5Inner inner = outer.new Ex5Inner();
		Ex5Outer.Ex5Inner.Ex5InnerInner innerinner = inner.new Ex5InnerInner();
	}
}
