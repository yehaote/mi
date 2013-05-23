package mi.practice.java.base.exceptions;
/**
 * 重写的方法必须抛出被覆盖方法的异常,
 * 而且只能抛出被覆盖方法的异常及其继承异常
 * @author waf
 *
 */
//直接从Exception继承的异常类
@SuppressWarnings("serial")
class BaseballException extends Exception{}
//继承BaseballException
@SuppressWarnings("serial")
class Foul extends BaseballException{};
//继承BaseballException
@SuppressWarnings("serial")
class Strike extends BaseballException{};

/**
 * 在抽象类(基类)中抛出BaseballException
 */
abstract class Inning{
	public Inning()throws BaseballException{};
	public void event() throws BaseballException{
		//实际上没有抛出异常
	};
	//抛出
	public abstract void atBat()throws Strike, Foul;
	public void walk(){}
//	public void walk()throws PopFoul{}
}
//继承Exception
@SuppressWarnings("serial")
class StromExcpetion extends Exception{};
//继承StromExcpetion
@SuppressWarnings("serial")
class RainedOut extends StromExcpetion{};
//继承Foul->Foul继承BaseballException
@SuppressWarnings("serial")
class PopFoul extends Foul{};

/**
 * 定义接口, 抛出RainedOut异常类
 * RainedOut 继承 StromExcpetion 继承 Exception
 */
interface Storm{
	public void event() throws RainedOut;
	public void rainHard() throws RainedOut;
}

public class StormyInning extends Inning implements Storm{
	//在构造器里可以抛出其他的异常, 不过必须抛出基类的异常
	public StormyInning() throws RainedOut, BaseballException{
	}
	public StormyInning(String s)throws Foul, BaseballException{
	}
	//在父类中walk是public, 这里也必须声明为public, 子类覆盖的方法必须比父类的访问权限高
	//! void walk(){}
	//如果父类方法没有抛出异常, 子类也不行, 必须保持一致
	//! public void walk() throws PopFoul{};
	//如果父类抛出异常, 在子类中覆盖的时候可以不抛出异常
	// 异常在子类中只能更少, 不能更多
//	@Override
//	public void walk(){}
	//在接口中也一样, 不能在实现中添加额外的异常,但是可以减少
	//因为父类跟接口对异常的限定, 这里只能不抛出异常
	public void event(){}
	//! public void event() throws RainedOut{}
	//只有接口的限定
	@Override
	public void rainHard() throws RainedOut{
	}
	//覆盖方法可以抛出继承的异常代替
	@Override
	public void atBat() throws PopFoul {}
	
	public static void main(String[] args){
		try{
			StormyInning si = new StormyInning();
			si.atBat();
		}catch(PopFoul e){
			System.out.println("Pop foul");
		}catch(RainedOut e){
			System.out.println("Rained out");
		}catch(BaseballException e){
			System.out.println("Generic baseball exception");
		}
		//当把实现用父类的调用的时候, 必须处理父类的异常
		try{
			Inning i = new StormyInning();
			i.atBat();
		}catch (Strike e) {
			System.out.println("strike");
		}catch(Foul e){
			System.out.println("Foul");
		}catch(RainedOut e){
			System.out.println("Rained out");
		}catch (BaseballException e) {
			System.out.println("Generic baseball exception");
		}
	}
}