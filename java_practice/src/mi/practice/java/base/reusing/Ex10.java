package mi.practice.java.base.reusing;
/**
 * 练习10
 */
class C1{
	C1(byte b){
		System.out.println("C1(byte b)");
	}
}

class C2{
	C2(short s){
		System.out.println("C2(short s)");
	}
}

class C3{
	C3(int i){
		System.out.println("C3(int i)");
	}
}

class Ex10Root{
	Ex10Root(long l){
		System.out.println("Ex10Root(long l)");
	}
	C1 c1 = new C1((byte) 1);
	C2 c2 = new C2((short) 2);
	C3 c3 = new C3(3);
}

class Ex10Stem extends Ex10Root{
	Ex10Stem(float f){
		super(7);
		System.out.println("Ex10Stem(float f)");
	}
	C1 c1 = new C1((byte) 4);
	C2 c2 = new C2((short) 5);
	C3 c3 = new C3(6);
}

public class Ex10 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		System.out.println("new Ex10Root");
		Ex10Root root = new Ex10Root(7);
		System.out.println("new Ex10Stem");
		Ex10Stem stem = new Ex10Stem(0.7f);
	}
}
