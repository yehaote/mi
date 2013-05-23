package mi.practice.java.base.initialization;
/**
 * 演示类里的成员String, 会被初始化成null
 */
class Tester{
	String s;
}
public class ConstructorTest {
	public static void main(String[] args){
		Tester tester = new Tester();
		System.out.println(tester.s);
	}

}