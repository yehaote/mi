package mi.practice.java.base.generics;

/**
 * java为了兼容性的问题在实现泛型的时候, 
 * 使用了擦写的模式
 * 擦写的模式也带来了很多损失,
 * 比如转换, intanceof操作, new操作
 * Java的泛型想对于其他语言来说显得很弱
 * @author waf
 * @param <T>
 */
class GenericBase<T>{
	private T element;
	public void set(T arg){
		element=arg;
	}
	public T get(){
		return element;
	}
}

class Derived1<T> extends GenericBase<T>{};
@SuppressWarnings("rawtypes")
class Derived2 extends GenericBase{};// 不报异常, 只有警告
//class Derived3 extends GenericBase<?>{} //不能编译, 
public class ErasureAndInheritance {
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		Derived2 d2 = new Derived2();
		Object obj=d2.get();
		d2.set(obj);//警告
	}
}
