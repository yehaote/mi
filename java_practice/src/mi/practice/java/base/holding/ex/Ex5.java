package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static mi.practice.java.base.util.Print.*;

public class Ex5 {
	static List<Integer> listOfRandom(int length, int n){
		Random rand = new Random(7);
		List<Integer> result = new ArrayList<Integer>();
		for(int i=0;i<length;i++){
			result.add(rand.nextInt(n));
		}
		return result;
	}
	public static void main(String[] args) {
		Random rand = new Random(47);
		//生成新的Integer序列
		List<Integer> ints =listOfRandom(7, 10);
		print("1: " + ints);
		//增加新元素
		Integer integer = new Integer(rand.nextInt(10));
		ints.add(integer);// Automatically resizes
		print("2: " + ints);
		print("3: " + ints.contains(integer));
		ints.remove(integer); // Remove by object
		Integer n = ints.get(2);
		print("4: " + n + " " + ints.indexOf(n));
		Integer m = new Integer(rand.nextInt(10));
		print("5: " + ints.indexOf(m));
		print("6: " + ints.remove(m));
		// Must be the exact object
		print("7: " + ints.remove(n));
		print("8: " + ints);
		ints.add(3, new Integer(rand.nextInt(10)));
		print("9: " + ints);
		List<Integer> sub = ints.subList(1, 4);
		print("sublist: " + sub);
		print("10: " + ints.containsAll(sub));
		Collections.sort(sub);// In-place sort
		print("sorted subList: " + sub);
		// Order is not important in containsAll();
		print("11: " + ints.containsAll(sub));
		Collections.shuffle(sub, rand);
		print("12: " + ints.containsAll(sub));
		List<Integer> copy = new ArrayList<Integer>(ints);
		sub = Arrays.asList(ints.get(1), ints.get(4)); // 跟书上结果好像不一样
		print("sub: " + sub);
		copy.retainAll(sub);
		print("13: " + copy);
		copy = new ArrayList<Integer>(ints); // Get a fresh copy
		copy.remove(2);
		print("14: " + copy);
		copy.removeAll(sub); // Only remove exact objects
		print("15: " + copy);
		copy.set(1,  new Integer(rand.nextInt(10))); // Replace an elements
		print("16: " + copy);
		copy.addAll(2, sub); // Insert a list in the middle
		print("17: " + copy);
		print("18: " + ints.isEmpty());
		ints.clear();
		print("19: " + ints);
		print("20: " + ints.isEmpty());
		ints.addAll(listOfRandom(4, 10));
		print("21: " + ints);
		Object[] o = ints.toArray();
		print("22: " + o[3]);
		Integer[] pa = ints.toArray(new Integer[0]);
		print("23: " + pa[3]);
	}

}
