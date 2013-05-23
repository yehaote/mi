package mi.practice.java.base.generics;

class Base{}
class Derived extends Base{}

interface OrdinaryGetter{
	Base get();
}

interface DerivedGetter extends OrdinaryGetter{
	// 在参数定制中覆盖的话允许参数类型变化, 但是只能是其子类
	@Override
	Derived get();
}
public class CovariantReturnTypes {
	void test(DerivedGetter d){
		@SuppressWarnings("unused")
		Derived d2 = d.get();
	}
}
