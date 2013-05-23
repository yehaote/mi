package mi.practice.java.base.exceptions.ex;
/**
 * 如果构造函数会产生异常, 应该在try里面进行构造
 * 也可以整个抛出, 或者rethrow
 * @author waf
 *
 */
class FailingConstructor{
	public FailingConstructor() throws ConstructionException{
		
	}
}

@SuppressWarnings("serial")
class ConstructionException extends Exception{}

public class Ex22 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		try{
			FailingConstructor fc = new FailingConstructor();
		}catch(ConstructionException e){
			System.out.println(e);
//			throw e;
		}
	}
}