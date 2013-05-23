package mi.practice.java.base.generics;
/*
 * 擦写会带来很多不好的副作用
 * instanceof
 * new
 * 等
 */
public class Erased <T>{
	@SuppressWarnings("unused")
	private final int SIZE=100;
	public static void f(Object arg){
//		if(arg instanceof T){ //error
//		}
//		T var = new T();//error
//		T[] array = new T[SIZE];//error
//		T[] array = (T)new Object[SIZE];
	}
}
