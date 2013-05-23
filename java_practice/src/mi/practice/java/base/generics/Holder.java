package mi.practice.java.base.generics;

public class Holder<T> {
	private T value;
	public Holder(){}
	public Holder(T val){
		this.value=val;
	}
	public void set(T val){
		this.value=val;
	}
	public T get(){
		return value;
	}
	@Override
	public boolean equals(Object obj){
		return value.equals(obj);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Holder<Apple> apple= new Holder<Apple>(new Apple());
		Apple d = apple.get();
		apple.set(d);
//		Holder<Fruit> fruit= apple; // 不能转换
		Holder<? extends Fruit> fruit = apple; //可以转换
		Fruit p= fruit.get();
		//返回? extends Fruit类型
		d=(Apple) fruit.get();
		try{
			//转化发生异常
			Orange c = (Orange) fruit.get();
		}catch (Exception e) {
			System.out.println(e);
		}
		//set 还是不行, 因为编译器不能验证? extends Fruit类型
//		fruit.set(new Apple()); //不能调用
//		fruit.set(new Fruit()); //不能调用
		System.out.println(fruit.equals(d));
	}
}