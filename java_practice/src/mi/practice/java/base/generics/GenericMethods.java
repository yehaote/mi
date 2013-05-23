package mi.practice.java.base.generics;
/**
 * 泛型也可以用在方法上,
 * 在定义的时候放在前面就好了
 * 如果一个方法是static的, 它是权限访问类定义的泛型类型的,
 * 因为所有的类共用static方法
 * 在使用泛型类的时候需要指定类型,
 * 但是使用泛型方法的时候并不需要, 
 * 编译器会为你生成类型
 * 这种方法叫类型引用
 * @author waf
 */
public class GenericMethods {
	public <T> void f(T x){
		System.out.println(x.getClass().getName());
	}
	
	@SuppressWarnings("hiding")
	public <A, B, C> void h(A a, B b, C c){
		System.out.println(a.getClass().getName());
		System.out.println(b.getClass().getName());
		System.out.println(c.getClass().getName());
	}
	
	public <T> void g(){
	}
	
	public static void main(String[] args){
		GenericMethods gm= new GenericMethods();
		gm.f("");
		gm.f(1);
		gm.f(1.0);
		gm.f(1.0F);
		gm.f('c');
		gm.f(gm);
	}
}
