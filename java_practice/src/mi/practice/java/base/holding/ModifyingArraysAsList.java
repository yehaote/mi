package mi.practice.java.base.holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * 第一种方式相当于做了个拷贝, 
 * 对数据进行更改不会影响到原来的array
 * 第二种方式:
 * 直接使用Arrays.asList()来返回List的话,
 * 这个List会把array做为它自己的底层(物理)实现
 * 对list更改会直接影响到原来array
 */
public class ModifyingArraysAsList {
	public static void main(String[] args){
		Random rand = new Random(47);
		Integer[] ia ={1,2,3,4,5,6,7,8,9,10};
		List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(ia));
		System.out.println("Before shuffling: "+list1);
		Collections.shuffle(list1, rand);
		System.out.println("After shuffling: "+list1);
		System.out.println("array: "+Arrays.toString(ia));
		
		List<Integer> list2 = Arrays.asList(ia);
		System.out.println("Before shuffling: "+list2);
		Collections.shuffle(list2);
		System.out.println("After shuffling: "+list2);
		System.out.println("array: "+Arrays.toString(ia));
		
	}
}
