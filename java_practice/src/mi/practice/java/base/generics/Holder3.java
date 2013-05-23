package mi.practice.java.base.generics;
/**
 * 我们希望在时候的时候再制定类型,
 * 所以就有了泛型这个东西
 * 类型放在<>之中
 * @author waf
 * @param <T>
 */
public class Holder3<T> {
	private T a ;
	public Holder3(T a){
		this.a=a;
	}
	public void set(T a){
		this.a=a;
	}
	public T get(){
		return a;
	}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Holder3<Automobile> h3 = new Holder3<Automobile>(new Automobile());
		Automobile a = h3.get();//不需要转换
//		h3.set("Not a Automobile");//error
//		h3.set(1);//error
	}
}
