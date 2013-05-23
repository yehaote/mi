package mi.practice.java.base.holding;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
/**
 * Hash是按值查找比较快的实现
 * Set根据"value"来判断两个对象的关系, 
 * 去除重复的, 相等的添加不进来
 * HashSet是根据hash值进行排序的
 */
public class SetOfInteger {
	public static void main(String[] args){
		Random rand = new Random(47);
		Set<Integer> intset = new HashSet<Integer>();
		for(int i=0;i<10000;i++){
			intset.add(rand.nextInt(30));
		}
		System.out.println(intset);
	}
}
