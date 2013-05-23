package mi.practice.java.base.arrays;

import java.util.Arrays;

import mi.practice.java.base.util.ConvertTo;
import mi.practice.java.base.util.Generated;
import mi.practice.java.base.util.RandomGenerator;

public class TestArrayGeneration {
	public static void main(String[] args) {
		int size = 6;
		boolean[] a1 = ConvertTo.prmitive(Generated.array(Boolean.class,
				new RandomGenerator.Boolean(), size));
		System.out.println("a1 = "+Arrays.toString(a1));
		
		byte[] a2 = ConvertTo.prmitive(Generated.array(Byte.class,
				new RandomGenerator.Byte(), size));
		System.out.println("a2 = "+Arrays.toString(a2));
		
		char[] a3 = ConvertTo.prmitive(Generated.array(Character.class,
				new RandomGenerator.Character(), size));
		System.out.println("a3 = "+Arrays.toString(a3));
		
		short[] a4 = ConvertTo.prmitive(Generated.array(Short.class,
				new RandomGenerator.Short(), size));
		System.out.println("a4 = "+Arrays.toString(a4));
		
		int[] a5 = ConvertTo.prmitive(Generated.array(Integer.class,
				new RandomGenerator.Integer(), size));
		System.out.println("a5 = "+Arrays.toString(a5));
		
		long[] a6 = ConvertTo.prmitive(Generated.array(Long.class,
				new RandomGenerator.Long(), size));
		System.out.println("a6 = "+Arrays.toString(a6));
		
		float[] a7 = ConvertTo.prmitive(Generated.array(Float.class,
				new RandomGenerator.Float(), size));
		System.out.println("a7 = "+Arrays.toString(a7));
		
		double[] a8 = ConvertTo.prmitive(Generated.array(Double.class,
				new RandomGenerator.Double(), size));
		System.out.println("a8 = "+Arrays.toString(a8));
	}
}
