package mi.practice.java.base.reusing;
/**
 * 练习8
 *
 */
class Ex8A{
	Ex8A(int i){
		System.out.println("Ex8A(int i)");
	}
}

class Ex8B extends Ex8A{
	Ex8B(){
		super(7);
		System.out.println("Ex8B()");
	}
	
	Ex8B(int i){
		super(i);
		System.out.println("Ex8B(int i)");
	}
}

public class Ex8 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Ex8B b = new Ex8B();
		Ex8B b2= new Ex8B(7);
	}
}
