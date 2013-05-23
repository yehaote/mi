package mi.practice.java.base.innerclasses;
/**
 * 实例初始化跟定义初始化的区别是:
 * 在实例初始化的时候还可以用if这些语句, 
 * 就像写一般的语句一样.
 * 实例初始化跟构造器的区别是实例初始化不能被重载.
 * 匿名类只能继承或者实现接口, 不能两者同时,
 * 也不能同时实现多个接口(如果把多个接口结合成一个接口就可以)
 */
public class Parcel10 {
	public Destination destination(final String dest, final float price){
		return new Destination() {
			private int cost;
			//进行实例初始化
			{
				cost= Math.round(price);
				if(cost > 100){
					System.out.println("Over budget!");
				}
			}
			private String label = dest;
			@Override
			public String readLabel() {
				return label;
			}
		};
	}
	public static void main(String[] args){
		Parcel10 p = new Parcel10();
		Destination d = p.destination("Tasmania", 101.395f);
		System.out.println(d.readLabel());
	}
}
