package mi.practice.java.base.generics;
/**
 * 使用object对象,
 * Holder2几乎能保存所有的数据类型(所有对象)
 * 虽说有的时候需要一个可以包含多类型数据的集合,
 * 但是大多时候还是单一类型的比较多
 * @author waf
 */
public class Holder2 {
	private Object a;
	public Holder2(Object a){
		this.a=a;
	}
	void set(Object a){
		this.a=a;
	}
	Object get(){
		return a;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Holder2 h2 = new Holder2(new Automobile());
		Automobile a = (Automobile)h2.get();
		//放入其他的元素
		h2.set("Not aAautomobile");
		String s = (String)h2.get();
		//还可以放入基本类型
		h2.set(1);
		Integer x = (Integer)h2.get();
	}
}
