package mi.practice.java.base.operators;

public class Bitwise {

	public static void main(String[] args) {
		int i=1;
		System.out.println(Integer.toBinaryString(i));
		System.out.println(Integer.numberOfLeadingZeros(i));
		System.out.println(Integer.numberOfTrailingZeros(i));
		
		int j=-1;
		System.out.println(Integer.toBinaryString(j));
		System.out.println(Integer.numberOfLeadingZeros(j));
		System.out.println(Integer.numberOfTrailingZeros(j));

		
		j>>>=1;
		System.out.println(Integer.toBinaryString(j));
		System.out.println(Integer.numberOfLeadingZeros(j));
		System.out.println(Integer.numberOfTrailingZeros(j));

		
		j=j<<2;
		System.out.println(Integer.toBinaryString(j));
		System.out.println(Integer.numberOfLeadingZeros(j));
		System.out.println(Integer.numberOfTrailingZeros(j));
	}

}
