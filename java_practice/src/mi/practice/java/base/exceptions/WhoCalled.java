package mi.practice.java.base.exceptions;
/**
 * StackTrackElement[] 可以由Exception.getStackTrace()返回
 * 第一个元素是离抛出异常最近的地方,
 * 而最后一个原始是抛出异常起始的地方
 */
public class WhoCalled {
	static void f(){
		try{
			throw new Exception();
		}catch (Exception e) {
			for(StackTraceElement ste:e.getStackTrace()){
				System.out.println(ste.getMethodName());
//				System.out.println(ste);
			}
		}
	}
	static void g(){
		f();
	}
	static void h(){
		g();
	}
	public static void main(String[] args){
		f();
		System.out.println("------------------------------");
		g();
		System.out.println("------------------------------");
		h();
	}
}
