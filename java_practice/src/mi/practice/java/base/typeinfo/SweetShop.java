package mi.practice.java.base.typeinfo;
import static mi.practice.java.base.util.Print.*;
/**
 * 1. 类在static被访问的时候会进行加载,
 *    类的构造函数也是static方法, JVM是懒加载的只有当被需要的时候才会去加载
 * 2. Candy, Gum, Cookie在被加载的时候会执行static初始化,
 *    各自打印自己的信息
 * 3. Class.Forname()只支持当前已经加载的类
 * 
 * 每一个Object都有一个自己对应的Class类,
 * 而这些Class类就像所有的类都继承Object一样,
 * 它们都继承自Class
 * 
 * 我们可以通过Class.forName()的静态方法类获取Class,
 * 当找不到的时候会返回ClassNotFoundException
 * @author waf
 */
class Candy{
	static {
		print("Loading Candy");
	}
}
class Gum{
	static{
		print("Loading Gum");
	}
}
class Cookie{
	static{
		print("Loading Cookie");
	}
}

public class SweetShop {
	public static void main(String[] args){
		print("inside main");
		new Candy();
		print("After creating Candy");
		try{
			Class.forName("Gum");
		}catch(ClassNotFoundException e){
			print("Couldn't find Gum");
		}
		print("After Class.forName(\"Gum\")");
		new Cookie();
		print("After creating Cookie");
	}
}
