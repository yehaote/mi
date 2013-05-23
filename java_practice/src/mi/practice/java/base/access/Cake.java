package mi.practice.java.base.access;
/**
 * 默认没有包名
 * 当两个类都没有指定包名的时候
 * 他们被存放在同一个java默认的包当中
 * 这个包名被称为"default package"
 */
public class Cake {
	public static void main(String[] args){
		Pie x = new Pie();
		x.f();
	}
}
