package mi.practice.java.base.generics;
/**
 * 因为擦写的缘故不能指定对象哪怕它本身就有的方法, 
 * 不像C++一样
 * 不过我们可以通过限制来进行调用, 比如限制泛型的范围
 * extends在泛型中代表其类和子类
 * 在这个例子中T被擦写为HasF在类的范围内
 * @author waf
 * @param <T>
 */
public class Manipulator2 <T extends HasF>{
	private T object;
	public Manipulator2(T x){
		object=x;
	}
	public void manipulate(){
		//这样就可以进行调用了
		object.f();
	}
}
