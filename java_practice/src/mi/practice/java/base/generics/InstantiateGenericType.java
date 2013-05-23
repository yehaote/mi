package mi.practice.java.base.generics;
/**
 * 在Java中使用了泛型以后不能使用new,
 * 因为不知道有没有对应的构造函数,
 * 可以使用下面这个方法采用Class的newInstance()来进行实例化, 
 * 不过要注意对应的类有没有无参的构造函数
 * @author waf
 * @param <T>
 */
class ClassAsFactory<T>{
	T x;
	public ClassAsFactory(Class<T> kind){
//		T x = new T();//不能这么做,可以改用下面的方法
		try {
			x=kind.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

class Employee{}

public class InstantiateGenericType {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		ClassAsFactory<Employee> fe=new ClassAsFactory<Employee>(Employee.class);
		System.out.println("ClassAsFactory<Employee> successed");
		try{
			ClassAsFactory<Integer> fi = new ClassAsFactory<Integer>(Integer.class);
			//不能初始化, 因为没有默认无参构造函数
		}catch(Exception e){
			System.out.println("ClassAsFactory<Integer> failed");
		}
	}
}
