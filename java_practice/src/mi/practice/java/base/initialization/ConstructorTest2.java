package mi.practice.java.base.initialization;

class Tester2{
	String a;
	String b="hello";
	String c;
	
	Tester2() {
		c="bye";
	}
}
public class ConstructorTest2 {

	public static void main(String[] args) {
		Tester2 tester2 = new Tester2();
		System.out.println(tester2.a);
		System.out.println(tester2.b);
		System.out.println(tester2.c);
	}

}
