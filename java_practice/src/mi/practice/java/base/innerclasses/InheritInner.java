package mi.practice.java.base.innerclasses;
/**
 * 当继承一个内部类的时候, 
 * 必须在构造器里面添加
 * enclosingClassReference.super();
 * 就是继承的内部类的外部类的引用
 */
class WithInner {
	class Inner {
		void say(){}
	}
}

public class InheritInner extends WithInner.Inner {
//	InheritInner() {} // 编译不通过
	InheritInner(WithInner wi) {
		wi.super();
	}
	public static void main(String[] args){
		WithInner wi = new WithInner();
		InheritInner ii = new InheritInner(wi);
		ii.say();
	}
}