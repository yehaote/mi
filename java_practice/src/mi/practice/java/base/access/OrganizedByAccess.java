package mi.practice.java.base.access;
/**
 * 把类中的方法按访问修饰符排序
 * 让代码变得更加清晰(客户端编程人员能更加方法的看到public方法)
 * 顺序: public -> package access -> protected -> private
 */

@SuppressWarnings("unused")
public class OrganizedByAccess {
	public void pub1(){/* ... */};
	public void pub2(){/* ... */};
	public void pub3(){/* ... */};
	private void priv1(){ /* ... */};
	private void priv2(){ /* ... */};
	private void priv3(){ /* ... */};
	private int i;
	// ...
}
