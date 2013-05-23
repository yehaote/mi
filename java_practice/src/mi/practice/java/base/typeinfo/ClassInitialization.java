package mi.practice.java.base.typeinfo;

import java.util.Random;
/**
 * 如果是编译期常量的话, 被访问的时候将不会导致静态初始化,
 * 比如Initable.staticFinal
 * 直接显示调用class不会导致静态初始化,  而使用Class.forName()会导致静态初始化
 * 访问static非final的域需要连接(分配空间)和静态初始化
 * @author waf
 *
 */
class Initable{
	static final int staticFinal =47;
	static final int staticFinal2= ClassInitialization.random.nextInt();
	static{
		System.out.println("Initializing Initable");
	}
}

class Initable2{
	static int staticNonFinal =147;
	static{
		System.out.println("Initializing Initable2");
	}
}

class Initable3{
	static int staticNonFinal = 74;
	static{
		System.out.println("Initializing Initable3");
	}
}

@SuppressWarnings("rawtypes")
public class ClassInitialization {
	public static Random random = new Random(47);
	@SuppressWarnings("unused")
	public static void main(String[] args)throws Exception{
		Class initable=Initable.class;
		System.out.println("After creating Initable ref");
		//Does not trigger initialization
		System.out.println(Initable.staticFinal);
		//Does trigger initialization
		System.out.println(Initable.staticFinal2);
		//Does trigger initialization
		System.out.println(Initable2.staticNonFinal);
		Class initable3=Class.forName("mi.practice.java.base.typeinfo.Initable3");
		System.out.println("After creating Initable3 ref");
		System.out.println(Initable3.staticNonFinal);
	}
}