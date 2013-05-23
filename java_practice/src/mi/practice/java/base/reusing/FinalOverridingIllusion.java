package mi.practice.java.base.reusing;

import static mi.practice.java.base.util.Print.*;

/**
 * 函数可以被声明成final
 * 有两个效果:1.让子类不能覆盖这个方法
 * 			2.提升性能
 * 提升性能在java SE5/6已经不建议你这么做了
 * 因为jvm会自己去优化
 * 所有的private函数默认是final的
 * 但是private函数可以被覆盖, 这点容易产生混淆
 * 其实不是覆盖而是重新定义函数
 * 因为private函数对子类是不可见的
 * @Override 注解可以解决这个问题
 */
@SuppressWarnings("unused")
class WithFinals{
	private final void f(){
		print("WithFinals.f()");
	}
	
	private void g(){
		print("WothFinals.g()");
	}
}
@SuppressWarnings("unused")
class OverridingPrivate extends WithFinals{
	private final void f(){
		print("OverridingPrivate.f()");
	}
	
	private void g(){
		print("OverridingPrivate.g()");
	}
}

class OverridingPrivate2 extends OverridingPrivate{
	public final void f(){
		print("OverridingPrivate2.f()");
	}
	
	public void g(){
		print("OverridingPrivate2.g()");
	}
}
public class FinalOverridingIllusion extends WithFinals{
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		OverridingPrivate2 op2 = new OverridingPrivate2();
		op2.f();
		op2.g();
		OverridingPrivate op = op2;
//		op.f();
//		op.g();
		WithFinals wf = op2;
//		wf.f();
//		wf.g();
	}
}
