package mi.practice.java.base.innerclasses;
/**
 * 在内部类中可以返回当前外套的类
 * 使用外套类名.this就可以返回当前外套类的实例了
 */
public class DotThis {
	void f(){
		System.out.println("DotThis.f()");
	}
	public class Inner{
		public DotThis outer(){
			return DotThis.this;//如果直接this会返回当前inner实例
		}
	}
	public Inner inner(){
		return new Inner();
	}
	public static void main(String[] args){
		DotThis dt = new DotThis();
		DotThis.Inner dti = dt.inner();
		dti.outer().f();
	}
}
