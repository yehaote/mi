package mi.practice.java.base.generics;
/**
 * 也可以使用模板模式来构建泛型的实例化
 * @author waf
 * @param <T>
 */
abstract class GenericWithCreate<T>{
	final T element;
	public GenericWithCreate() {
		element =create();
	}
	abstract T create();
}

class X{}

class Creator extends GenericWithCreate<X>{
	@Override
	X create() {
		return new X();
	}
	void f(){
		System.out.println(element.getClass().getSimpleName());
	}
}

public class CreatorGeneric {
	public static void main(String[] args){
		Creator c = new Creator();
		c.f();
	}
}
