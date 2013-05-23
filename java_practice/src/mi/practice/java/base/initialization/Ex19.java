package mi.practice.java.base.initialization;

public class Ex19 {
	static void f(String... args){
		for(String str: args){
			System.out.print(str+"");
		}
		System.out.println();
	}
	
	public static void main(String[] args){
		f("a","b","c");
		f(new String("d"),new String("e"));
		f("f",new String("g"));
		f(new String[]{"h","i",new String("j")});
	}
}
