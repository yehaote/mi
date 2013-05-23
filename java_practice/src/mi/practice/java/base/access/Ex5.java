package mi.practice.java.base.access;

public class Ex5 {
	private int a;
	int b;
	protected int c;
	public int d;
	
	private void privateSay(){
		System.out.println("privateSay");
	}
	
	void packageSay(){
		System.out.println("packageSay");
	}
	
	protected void protectedSay(){
		System.out.println("protectedSay");
	}
	
	public void publicSay(){
		System.out.println("publicSay");
	}
	
	public static void main(String[] args){
		Ex5 ex = new Ex5();
		System.out.println(ex.a);
		System.out.println(ex.b);
		System.out.println(ex.c);
		System.out.println(ex.d);
		ex.privateSay();
		ex.packageSay();
		ex.protectedSay();
		ex.publicSay();
	}
}
