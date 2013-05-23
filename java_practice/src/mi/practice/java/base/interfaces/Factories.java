package mi.practice.java.base.interfaces;
/**
 * 工厂方法设计模式
 * 不是直接调用构造器实例化类, 
 * 而是调用一个方法在一个工厂对象里面去产生实例
 */
interface Service{
	void method1();
	void method2();
}
interface ServiceFactory{
	Service getService();
}
class Implemention1 implements Service{
	Implemention1(){}
	public void method1(){}
	public void method2(){}
}
class Implementation1Factory implements ServiceFactory{
	public Service getService(){
		return new Implemention1();
	}
}
class Implementation2 implements Service{
	Implementation2(){}
	public void method1(){}
	public void method2(){}
}
class Implementation2Factory implements ServiceFactory{
	public Service getService(){
		return new Implementation2();
	}
}
public class Factories {
	public static void serviceConsumer(ServiceFactory fact){
		Service s =fact.getService();
		s.method1();
		s.method2();
	}
	public static void main(String[] args){
		serviceConsumer(new Implementation1Factory());
		serviceConsumer(new Implementation2Factory());
	}
}
