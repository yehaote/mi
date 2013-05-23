package mi.practice.java.base.interfaces.ex;

interface I1 {
	void test1();
}

interface I2 extends I1 {
	void test2();
}

interface I3 extends I1 {
	void test3();
}

interface I4 extends I2, I3 {
	void test4();
}

class Ex13_Demo implements I4 {

	@Override
	public void test2() {
	}

	@Override
	public void test1() {
	}

	@Override
	public void test3() {
	}

	@Override
	public void test4() {
	}

}

public class Ex13 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Ex13_Demo demo = new Ex13_Demo();
		I1 i1 = demo;
		I2 i2 = demo;
		I3 i3 = demo;
		I4 i4 = demo;
	}
}
