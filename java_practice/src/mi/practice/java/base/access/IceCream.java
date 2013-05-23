package mi.practice.java.base.access;
/**
 * 演示private的使用
 * 可以指定函数去实例化对象
 * 把默认唯一的构造函数设置成private, 
 * 可以使别人无法通过构造方法实例化对象
 */

class Sundae{
	private Sundae(){
	}
	
	static Sundae makeASundae(){
		return new Sundae();
	}
}

public class IceCream {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		//! Sundae x = new Sundae(); //不能编译
		Sundae x = Sundae.makeASundae();
	}
}
