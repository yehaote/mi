package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class Ex11 {
	static void display(Collection<String> collection){
		Iterator<String> it = collection.iterator();
		System.out.print(collection.getClass().getSimpleName()+" ");
		while(it.hasNext()){
			System.out.print(it.next()+" ");
		}
		System.out.println();
	}
	static void append(Collection<String> collection){
		collection.add("one");
		collection.add("two");
		collection.add("three");
		collection.add("four");
		collection.add("five");
		collection.add("six");
		collection.add("seven");
	}
	public static void main(String[] args){
		ArrayList<String> arrayList = new ArrayList<String>();
		LinkedList<String> linkedList = new LinkedList<String>();
		HashSet<String> hashSet = new HashSet<String>();
		TreeSet<String> treeSet = new TreeSet<String>();
		append(arrayList);
		append(linkedList);
		append(hashSet);
		append(treeSet);
		display(arrayList);
		display(linkedList);
		display(hashSet);
		display(treeSet);
	}
}
