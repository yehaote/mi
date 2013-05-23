package mi.practice.java.base.access.ex;

import mi.practice.java.base.access.Ex5;
/**
 * 在继承实现类中, 能访问protected和public
 */
public class Ex5Main2 extends Ex5{
	
	public static void main(String[] args){
		Ex5Main2 ex = new Ex5Main2();
//		System.out.println(ex.a);
//		System.out.println(ex.b);
		System.out.println(ex.c);
		System.out.println(ex.d);
//		ex.privateSay();
//		ex.packageSay();
		ex.protectedSay();
		ex.publicSay();
	}
}
