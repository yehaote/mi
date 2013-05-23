package mi.practice.java.base.reusing;
import static mi.practice.java.base.util.Print.*;

class Ex23_A{
	static int j =printInit("Ex23_A.j initialized");
	static int printInit(String s){
		print(s);
		return 47;
	}
	Ex23_A(){
		print("Ex23_A() constructor");
	}
}

class Ex23_B extends Ex23_A{
	static int k =printInit("Ex23_B.k initialized");
	Ex23_B(){
		print("Ex23_B() constructor");
	} 
}

class Ex23_C{
	static int n =printInit("Ex23_C.n initialized");
	static Ex23_A a = new Ex23_A();
	static int printInit(String s){
		print(s);
		return 47;
	}
	Ex23_C(){
		print("Ex23_C() constructor");
	}
}

class Ex23_D{
	Ex23_D(){
		print("Ex23_D() constructor");
	}
}
public class Ex23 extends Ex23_B{
	static int i = printInit("Ex23.i initialized");
	Ex23(){
		print("Ex23() constructor");
	}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		print("static initialized");
		print("normal class constructor called");
		Ex23 ex = new Ex23(); 
		print("Initialize Ex23_C's static");
		print(Ex23_C.a);
		print("Initialize Ex23_D");
		Ex23_D d = new Ex23_D();
		
	}
}
