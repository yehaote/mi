package mi.practice.java.base.innerclasses;
import static mi.practice.java.base.util.Print.*;
/**
 * local inner class(局限类)
 * 不是外部类的一部分, 它嵌入在方法或者语句当中, 
 * 可以调用final的方法参数和外部类的所有成员
 * 局限类跟匿名类的区别:
 * 1.局限类可以有构造器, 或者重载构造器
 * 2.匿名类只能实例初始化
 * 3.局限类可以生成多个对象(因为匿名类没有名字)
 * 
 * 内部类的识别符是:
 * 外部类$内部类.class
 * 如果是匿名类则编译器自动分配一个数值作为它的标示符
 * 内部类$1.class
 */
interface Counter{
	int next();
}

public class LocalInnerClass {
	private int count=0;
	Counter getCounter(final String name){
		class LocalCounter implements Counter{
			public LocalCounter(){
				print("LocalCounter()");
			}
			public int next(){
				printnb(name);
				return count++;
			}
		}
		return new LocalCounter();
	}
	Counter getCounter2(final String name){
		return new Counter(){
			{
				print("Anonymous Counter");
			}
			public int next(){
				printnb(name);
				return count++;
			}
		};
	}
	public static void main(String[] args){
		LocalInnerClass lic = new LocalInnerClass();
		Counter c1 = lic.getCounter("LocalInner"),
				c2 = lic.getCounter2("Anonymous inner");
		for(int i=0;i<5;i++){
			print(c1.next());
		}
		for(int i=0;i<5;i++){
			print(c2.next());
		}
	}
}
