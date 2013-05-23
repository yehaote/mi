package mi.practice.java.base.reusing;
/**
 * 使用final定义的方法是不能被覆盖的
 */
class Ex21Demo{
	
	public final void sayHello(){
		System.out.println("EX21Demo.sayHello()");
	}
}


public class Ex21 extends Ex21Demo{
//	public void sayHello(){}
}
