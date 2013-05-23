package mi.practice.java.base.exceptions;
/**
 * 当你重新抛出一个新的异常的时候,
 * 你获得的效果跟fillInStackTrace()相似,
 * 所有相关的StackTrace信息将跟新的throw结合在一起
 * 而旧的throw信息将会丢失
 */
@SuppressWarnings("serial")
class OneException extends Exception{
	public OneException(String s){
		super(s);
	}
}
@SuppressWarnings("serial")
class TwoException extends Exception{
	public TwoException(String s){
		super(s);
	}
}
public class RethrowNew {
	public static void f() throws OneException{
		System.out.println("originating the exception in f()");
		throw new OneException("throw from f()");
	}
	public static void main(String[] args){
		try{
			try{
				f();
			}catch (OneException e) {
				System.out.println("Caught in inner try. e.printStackTrace()");
				e.printStackTrace(System.out);
				throw new TwoException("from inner try");
			}
		}catch(TwoException e){
			System.out.println("Caught in outer try, e.printStackTrace()");
			e.printStackTrace(System.out);
		}
	}
}
