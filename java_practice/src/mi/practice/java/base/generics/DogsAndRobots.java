package mi.practice.java.base.generics;

import mi.practice.java.base.typeinfo.pets.Dog;
/**
 * Java不支持Latent typing, 
 * Java对于类似的效果实现只能先定义一个接口,
 * 然后在使用泛型的时候把边界指向这个接口.
 * Python在使用Latent typing的时候, 是在执行的时候检查的,
 * 如果执行的时候没有对应的一些东西就会抛运行期异常.
 * C++的话, 在编译的时候就会做检查.
 * @author waf
 */
class PerformingDog extends Dog implements Performs{
	@Override
	public void speak() {
		System.out.println("Woof!");
	}

	@Override
	public void sit() {
		System.out.println("Sitting");
	}
	public void reproduce(){}
}
class Robot implements Performs{
	@Override
	public void speak() {
		System.out.println("Click");
	}
	@Override
	public void sit() {
		System.out.println("Clank!");
	}
	public void oilChange(){}
}
class Communicate{
	public static <T extends Performs>
		void perform(T performer){
		performer.speak();
		performer.sit();
	}
}
public class DogsAndRobots {
	public static void main(String[] args){
		PerformingDog  d = new PerformingDog();
		Robot r = new Robot();
		Communicate.perform(d);
		Communicate.perform(r);
	}
}
