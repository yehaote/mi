package mi.practice.java.base.typeinfo;

import static mi.practice.java.base.util.Print.*;

/**
 * instanceof 相等于 isInstanceOf()
 * == 相等于 .equals()
 * instanceof跟== 的最大区别在于,
 * ==是完全匹配而instanceof可以是它自己或者是它的继承类,
 * 换个角度来说instanceof差不多可以形容为>=
 * @author waf
 */
class Base{}
class Derived extends Base{}

public class FamilyVSExactType {
	static void test(Object x){
		print("Testing x of type "+x.getClass());
		print("x instanceof Base "+(x instanceof Base));
		print("x instanceof Derived "+(x instanceof Derived));
		print("Base.isIntance(x) "+(Base.class.isInstance(x)));
		print("Derived.isIntance(x)  "+(Derived.class.isInstance(x)));
		print("x.getClass() == Base.class "+(x.getClass()==Base.class));
		print("x.getClass() == Derived.class "+(x.getClass()== Derived.class));
		print("x.getClass().equals(Base.class) "+x.getClass().equals(Base.class));
		print("x.getClass().equals(Derived.class) "+x.getClass().equals(Derived.class));
	}
	public static void main(String[] args){
		test(new Base());
		test(new Derived());
	}
}
