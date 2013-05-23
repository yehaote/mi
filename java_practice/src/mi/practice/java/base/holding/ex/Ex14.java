package mi.practice.java.base.holding.ex;

import java.util.LinkedList;
import java.util.ListIterator;

public class Ex14 {
	public static void main(String[] args){
		LinkedList<Integer> ints = new LinkedList<Integer>();
		ListIterator<Integer> it = ints.listIterator();
		for(int i=0;i<10;i++){
			if(i%2==0){
				it.add(i);
			}else{
				it.add(i);
				it.previous();
			}
			System.out.println(ints);
		}
		ints = new LinkedList<Integer>();
		for(int i=0;i<10;i++){
			it = ints.listIterator(ints.size()/2);
			it.add(i);
			System.out.println(ints);
		}
	}
}
