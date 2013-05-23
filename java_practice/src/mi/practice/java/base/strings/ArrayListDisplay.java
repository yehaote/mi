package mi.practice.java.base.strings;

import java.util.ArrayList;
import java.util.List;

public class ArrayListDisplay {
	public static void main(String[] args){
		List<Integer> nums = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			nums.add(i);
		}
		System.out.println(nums);
	}
}
