package mi.practice.java.base.innerclasses.ex;

public class Ex8 {
	class Ex8Inner{
		private int i=1;
		private void value(){
			System.out.println(i);
		}
	}
	
	void changeInner(Ex8Inner inner){
		inner.value();
		inner.i++;
		inner.value();
	}
	
	Ex8Inner inner(){
		return new Ex8Inner();
	}
	
	public static void main(String[] args){
		Ex8 ex8 = new Ex8();
		Ex8Inner inner = ex8.inner();
		ex8.changeInner(inner);
	}
}
