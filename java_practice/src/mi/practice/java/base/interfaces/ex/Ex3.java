package mi.practice.java.base.interfaces.ex;
abstract class Ex3Super{
	public Ex3Super(){
		System.out.println("Before print()");
		print();
		System.out.println("After print()");
	}
	
	abstract void print();
}
class Ex3Sub extends Ex3Super{
	private int i=7;
	@Override
	void print(){
		System.out.println(i);
	}
}
public class Ex3 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Ex3Sub sub = new Ex3Sub();
	}
}
