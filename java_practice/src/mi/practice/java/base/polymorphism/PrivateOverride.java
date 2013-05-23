package mi.practice.java.base.polymorphism;
/**
 * private方法是不会被覆盖的
 * 私有的f()对Derived是不可见的
 */
public class PrivateOverride {
	private void f(){
		System.out.println("private f()");
	}
	
	public static void main(String[] args){
		PrivateOverride po = new Derived();
		po.f();
	}
}

class Derived extends PrivateOverride{
	public void f(){
		System.out.println("public f()");
	}
}

