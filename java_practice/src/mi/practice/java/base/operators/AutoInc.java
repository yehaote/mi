package mi.practice.java.base.operators;

/**
 * 自增, 自减示例
 * 前缀(prefix)先计算, 再提供值
 * 后缀(postfix)先提供值, 再计算
 */
public class AutoInc {

	public static void main(String[] args) {
		int i = 1;
		System.out.println("i : " + i);
		System.out.println("++i : " + ++i);
		System.out.println("i++ : " + i++);
		System.out.println("i : " + i);
		System.out.println("--i : " + --i);
		System.out.println("i-- : " + i--);
		System.out.println("i : " + i);
	}

}
