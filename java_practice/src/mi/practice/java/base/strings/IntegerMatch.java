package mi.practice.java.base.strings;
/**
 * String.matches()可以直接调用正则表达式对当前字符串进行检测,
 * 看字符串是否符合
 * @author waf
 *
 */
public class IntegerMatch {
	public static void main(String[] args){
		System.out.println("-1234".matches("-?\\d+"));
		System.out.println("5678".matches("-?\\d+"));
		System.out.println("+911".matches("-?\\d+"));
		System.out.println("+911".matches("(-|\\+)?\\d+"));
	}
}
