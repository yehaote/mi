package mi.practice.java.base.generics;
/**
 * BasicHolder可以使用任何的类型作为type参数
 * @author waf
 */
class Other{}
class BasicOther extends BasicHolder<Other>{}
public class Unconstrained {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		BasicOther b = new BasicOther(),
				b2 = new BasicOther();
		b.set(new Other());
		Other other = b.get();
		b.f();
	}
}
