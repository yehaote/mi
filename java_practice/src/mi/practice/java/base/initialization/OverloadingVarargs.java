package mi.practice.java.base.initialization;
/**
 * 可变参数重载
 * 当有重载的时候注意, 无参调用的时候是否存在歧义
 */
public class OverloadingVarargs {
	static void f(Character... args){
		System.out.print("first");
		for(Character c: args){
			System.out.print(" "+c);
		}
		System.out.println();
	}
	
	static void f(Integer... args){
		System.out.print("second");
		for(Integer i: args){
			System.out.print(" "+i);
		}
		System.out.println();
	}
	
	static void f(Long... args){
		System.out.println("third");
	}
	
	public static void main(String[] args){
		f('a','b','c');
		f(1);
		f(2,1);
		f(0);
		f(0L);
//		f();//歧义
	}
}
