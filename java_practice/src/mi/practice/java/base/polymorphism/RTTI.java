package mi.practice.java.base.polymorphism;
/**
 * "is-a"关系, 子类跟父类的接口一样
 * "is-like-a"关系, 子类比父类接口多
 * Java所有的类型转换都是检查的, 
 * 如果转换对象不符, 会抛出异常
 * 多态性代表不同的形式
 */
class Useful{
	public void f(){}
	public void g(){}
}
class MoreUseful extends Useful{
	@Override
	public void f(){}
	@Override
	public void g(){}
	public void u(){}
	public void v(){}
	public void w(){}
}
public class RTTI {
	public static void main(String[] args){
		Useful[] x = {new Useful(), new MoreUseful()};
		x[0].f();
		x[1].g();
//		x[1].u(); //不能调用, 父类引用没有这个方法
		((MoreUseful)x[1]).u();
		((MoreUseful)x[0]).u();//抛出异常
	}
}
