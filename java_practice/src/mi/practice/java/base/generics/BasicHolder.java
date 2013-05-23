package mi.practice.java.base.generics;

public class BasicHolder<T> {
	T element;
	void set(T arg){
		element = arg;
	}
	T get(){
		return element;
	}
	void f(){
		// element can only call method like Object
		System.out.println(element.getClass().getSimpleName());
	}
}
