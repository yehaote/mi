package mi.practice.java.base.innerclasses;
/**
 * 匿名类
 * 定义匿名类相当于说实现接口或者继承一个类
 * 然后直接返回成接口或者父类的引用
 */
public class Parcel7 {
	public Contents contents(){
		return new Contents(){//主意这个()
			private int i=11;
			public int value(){
				return i;
			}
		};//主意这个分号
	}
	public static void main(String[] args){
		Parcel7 p = new Parcel7();
		Contents c =p.contents();
		c.value();
	}
}
