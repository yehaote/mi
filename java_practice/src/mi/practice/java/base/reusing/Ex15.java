package mi.practice.java.base.reusing;

import mi.practice.java.base.reusing.ex.Ex15From;
/**
 * 不能直接调用类的protected方法在继承类和包之外
 * 在继承类中可以调用父类的protected方法
 */
class Ex15_Demo extends Ex15From{
	public void print(){
		super.sayHello();
	}
}

public class Ex15 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Ex15From ex15From = new Ex15From();
//		ex15From.sayHello();
		Ex15_Demo demo = new Ex15_Demo();
		demo.print();
	}
}
