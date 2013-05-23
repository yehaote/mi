package mi.practice.java.base.initialization;

import static mi.practice.java.base.util.Print.*;

/**
 * 方法重载演示
 * 参数的顺序不同可能代表不同的方法
 */
public class OverloadingOrder {
	static void f(String s, int i){
		print("String: "+s+", int: "+i);
	}
	
	static void f(int i, String s){
		print("int: "+i+", String: "+s);
	}
	
	public static void main(String[] args){
		f("String first",11);
		f(99,"Int first");
	}
	
}
