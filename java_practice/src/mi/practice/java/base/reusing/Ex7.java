package mi.practice.java.base.reusing;
/**
 * 练习7
 */
class _A{
	_A(int a){
		System.out.println("_A()"+a);
	}
}

class _B{
	_B(int b){
		System.out.println("_B()"+b);
	}
}

class _C extends _A{
	_B b;
	_C(){
		super(7);
		b = new _B(7);
	}
}

public class Ex7 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		_C c = new _C();
	}
}
