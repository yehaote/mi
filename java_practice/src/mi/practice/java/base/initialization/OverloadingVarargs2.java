package mi.practice.java.base.initialization;
/**
 * 注意跟OverloadingVarargs3的对比
 * 3的写法比较好
 * @author waf
 *
 */
public class OverloadingVarargs2 {
	static void f(float i, Character... args){
		System.out.println("first");
	}
	
	static void f(Character... args){
		System.out.println("second");
	}
	
	public static void main(String[] args){
		f(1,'a');
//		f('a','b');//歧义, 不能这么调用
		f();
	}
}
