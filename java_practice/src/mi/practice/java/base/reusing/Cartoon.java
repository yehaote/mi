package mi.practice.java.base.reusing;
/**
 * 演示子类构造函数会执行父类构造函数
 * 子类在扩展父类的时候, 其实相当于在子类掩藏了一个父类的引用
 * 即使不为Cartoon创作构造器, 系统会默认给它分配一个构造器,
 * 并调用父类构造器的方法
 */
class Art{
	Art(){
		System.out.println("Art constructor");
	}
}

class Drawing extends Art{
	Drawing(){
		System.out.println("Drawing constructor");
	}
}

public class Cartoon extends Drawing{
//	public Cartoon(){
//		System.out.println("Cartoon constructor");
//	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Cartoon x= new Cartoon();
	}
}
