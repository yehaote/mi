package mi.practice.java.base.exceptions;
/**
 * 重新抛出异常, 可以在捕获以后直接throw出来
 * 如果你想更改异常里的信息的话,
 * 可以使用e.fillInStrackTrace();
 */
public class Rethrowing {
	public static void f() throws Exception{
		System.out.println("originating the exception in f()");
		throw new Exception("thrown from f()");
	}
	public static void g() throws Exception{
		try{
			f();
		}catch(Exception e){
			System.out.println("Inside g(), e.printStackTrace()");
			e.printStackTrace(System.out);
			throw e;
		}
	}
	public static void h()throws Exception{
		try{
			f();
		}catch(Exception e){
			System.out.println("Inside h(), e.printStackTrace()");
			e.printStackTrace(System.out);
			throw (Exception)e.fillInStackTrace();
		}
	}
	public static void main(String[] args){
		try{
			g();
		}catch( Exception e){
			System.out.println("main: printStackTrace()");
			e.printStackTrace(System.out);
		}
		System.out.println("-------------------------------------------");
		try{
			h();
		}catch(Exception e){
			System.out.println("main: printStackTrace()");
			e.printStackTrace(System.out);
		}
	}
}
