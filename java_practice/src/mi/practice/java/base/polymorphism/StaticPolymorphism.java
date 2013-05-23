package mi.practice.java.base.polymorphism;
/**
 * static方法跟类有关,跟对象无关
 * 因为声明的时候是用super所以会直接调用父类的方法
 * 最后调用static的时候使用类名去进行调用而不是对象名
 * 继承类的构造函数里必须调用父类的构造函数, 
 * 如果没有的话, 编译器会默认添加默认构造函数
 * 如果没有默认构造函数, 编译就会出错
 * 调用构造函数如果类没有加载, 加载类
 * 1.加载父类
 * 2.初始化静态成员
 * 构造函数的调用次序
 * 1.调用父类构造函数
 * 2.初始化成员
 * 3.执行自己的构造函数体
 */
class StaticSuper{
	public static String staticGet(){
		return "Base staticGet()";
	}
	public String dynamicGet(){
		return "Base dynamicGet()";
	}
}

class StaticSub extends StaticSuper{
	public static String staticGet(){
		return "Derived staticGet()";
	}
	public String dynamicGet(){
		return "Derived dynamicGet()";
	}
}

public class StaticPolymorphism {
	@SuppressWarnings("static-access")
	public static void main(String[] args){
		StaticSuper sup = new StaticSub();
		System.out.println(sup.staticGet());
		System.out.println(sup.dynamicGet());
	}
}
