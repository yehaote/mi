package mi.practice.java.base.strings;
/**
 * 在java中对于String的操作符重载只有两个操作符,
 * + 和 +=
 * javap -c class文件可以查看编译后的代码
 * 编译器会对重载操作符用StringBuilder优化
 * @author waf
 *
 */
public class Concatenation {
	public static void main(String[] args){
		String mango = "mango";
		String s ="abc"+mango+"def"+47;
		System.out.println(s);
	}
}
