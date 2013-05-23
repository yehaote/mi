package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Ex12 {
	public static void main(String[] args){
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			list.add(i);
		}
		System.out.println(list);
		List<Integer> reverse=new ArrayList<Integer>(list.size());
		ListIterator<Integer> it =list.listIterator(list.size());
		while(it.hasPrevious()){
			reverse.add(it.previous());
		}
		System.out.println(reverse);
	}
}
