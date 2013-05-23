package mi.practice.java.base.exceptions.ex;

public class Ex8 {
	static void f()throws Ex4Exception{
		throw new Ex4Exception("in ex8 f()");
	}
	public static void main(String[] args){
		try{
			f();
		}catch(Ex4Exception e){
			e.printStackTrace(System.out);
		}
	}
}
