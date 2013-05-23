package mi.practice.java.base.arrays;

import java.util.Arrays;
import java.util.Random;
/**
 * 像C或者C++, 当返回一个值不仅仅是一个数据, 而是一批数据的时候就会比较麻烦,
 * 因为返回的数组的生命周期要自己控制, 不然的话就可能内存泄露.
 * 在大多数情况下, 返回一批数据的时候基本都是直接返回一个引用.
 * 而Java则没这个问题.
 * @author waf
 */
public class IceCream {
	private static Random rand = new Random(47);
	static final String[] FLAVORS ={"Chocolate", "Strawberry", "Vanilla Fudge Swirl",
		"Mini Chip", "Mocha Almod Fudge", "Rum Raisin", "Parline Cream", "Mud Pie"};
	public static String[] flavorSet(int n){
		if(n>FLAVORS.length){
			throw new IllegalArgumentException("Set too big");
		}
		String[] result = new String[n];
		boolean[] picked=new boolean[FLAVORS.length];
		for(int i=0;i<n;i++){
			int t;
			// 找到没有被写入过的值
			do{
				t = rand.nextInt(FLAVORS.length);
			}while(picked[t]);
			result[i]=FLAVORS[t];
			picked[t]=true;
		}
		return result;
	}
	
	public static void main(String[] args){
		for(int i=0;i<7;i++){
			System.out.println(Arrays.toString(flavorSet(3)));
		}
	}
}
