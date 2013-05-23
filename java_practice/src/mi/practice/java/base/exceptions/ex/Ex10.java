package mi.practice.java.base.exceptions.ex;
@SuppressWarnings("serial")
class Ex10E1 extends Exception{}
@SuppressWarnings("serial")
class Ex10E2 extends Exception{}
public class Ex10 {
	static void f() throws Ex10E1 {
		throw new Ex10E1();
	}
	static void g() throws Ex10E2{
		try{
			f();
		}catch(Ex10E1 e){
			Ex10E2 e2=new Ex10E2();
			e2.initCause(e);
			throw e2;
		}
	}
	
	public static void main(String[] args){
		try{
			g();
		}catch(Exception e){
			e.printStackTrace(System.out);
		}
	}
}
