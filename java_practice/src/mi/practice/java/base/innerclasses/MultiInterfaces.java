package mi.practice.java.base.innerclasses;
/**
 * 一种方式看待内部类,
 * 可以把内部类看作多重继承的实现方式
 */
interface A{}
interface B{}
class X implements A,B{}
class Y implements A{
	B makeB(){
		//返回匿名类
		return new B() {
		};
	}
}
public class MultiInterfaces {
	static void takesA(A a){}
	static void takesB(B b){}
	public static void main(String[] args){
		X x = new X();
		Y y = new Y();
		takesA(x);
		takesA(y);
		takesB(x);
		takesB(y.makeB());
	}
}
