package mi.practice.java.base.innerclasses;
/**
 * 内部类, 在一个类中定义其他的类
 * 内部类的使用在语法上跟一般的类使用没有什么区别
 * 这里内部类跟一般类的区别是声明的时候, 
 * 它的名字接在被外部类之后, 
 * 这不是唯一的区别
 */
public class Parcel1 {
	class Contents {
		private int i = 11;

		public int value() {
			return i;
		}
	}
	class Destination{
		private String label;
		Destination(String whereTo){
			label = whereTo;
		}
		String readLabel(){
			return label;
		}
	}
	//使用内嵌类
	public void ship(String dest){
		@SuppressWarnings("unused")
		Contents c = new Contents();
		Destination	d = new Destination(dest);
		System.out.println(d.readLabel());
	}
	public static void main(String[] args){
		Parcel1 p = new Parcel1();
		p.ship("Tasmania");
	}
	
}
