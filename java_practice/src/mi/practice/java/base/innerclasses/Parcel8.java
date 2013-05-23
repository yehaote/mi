package mi.practice.java.base.innerclasses;
/**
 * 在定义匿名类的时候, 可以调用父类的有参函数进行初始化
 */
public class Parcel8 {
	public Wrapping wrapping(int x){//给父类传参数
		return new Wrapping(x){
			@Override
			public int value(){
				return super.value()*47;
			}
		};
	}
	public static void main(String[] args){
		Parcel8 p = new Parcel8();
		Wrapping w =p.wrapping(10);
		w.display();
		int i=w.value();
		w.display();
		System.out.println(i);
	}
}
