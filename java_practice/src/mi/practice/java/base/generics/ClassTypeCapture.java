package mi.practice.java.base.generics;
/**
 * 在泛型类中传入一个CLass可以实现因擦写导致不能实现的一些效果,
 * 比如instanceof可以使用Class的isInstance()代替
 * @author waf
 */
class Building{}
class House extends Building{}
public class ClassTypeCapture<T> {
	Class<T> kind;
	public ClassTypeCapture(Class<T> kind){
		this.kind=kind;
	}
	public boolean f(Object arg){
		return kind.isInstance(arg);
	}
	public static void main(String[] args){
		ClassTypeCapture<Building> ctt1= new ClassTypeCapture<Building>(Building.class);
		System.out.println(ctt1.f(new Building()));
		System.out.println(ctt1.f(new House()));
		ClassTypeCapture<House> ctt2= new ClassTypeCapture<House>(House.class);
		System.out.println(ctt2.f(new Building()));
		System.out.println(ctt2.f(new House()));
	}
}
