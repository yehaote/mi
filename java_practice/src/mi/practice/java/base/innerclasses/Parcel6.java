package mi.practice.java.base.innerclasses;
/**
 * 除了在方法中定义类以外, 
 * 还可以在if()之类的语句中定义类, 
 * 那样它的使用范围更加小
 * 虽说它是在if里面定义的可是它在编译的时候跟一般的类没有什么不同
 * 只是它的使用范围被限定了
 */
public class Parcel6 {
	private void internalTracking(boolean b){
		if(b){
			class TrackingSlip{
				private String id;
				TrackingSlip(String s){
					id=s;
				}
				String getSlip(){
					return id;
				}
			}
			TrackingSlip ts = new TrackingSlip("slip");
			@SuppressWarnings("unused")
			String s=ts.getSlip();
		}
//		TrackingSlip ts = new TrackingSlip("x"); //不能在这里定义,超出范围
	}
	public void track(){
		internalTracking(true);
	}
	public static void main(String[] args){
		Parcel6 p = new Parcel6();
		p.track();
	}
	
}
