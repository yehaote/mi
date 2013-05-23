package mi.practice.java.base.innerclasses;

class Parcel4 {
	private class PContents implements Contents{
		private int i=11;
		@Override
		public int value(){
			return i;
		}
	}
	protected class PDestination implements Destination{
		private String label;
		private PDestination(String whereTo){
			label=whereTo;
		}
		@Override
		public String readLabel(){
			return label;
		}
	}
	public Destination destination(String s){
		return new PDestination(s);
	}
	public Contents contents(){
		return new PContents();
	}
}

public class TestParcel{
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Parcel4 p = new Parcel4();
		Contents c = p.contents();
		Destination d =p.destination("Tasmania");
//		Parcel4.PContents pc = p.new PContents(); //不能这么初始化, 因为PContents是private内嵌类, 在类外部无访问权限
	}
}
