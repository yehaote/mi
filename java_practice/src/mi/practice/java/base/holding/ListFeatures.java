package mi.practice.java.base.holding;
//import java.util.*;
//import static mi.practice.java.base.util.Print.*;
/**
 * List比Collection多的方法, 在中间插入删除元素
 * ArrayList擅长随机访问, 当在中间插入删除元素的时候效率低
 * LinkedList随机访问效率低, 顺序访问效率高, 插入删除元素得到优化
 * 注意object.equals()的行为, 是用这个方法来判断对象是否相同的
 * Collections对子集进行排序会影响父集的顺序?
 */
//public class ListFeatures {
//	public static void main(String[] args){
//		Random rand = new Random(47);
//		List<Pet> pets = Pets.arrayList(7);
//		print("1: "+pets);
//		Hamster h = new Hamster();
//		pets.add(h);//Automatically resizes
//		print("2: "+pets);
//		print("3: "+pets.contains(h));
//		pets.remove(h); // Remove by object
//		Pet p =pets.get(2);
//		print("4: "+p+" "+pets.indexOf(p));
//		Pet cymric = new Cymric();
//		print("5: "+pets.indexOf(cymric));
//		print("6: "+pets.remove(cymric));
//		// Must be the exact object
//		print("7: "+pets.remove(p));
//		print("8: "+pets);
//		pets.add(3, new Mouse());
//		print("9: "+pets);
//		List<Pet> sub = pets.subList(1,4);
//		print("sublist: "+sub);
//		print("10: "+pets.containsAll(sub));
//		Collections.sort(sub);// In-place sort
//		print("sorted subList: "+sub);
//		// Order is not important in containsAll();
//		print("11: "+pets.containsAll(sub));
//		Collections.shuffle(sub,rand);
//		print("12: "+pets.containsAll(sub));
//		List<Pet> copy = new ArrayList<Pet>(pets);
//		sub = Arrays.asList(pets.get(1),pets.get(4)); //跟书上结果好像不一样
//		print("sub: "+sub);
//		copy.retainAll(sub);
//		print("13: "+copy);
//		copy = new ArrayList<Pet>(pets); // Get a fresh copy
//		copy.remove(2);
//		print("14: "+copy);
//		copy.removeAll(sub); // Only remove exact objects
//		print("15: "+copy);
//		copy.set(1, new Mouse()); // Replace an elements
//		print("16: "+copy);
//		copy.addAll(2,sub); // Insert a list in the middle
//		print("17: "+copy);
//		print("18: "+pets.isEmpty());
//		pets.clear();
//		print("19: "+pets);
//		print("20: "+pets.isEmpty());
//		pets.addAll(Pets.arrayList(4));
//		print("21: "+pets);
//		Object[] o =pets.toArray();
//		print("22: "+o[3]);
//		Pet[] pa =pets.toArray(new Pet[0]);
//		print("23: "+pa[3].id);
//	}
//}
