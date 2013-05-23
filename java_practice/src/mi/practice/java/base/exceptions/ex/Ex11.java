package mi.practice.java.base.exceptions.ex;
@SuppressWarnings("serial")
class Ex11E extends Exception{}
public class Ex11 {
	static void f() throws Ex11E{
		throw new Ex11E();
	}
	static void g(){
		try{
			f();
		}catch(Ex11E e){
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args){
		g();
	}
}
