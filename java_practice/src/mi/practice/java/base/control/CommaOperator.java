package mi.practice.java.base.control;

/**
 * 逗号操作符演示
 * 在for循环中定义变量的部分可以用逗号同时定义多个变量, 不过多个变量必须为相同的类型
 * 在for循环中最后的部分(step部分), 可以同时进行多次操作, 用逗号分开
 * 这些逗号分开的语言, 依据次序顺序执行
 */
public class CommaOperator {

	public static void main(String[] args) {
		for (int i = 1, j = i + 10; i < 5; i++, j = i * 2) {
			System.out.println("i = " + i + " j = " + j);
		}
	}

}
