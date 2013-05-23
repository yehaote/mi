package mi.practice.java.base.holding;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * 在集合内不能使用基本类型
 * 会自动装箱成对象类型
 * get()方法返回null, 如果key在map中不存在的话
 */
public class Statistics {
	public static void main(String[] args) {
		Random rand = new Random(47);
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10000; i++) {
			int r = rand.nextInt(20);
			Integer freq = m.get(r);
			m.put(r, freq == null ? 1 : freq + 1);
		}
		System.out.println(m);
	}
}
