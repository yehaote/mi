package mi.practice.java.base.generics;
/**
 * 也可以使用工厂的方式给泛型提供新建实例的方法
 * @author waf
 * @param <T>
 */
interface FactoryI<T>{
	T create();
}

class Foo2<T>{
	@SuppressWarnings("unused")
	private T x;
	@SuppressWarnings("hiding")
	public <F extends FactoryI<T>> Foo2(F factory){
		x=factory.create();
	}
}

class IntegerFactory implements FactoryI<Integer>{
	@Override
	public Integer create() {
		return new Integer(0);
	}
}

class Widget{
	public static class Factory implements FactoryI<Widget>{
		@Override
		public Widget create() {
			return new Widget();
		}
	}
}
public class FactoryConstraint {
	public static void main(String[] args){
		new Foo2<Integer>(new IntegerFactory());
		new Foo2<Widget>(new Widget.Factory());
	}
}
