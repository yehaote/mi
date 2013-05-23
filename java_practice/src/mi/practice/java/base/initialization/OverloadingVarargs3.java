package mi.practice.java.base.initialization;

public class OverloadingVarargs3 {
	
	static void f(float i, Character... args){
		System.out.println("first");
	}
	
	static void f(char c, Character... args){
		System.out.println("second");
	}
	
	public static void main(String[] args){
		f(1,'a');
		f('a','b');
//		f();//跟OverloadingVarargs2的区别就是, 不能再进行无参调用
	}
}
