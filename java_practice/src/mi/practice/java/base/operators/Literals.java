package mi.practice.java.base.operators;

public class Literals {

	public static void main(String[] args) {
		int i1 = 0x2f;// 十六进制
		System.out.println("i1: " + Integer.toBinaryString(i1));
		int i2 = 0X2F;// 十六进制
		System.out.println("i2: " + Integer.toBinaryString(i2));
		int i3 = 0177;// 八进制
		System.out.println("i3: " + Integer.toBinaryString(i3));
		char c = 0xffff;// char 最大值
		System.out.println("c: " + Integer.toBinaryString(c));
		byte b = 0x7f;// byte 最大值
		System.out.println("b: " + Integer.toBinaryString(b));
		short s = 0x7fff;// short 最大值
		System.out.println("s: " + Integer.toBinaryString(s));
		long n1 = 200L;
		System.out.println("n1: " + Long.toBinaryString(n1));
		long n2 = 200l;
		System.out.println("n2: " + Long.toBinaryString(n2));
		long n3 = 200;
		System.out.println("n3: " + Long.toBinaryString(n3));
//		float f1 = 1;
//		float f2 = 1F;
//		float f3 = 1f;
//		double d1 = 1d;
//		double d2 = 1D;
		System.out.println("Max float = " + Float.MAX_VALUE);
		System.out.println("Min float =: " + Float.MIN_VALUE);
		System.out.println("Max double = " + Double.MAX_VALUE);
		System.out.println("Min double = " + Double.MIN_VALUE);
	}

}
