package mi.practice.java.base.holding.ex;

import static mi.practice.java.base.util.Print.print;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Ex6 {
	public static void main(String[] args) {
		Random rand = new Random(47);
		//生成新的String序列
		List<String> ls =new ArrayList<String>();
		print("0: " + ls);
		Collections.addAll(ls, "oh","what", "a", "beautiful", "Manila", "Monday", "morning");
		print("1: " + ls);
		//增加新元素
		String h = new String("hi");
		ls.add(h);// Automatically resizes
		print("2: " + ls);
		print("3: " + ls.contains(h));
		ls.remove(h); // Remove by object
		String n = ls.get(2);
		print("4: " + n + " " + ls.indexOf(n));
		String m = new String("cy");
		print("5: " + ls.indexOf(m));
		print("6: " + ls.remove(m));
		// Must be the exact object
		print("7: " + ls.remove(n));
		print("8: " + ls);
		ls.add(3, new String("wonderful"));
		print("9: " + ls);
		List<String> sub = ls.subList(1, 4);
		print("sublist: " + sub);
		print("10: " + ls.containsAll(sub));
		Collections.sort(sub);// In-place sort
		print("sorted subList: " + sub);
		// Order is not important in containsAll();
		print("11: " + ls.containsAll(sub));
		Collections.shuffle(sub, rand);
		print("12: " + ls.containsAll(sub));
		List<String> copy = new ArrayList<String>(ls);
		sub = Arrays.asList(ls.get(1), ls.get(4)); // 跟书上结果好像不一样
		print("sub: " + sub);
		copy.retainAll(sub);
		print("13: " + copy);
		copy = new ArrayList<String>(ls); // Get a fresh copy
		copy.remove(2);
		print("14: " + copy);
		copy.removeAll(sub); // Only remove exact objects
		print("15: " + copy);
		copy.set(1,  new String("hello")); // Replace an elements
		print("16: " + copy);
		copy.addAll(2, sub); // Insert a list in the middle
		print("17: " + copy);
		print("18: " + ls.isEmpty());
		ls.clear();
		print("19: " + ls);
		print("20: " + ls.isEmpty());
		List<String> ts = new ArrayList<String>();
		Collections.addAll(ts, "one","two","three","four");
		ls.addAll(ts);
		print("21: " + ls);
		Object[] o = ls.toArray();
		print("22: " + o[3]);
		String[] pa = ls.toArray(new String[0]);
		print("23: " + pa[3]);
	}
}
