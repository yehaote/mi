package mi.practice.java.base.arrays;

import java.util.Arrays;

import mi.practice.java.base.util.ConvertTo;
import mi.practice.java.base.util.CountingGenerator;
import mi.practice.java.base.util.Generated;

public class PrimitiveConversionDemonstration {
	public static void main(String[] args) {
		Integer[] a = Generated.array(Integer.class,
				new CountingGenerator.Integer(), 15);
		int[] b = ConvertTo.prmitive(a);
		System.out.println(Arrays.toString(b));

		boolean[] c = ConvertTo.prmitive(Generated.array(Boolean.class,
				new CountingGenerator.Boolean(), 7));
		System.out.println(Arrays.toString(c));
	}
}
