package mi.practice.java.base.exceptions;
import static mi.practice.java.base.util.Print.*;
/**
 * Exception的方法
 * getMessage(), getLocalizedMessage(), toString(),
 * printStackTrace()的信息量是依次递增的
 */
public class ExceptionMethods {
	public static void main(String[] args){
		try{
			throw new Exception("My Exception");
		}catch(Exception e){
			print("Caught Exception");
			print("getMessage(): "+e.getMessage());
			print("getLocalizedMessage(): "+e.getLocalizedMessage());
			print("toString(): "+e);
			print("printStackTrace(): ");
			e.printStackTrace(System.out);
		}
	}
}
