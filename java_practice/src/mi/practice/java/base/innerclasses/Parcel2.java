package mi.practice.java.base.innerclasses;
/**
 * 通常在外部类中会有返回内部类的方法
 * 在外部引用的时候, 需要OuterClassName.InnerClassName
 */
public class Parcel2 {
	class Contents{
		private int i =11;
		public int value(){
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
	public Destination to(String s){
		return new Destination(s);
	}
	public Contents contents(){
		return new Contents();
	}
	public void ship(String dest){
		@SuppressWarnings("unused")
		Contents c = contents();
		Destination d = to(dest);
		System.out.println(d.readLabel());
	}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Parcel2 p = new Parcel2();
		p.ship("Tasmania");
		Parcel2 q = new Parcel2();
		Parcel2.Contents c = q.contents();
		Parcel2.Destination d = q.to("Borneo");
	}
}
