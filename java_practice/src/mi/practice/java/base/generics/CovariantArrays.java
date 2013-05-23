package mi.practice.java.base.generics;
/**
 * 虽说编译器允许数组添加一些信息, 
 * 但是实际运行的时候会抛出异常.
 * @author waf
 */
class Fruit{}
class Apple extends Fruit{}
class Jonathan extends Apple{}
class Orange extends Fruit{}

public class CovariantArrays {
	public static void main(String[] args){
		// 执行的时候会新建apple数组, apple数组不是fruit数组,
		// 只能添加Apple及其子类
		Fruit[] fruit = new Apple[10];
		fruit[0] = new Apple();
		fruit[1] = new Jonathan();
		// Runtime type is Apple[], not Fruit[] or Orange[]:
		try{
			//编译器允许你添加Fruit;
			fruit[0] = new Fruit();
		}catch(Exception e){
			System.out.println(e);
		}
		try{
			//编译器允许你添加Oranges:
			fruit[0]= new Orange();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
