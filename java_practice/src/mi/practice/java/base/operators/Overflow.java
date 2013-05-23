package mi.practice.java.base.operators;

/**
 * 溢出示例
 */
public class Overflow {

	public static void main(String[] args) {
		int big = Integer.MAX_VALUE;
		System.out.println("big = " + big);
		System.out.println("big's binary = " + Integer.toBinaryString(big));
		int bigger = big * 4;
		System.out.println("bigger = " + bigger);
		System.out.println("bigger's binary = " + Integer.toBinaryString(bigger));
	}

}
