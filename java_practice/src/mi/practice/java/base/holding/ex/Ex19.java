package mi.practice.java.base.holding.ex;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class Ex19 {
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		set.add(23);
		set.add(67);
		set.add(34);
		set.add(45);
		set.add(12);
		set.add(56);
		set.add(78);
		System.out.println("HashSet: "+set);
		set= new TreeSet<Integer>(set);
		System.out.println("TreeSet: "+set);
		Set<Integer> linkedSet = new LinkedHashSet<Integer>();
		Iterator<Integer> it = set.iterator();
		while(it.hasNext()){
			linkedSet.add(it.next());
		}
		System.out.println("LinkedHashSet:"+set);
	}
}
