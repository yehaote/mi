package mi.practice.java.base.exceptions;
import static mi.practice.java.base.util.Print.*;
/**
 * 当一个函数有多个返回的时候, 
 * 可以使用finally来做清理
 */
public class MultipleReturns {
	public static void f(int i){
		print("Initialization that requires cleanup");
		try{
			print("Point1");
			if(i==1){
				return;
			}
			print("Point2");
			if(i==2){
				return;
			}
			print("Point3");
			if(i==3){
				return;
			}
		}finally{
			print("Performing cleanup");
		}
	}
	public static void main(String[] args){
		for(int i=1;i<=4;i++){
			f(i);
			System.out.println();
		}
	}
}
