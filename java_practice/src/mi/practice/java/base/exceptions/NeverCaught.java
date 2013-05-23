package mi.practice.java.base.exceptions;
/**
 * RuntimeException, 不需要检查的异常
 * 一般出现RuntimeException都是程序上的bug,
 * 不需要在写java的时候一直做null判断(仅仅是为了异常的话)之类的
 * RuntimeException不需要捕捉或做特殊的声明, 
 * 方法里抛出RuntimeException也不需要说明
 * RuntimeException特点:
 * 1.一般都是无法预料的.比如null引用是在你控制之外的
  */
public class NeverCaught {
	static void f(){
		throw new RuntimeException("From f()");
	}
	static void g(){
		f();
	}
	public static void main(String[] args){
		g();
	}
}
