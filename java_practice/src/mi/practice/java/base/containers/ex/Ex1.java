package mi.practice.java.base.containers.ex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ex1 {
	public static void main(String[] args){
		List<Integer> aList = new ArrayList<Integer>();
		for(int i=0;i<100;i++){
			aList.add(i);
		}
		System.out.println(aList);
		//随机打乱, 是直接作用在对象上的不用像String那样还要赋值回来
		Collections.shuffle(aList);
		System.out.println(aList);
	}
}
