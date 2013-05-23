package mi.practice.java.base.generics;
/**
 * 新的类使用它自己作为泛型的参数跟返回值.
 * @author waf
 */
class Subtype extends BasicHolder<Subtype>{}

public class CRGWithBasicHolder {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Subtype st1 = new Subtype(),
				st2 = new Subtype();
		st1.set(st2);
		Subtype st3= st1.get();
		st1.f();
	}
}
