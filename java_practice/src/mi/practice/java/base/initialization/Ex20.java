package mi.practice.java.base.initialization;

class MainStringVarargs{
	public static void main(String... args){
		System.out.println("可变参数形式,main调用");
		for(String arg: args){
			System.out.println(arg);
		}
		System.out.println("调用完毕");
	}
}

public class Ex20 {
	
	public static void main(String... args){
		for(String arg: args){
			System.out.println(arg);
		}
		MainStringVarargs.main("a","b",new String("c"));
	}
}
