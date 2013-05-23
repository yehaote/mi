package mi.practice.java.base.initialization;

class StringInner{
	String str;
	{
		str= new String("Hello world");
		System.out.println("str is be initialized");
	}
}

public class Ex15 {
	public static void main(String[] args){
		System.out.println("Inside main");
		StringInner inner= new StringInner();
		System.out.println(inner.str);
		System.out.println("main completed");
	}
}
