package mi.practice.java.base.generics;
class OrdinarySetter{
	void set(Base base){
		System.out.println("OrdinarySetter.set(Base)");
	}
}
class DerivedSetter extends OrdinarySetter{
//	@Override // 不是覆盖
	void set(Derived derived){
		System.out.println("DerivedSetter.set(Derived)");
	}
}

public class OrdinaryArguments {
	public static void main(String[] args){
		Base base = new Base();
		Derived derived = new Derived();
		DerivedSetter ds = new DerivedSetter();
		//其实调用不同的方法, 而不是同一个方法
		ds.set(base);
		// 如果把DerivedSetter中的set(Derived)去掉, 会发现可以通过多态调用到set(Base base)
		ds.set(derived);
	}
}
