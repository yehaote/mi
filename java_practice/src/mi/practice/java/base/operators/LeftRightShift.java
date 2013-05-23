package mi.practice.java.base.operators;

/**
 * 左移, 右移操作示例
 */
public class LeftRightShift {

	public static void main(String[] args) {

		int i = 0x1000;
		for (int j = 0; j < 14; j++) {
			i >>= 1;
			System.out.println(Integer.toBinaryString(i));
		}
		System.out.println();

		i = -1;
		for (int j = 0; j < 5; j++) {
			i <<= 1;
			System.out.println(Integer.toBinaryString(i));
		}
		System.out.println();
		
		for (int j = 0; j < 13; j++) {
			i >>= 1;
			System.out.println(Integer.toBinaryString(i));
		}
	}

}
