package mi.practice.java.base.control;

import static mi.practice.java.base.util.Range.*;

/**
 * break 结束当前循环, 并跳出整个循环
 * continue 结束当前循环, 并继续执行下一次循环
 * for循环的step在continue后还是会执行
 */
public class BreakAndContinue {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			if (i == 74) {
				break;
			}
			if (i % 9 != 0) {
				continue;
			}
			System.out.print(i + " ");
		}
		System.out.println();

		for (int i : range(100)) {
			if (i == 74) {
				break;
			}
			if (i % 9 != 0) {
				continue;
			}
			System.out.print(i + " ");
		}
		System.out.println();

		int i = 0;
		while (true) { // for(;true;) for(;;)
			i++;
			int j = i * 27;
			if (j == 1269) {
				break;
			}
			if (i % 10 != 0) {
				continue;
			}
			System.out.print(i + " ");
		}
		System.out.println();
	}

}
