package mi.practice.java.base.initialization;

class Demo {
	static String str1 = "Hello 1";
	static String str2;
	static {
		str2 = "Hello 2";
	}
	
	static void print(){
		System.out.println(str1);
		System.out.println(str2);
	}
}

public class Ex14 {
	public static void main(String[] args){
		Demo.print();
	}
}
