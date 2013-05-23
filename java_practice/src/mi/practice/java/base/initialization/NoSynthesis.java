package mi.practice.java.base.initialization;
/**
 * 当给类指定了构造方法以后, 系统将不再给类生成默认的构造函数
 */
class Bird2{
	Bird2(int i){}
	Bird2(double d){}
}

public class NoSynthesis {

	public static void main(String[] args) {
//		Bird2 b= new Bird2(); //没有默认构造函数
		Bird2 b2 = new Bird2(1);
		Bird2 b3 = new Bird2(0.0);
		b2.toString();
		b3.toString();
	}

}
