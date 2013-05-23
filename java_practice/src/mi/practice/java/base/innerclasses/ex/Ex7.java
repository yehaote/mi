package mi.practice.java.base.innerclasses.ex;

class Ex7Outer{
	private int i=5;
	private int value(){
		System.out.println(" i = "+i);
		return i;
	}
	private class Ex7Inner{
		void outerIpp(){
			i++;
			value();
		}
	}
	void modify(){
		Ex7Inner inner = new Ex7Inner();
		inner.outerIpp();
	}
}
public class Ex7 {
	public static void main(String[] args){
		Ex7Outer outer = new Ex7Outer();
		outer.modify();
		outer.modify();
	}
}
