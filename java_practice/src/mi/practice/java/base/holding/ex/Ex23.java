 package mi.practice.java.base.holding.ex;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ex23 {
	public static void main(String[] args) {
		Random rand = new Random(47);
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10000; i++) {
			int r = rand.nextInt(20);
			Integer freq = m.get(r);
			m.put(r, freq == null ? 1 : freq + 1);
		}
		System.out.println(m);
		int maxId=0;
		int maxNum=0;
		for(Integer i : m.keySet()){
			int num=m.get(i);
			if(num>maxNum){
				maxId=i;
				maxNum=num;
			}
		}
		System.out.println(maxId+": "+maxNum);
	}
}