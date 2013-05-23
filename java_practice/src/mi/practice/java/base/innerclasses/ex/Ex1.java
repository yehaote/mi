package mi.practice.java.base.innerclasses.ex;
/**
 * Ex1, Ex3
 */
class Outer{
	private String str;
	Outer(String str){
		System.out.println("Outer("+str+")");
		this.str=str;
	}
	@Override
	public String toString(){
		return "Outer "+str;
	}
	class Inner{
		Inner(){
			System.out.println("Inner()");
		}
		public void displayOuter(){
			System.out.println("Inner.display() "+str);
		}
	}
	Inner getInner(){
		return new Inner();
	}
}
public class Ex1 {
	public static void main(String[] args){
		Outer outer = new Outer("a");
		Outer.Inner outin = outer.getInner();
		outin.displayOuter();
	}
}
