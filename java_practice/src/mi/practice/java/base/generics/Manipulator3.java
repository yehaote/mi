package mi.practice.java.base.generics;
/**
 * 我们也可以自己对类进行擦写, 而不使用泛型
 * 但是比如返回值什么的也会被擦写, 使用泛型可以有效得得到这些效果
 * @author waf
 */
public class Manipulator3 {
	private HasF obj;
	public Manipulator3(HasF x){
		obj=x;
	}
	public void manipulate(){
		obj.f();
	}
}
