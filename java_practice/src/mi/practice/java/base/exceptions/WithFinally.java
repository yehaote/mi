package mi.practice.java.base.exceptions;
/**
 * finally主要用于做一些清理, 
 * 比如IO,网络连接,或者外部的一些开关什么的
 * 无论是否有异常产生, finally语块里的语句都将执行
 */
public class WithFinally {
	static Switch sw = new Switch();
	public static void main(String[] args){
		try{
			sw.on();
			OnOffSwitch.f();
		}catch(OnOffException1 e){
			System.out.println("OnOffException1");
		}catch(OnOffException2 e){
			System.out.println("OnOffException2");
		}finally{
			sw.off();
		}
	}
}
