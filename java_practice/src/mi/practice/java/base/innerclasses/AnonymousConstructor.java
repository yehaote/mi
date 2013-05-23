package mi.practice.java.base.innerclasses;
import static mi.practice.java.base.util.Print.*;
/**
 * 参数如果要在匿名类中使用必须定义为final,
 * 因为匿名类没有名字, 所以不能使用构造函数进行初始化, 
 * 不过可以使用下面的方法(在实例初始化里定义)来完成构造函数差不多的功能
 */
abstract class Base{
	public Base(int i){
		print("Base constructor, i = "+i);
	}
	public abstract void f();
}
public class AnonymousConstructor {
	public static Base getBase(int i){
		return new Base(i){
			{
				print("Inside instance initializer");
			}
			public void f(){
				print("In anonymous f()");
			}
		};
	}
	public static void main(String[] args){
		Base base = getBase(47);
		base.f();
	}
}
