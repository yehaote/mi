package mi.practice.java.base.exceptions.ex;
/**
 * 在继承类中不能捕获父类构造器的异常
 * @author waf
 *
 */
@SuppressWarnings("serial")
class InningException extends Exception{}

class Base{
	public Base() throws InningException{
		//在父类中抛出异常
	}
}

class Extend extends Base{
	public Extend()throws InningException{
		//不能这样try-catch, 如果调用父类构造函数只能在第一行
//		try{
//			super();
//		}catch(InningException e){
//			
//		}
	}
}
public class Ex21 {

}
