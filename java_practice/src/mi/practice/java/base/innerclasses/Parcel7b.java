package mi.practice.java.base.innerclasses;
/**
 * 匿名类的使用相当于这样实现的简写
 */
public class Parcel7b {
	class MyContents implements Contents{
		private int i=11;
		public int value(){
			return i;
		}
	}
	public Contents contents(){
		return new MyContents();
	}
	public static void main(String[] args){
		Parcel7b p = new Parcel7b();
		Contents c = p.contents();
		c.value();
	}
}
