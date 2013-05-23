package mi.practice.java.base.innerclasses;
/**
 * 定义匿名类的时候, 
 * 还可以使用参数对匿名类成员进行初始化
 * 但是参数必须定义为final
 */
public class Parcel9 {
	public Destination destination(final String dest){
		return new Destination(){
			private String label=dest;
			@Override
			public String readLabel() {
				return label;
			}
			
		};
	}
	public static void main(String[] args){
		Parcel9 p = new Parcel9();
		Destination d =p.destination("Tasmania");
		System.out.println(d.readLabel());
	}
}
