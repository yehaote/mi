package mi.practice.java.base.generics;
/**
 * 自包含的泛型类, 
 * 它的类型参数必须为它自己及其继承类.
 * Java的自包含也不是很严谨, 因为你可以通过使用rawtype绕过
 * type参数的限制.
 * @author waf
 * @param <T>
 */
class SelfBounded<T extends SelfBounded<T>> {
	T element;
	SelfBounded<T> set(T arg){
		element = arg;
		return this;
	}
	T get(){
		return element.get();
	}
}

class A extends SelfBounded<A>{}
class B extends SelfBounded<A>{} // Also OK
class C extends SelfBounded<C>{
	C setAndGet(C arg){
		set(arg);
		return get();
	}
}
class D{}
//class E extends SelfBounded<D>{} // 这样不行, 因为D必须为SelfBounded的子类
@SuppressWarnings("rawtypes")
class F extends SelfBounded{} // 这样也可以

public class SelfBounding{
	public static void main(String[] args){
		A a = new A();
		a.set(new A());
		a=a.set(new A()).get();
		C c = new C();
		c =c.setAndGet(new C());
	}
}
