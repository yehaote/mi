package mi.practice.java.base.operators;

/**
 * 转换示例
 */
public class Casting {

	public static void main(String[] args) {
		int i = 200;
		long lng = (long) i;
		lng = i;
		long lng2 = (long) 200;
		lng = 200;
		i = (int) lng2;// 必须要强制转换,上面都可以自动转换
		System.out.println(lng);
	}

}
