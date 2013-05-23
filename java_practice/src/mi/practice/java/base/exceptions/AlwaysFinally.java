package mi.practice.java.base.exceptions;
import static mi.practice.java.base.util.Print.*;
@SuppressWarnings("serial")
/***
 * 就算在当前语块中没有处理异常, 
 * finally语块还是会执行
 * 注意下面执行的顺序
 * try->catch->finally
 * finally的语块都将被执行, 哪怕是在break,continue之后,
 * finally跟label break, label continue集合起来可以更好更好地消除go语句的需求
 */
class FourException extends Exception{}
public class AlwaysFinally {
	public static void main(String[] args){
		print("Entering first try block");
		try{
			print("Entering second try block");
			try{
				throw new FourException();
			}finally{
				print("finally in 2nd try block");
			}
		}catch(FourException e){
			print("Caught FourException in 1st try block");
		}finally{
			print("finally in 1st try block");
		}
		
		int i=0;
		while(true){
			try{
				System.out.println(i);
				if(i==5){
					break;
				}
				i++;
			}finally{
				System.out.println("finally i="+i);
			}
		}
	}
}
