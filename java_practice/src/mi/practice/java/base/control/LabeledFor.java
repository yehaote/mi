package mi.practice.java.base.control;

import static mi.practice.java.base.util.Print.*;

/**
 * for循环结合label演示
 */
public class LabeledFor {

	public static void main(String[] args) {
		int i = 0;
		outer: for (; true;) {
			inner: for (; i < 10; i++) {
				print("i = " + i);
				if (i == 2) {
					print("continue");
					continue;
				}
				if (i == 3) {
					print("break");
					i++;// 如果没有这句的话, i会都不增加
					break;
				}
				if (i == 7) {
					print("continue outer");
					i++;
					continue outer;
				}
				if (i == 8) {
					print("break outer");
					i++;
					break outer;
				}
				for (int k = 0; k < 5; k++) {
					if (k == 3) {
						print("continue inner");
						continue inner;
					}
				}
			}
		}
	}

}
