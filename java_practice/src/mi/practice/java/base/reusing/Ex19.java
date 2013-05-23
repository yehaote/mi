package mi.practice.java.base.reusing;

public class Ex19 {
	private final String s;
	public Ex19(){
		s="Hello";
	}
	public Ex19(String s){
		this.s=s;
	}
	public static void main(String[] args){
		Ex19 ex1 = new Ex19();
		System.out.println(ex1.s);
//		s="another";
		Ex19 ex2 = new Ex19("ex2");
		System.out.println(ex2.s);
//		ex2.s="another";
	}
}
