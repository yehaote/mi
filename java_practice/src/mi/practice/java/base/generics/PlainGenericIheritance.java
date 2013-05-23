package mi.practice.java.base.generics;
class GenericSetter<T>{// 没有自包含
	void set(T arg){
		System.out.println("GenericSetter.set(Base)");
	}
}
class DerivedGS extends GenericSetter<Base>{
	void set(Derived derived){
		System.out.println("DerivedGS.set(Derived)");
	}
}
public class PlainGenericIheritance {
	public static void main(String[] args){
		Base base = new Base();
		Derived derived = new Derived();
		DerivedGS dgs = new DerivedGS();
		dgs.set(derived);
		dgs.set(base);// 重载, 而不是覆盖
	}
}
