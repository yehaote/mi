package mi.practice.java.base.util;
/**
 * 因为通过泛型无法创建基于基本类型的数组,
 * 这个工具可以把基本类型对象数组转化成数组.
 * @author waf
 */
public class ConvertTo {
	public static boolean[] prmitive(Boolean[] in){
		boolean[] result = new boolean[in.length];
		for(int i=0; i<in.length; i++){
			result[i]=in[i];// Autounboxing
		}
		return result;
	}
	
	public static char[] prmitive(Character[] in){
		char[] result = new char[in.length];
		for(int i=0; i<in.length; i++){
			result[i]=in[i];// Autounboxing
		}
		return result;
	}
	
	public static byte[] prmitive(Byte[] in){
		byte[] result = new byte[in.length];
		for(int i=0; i<in.length; i++){
			result[i]=in[i];// Autounboxing
		}
		return result;
	}
	
	public static short[] prmitive(Short[] in){
		short[] result = new short[in.length];
		for(int i=0; i<in.length; i++){
			result[i]=in[i];// Autounboxing
		}
		return result;
	}
	
	public static int[] prmitive(Integer[] in){
		int[] result = new int[in.length];
		for(int i=0; i<in.length; i++){
			result[i]=in[i];// Autounboxing
		}
		return result;
	}
	
	public static long[] prmitive(Long[] in){
		long[] result = new long[in.length];
		for(int i=0; i<in.length; i++){
			result[i]=in[i];// Autounboxing
		}
		return result;
	}
	
	public static float[] prmitive(Float[] in){
		float[] result = new float[in.length];
		for(int i=0; i<in.length; i++){
			result[i]=in[i];// Autounboxing
		}
		return result;
	}
	
	public static double[] prmitive(Double[] in){
		double[] result = new double[in.length];
		for(int i=0; i<in.length; i++){
			result[i]=in[i];// Autounboxing
		}
		return result;
	}
}
