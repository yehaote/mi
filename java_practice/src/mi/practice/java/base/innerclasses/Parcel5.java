package mi.practice.java.base.innerclasses;
/**
 * 把类定义嵌入在方法之内
 * 在方法里定义的类, 不能在方法之外进行访问
 * 在方法里定义的类出了访问上的不同以外, 
 * 跟一般的类也没有什么区别
 * 内部类的类名, 在同一个包下的不同类中不会重复
 */
public class Parcel5 {
	public Destination destination(String s) {
		class PDestination implements Destination {
			private String label;

			private PDestination(String whereTo) {
				label = whereTo;
			}

			public String readLabel() {
				return label;
			}
		}
		return new PDestination(s);
	}
	public static void main(String[] args){
		Parcel5 p = new Parcel5();
		p.destination("Tasmania");
	}
}
