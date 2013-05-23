package mi.practice.java.base.innerclasses;
/**
 * 当外套类没有提供返回内部类的实例的方法的时候, 
 * 我们可以直接使用.new来实例化这个内部类
 * 外部类的引用名.new Inner();
 * 不能直接创建一个内部类, 除非有已经创建了一个外部类
 * 如果是static内部类(内嵌类), 则不需要外部的引用
 */
public class DotNew {
	public class Inner{}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		DotNew dn = new DotNew();
		DotNew.Inner dni = dn.new Inner();
//		DotNew.Inner dni = dn.new DotNew.Inner(); //不能这么声明
	}
}
