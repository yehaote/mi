package mi.practice.java.base.exceptions;
@SuppressWarnings("serial")
/**
 * Throwable类有一个printStackTrace()方法
 * 可以打印错误的追踪信息, 如果不制定参数的话, 
 * 它默认把信息输出到System.err流中
 */
class MyException extends Exception{
	public MyException(){
		super();
	}
	public MyException(String msg){
		super(msg);
	}
}
public class FullConstructors {
	public static void f() throws MyException{
		System.out.println("Throwing MyException from f()");
		throw new MyException();
	}
	public static void g() throws MyException{
		System.out.println("Throwing MyException form g()");
		throw new MyException("Originated in g()");
	}
	public static void main(String[] args){
		try{
			f();
		}catch (MyException e) {
			e.printStackTrace(System.out);
		}
		try{
			g();
		}catch (MyException e) {
			e.printStackTrace(System.out);
			System.out.println();
			e.printStackTrace();
		}
	}
}
