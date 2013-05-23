package mi.practice.java.base.exceptions;
/**
 * java捕获异常是一直采取第一个命中的方法, 
 * 父类异常的catch可以捕获子类的异常
 * 捕获异常的顺序必须从子类开始再到父类, 
 * 不然的话对子类的捕获语句是不可达的
 * @author waf
 *
 */
@SuppressWarnings("serial")
class Annoyance extends Exception{}
@SuppressWarnings("serial")
class Sneeze extends  Annoyance{}

public class Human {
	@SuppressWarnings("hiding")
	public static void main(String[] args){
		try{
			throw new Sneeze();
		}catch(Sneeze s){
			System.out.println("Caught Sneeze");
		}catch(Annoyance a){
			System.out.println("Caught Annoyance");
		}
		
		try{
			throw new Sneeze();
		}catch(Annoyance a){
			System.out.println("Caught Annoyance");
		}
	}
}
