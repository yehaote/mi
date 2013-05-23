package mi.practice.java.base.access;
/**
 * 如果是非public类, 方法都不建议使用public修饰
 * Soup2实现单例模式
 */
class Soup1{
	private Soup1(){}
	//把构造函数设置成私有, 让别人不能通过构造函数直接初始化Soup类
	//提供一个静态方法来实例化类
	public static Soup1 makeSoup(){
		return new Soup1();
	}
}

class Soup2{
	private Soup2() {}
	private static Soup2 ps1 = new Soup2();
	public static Soup2 access(){
		return ps1;
	}
	public void f() {}
}

public class Lunch {
	void testPrivate(){
		//不能这样实例化, 私有构造函数
		//Soup1 soup = new Soup1();
	}
	
	@SuppressWarnings("unused")
	void testStatic(){
		Soup1 soup= Soup1.makeSoup();
	}
	
	void testSingleton(){
		Soup2.access().f();
	}
}
