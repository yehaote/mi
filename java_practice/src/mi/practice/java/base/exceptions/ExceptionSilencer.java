package mi.practice.java.base.exceptions;
/**
 * 当在finally里指定return的时候, 
 * 会把try里的异常抹去
 */
public class ExceptionSilencer {
	@SuppressWarnings("finally")
	public static void main(String[] args){
		try{
			throw new RuntimeException();
		}finally{
			return;
		}
	}
}
