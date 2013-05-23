package mi.practice.java.base.reusing;
/**
 * 练习1
 * 演示懒加载, 就是执行前再进行判断并初始化
 */
class Ex1Demo{
	String content;
	
	Ex1Demo(){
		System.out.println("Ex1Demo initialization");
		content="content initialized";
	}
}

public class Ex1 {
	private Ex1Demo demo;
	
	public String toString(){
		if(demo == null){
			System.out.println("initialize demo");
			demo = new Ex1Demo();
		}
		return "Ex1's demo's content"+demo.content;
	}
	public static void main(String[] args){
		Ex1 ex = new Ex1();
		System.out.println("Initialized ex1");
		System.out.println(ex);
	}
}
