package mi.practice.java.base.interfaces.ex;

import mi.practice.java.base.interfaces.ex.ex17.Ex17Interface;

class Ex17Demo implements Ex17Interface{
	private static long count=0;
	private final long ID=count++;
	public Ex17Demo(){
		System.out.println("Ex17Demo() ID = "+ID);
	}
}
public class Ex17{
	@SuppressWarnings({ "static-access", "unused" })
	public static void main(String[] args){
		Ex17Demo demo = new Ex17Demo();
//		demo.A=2;
//		demo.B=2;	//不能更改, 默认是final
		System.out.println(demo.A);
		Ex17Demo demo2 = new Ex17Demo();
		//只初始化一次, static
	}
}
