package mi.practice.java.base.containers;

import java.util.LinkedHashSet;
import java.util.Set;

import mi.practice.java.base.util.CollectionData;
import mi.practice.java.base.util.Generator;
/**
 * 输入的顺序跟输出的顺序一样是因为使用的是LinkedHashSet,
 * 保持了元素输入的顺序.
 * @author waf
 */
class Government implements Generator<String> {
	String[] foundation = ("strange women lying in ponds "
			+ "distributing sowrds is no basis of a system of " + "government")
			.split(" ");
	private int index;

	@Override
	public String next() {
		return foundation[index++];
	}
}

public class CollectionDataTest {
	public static void main(String[] args) {
		Set<String> set = new LinkedHashSet<String>(new CollectionData<String>(
				new Government(), 15));
		// 使用方便的形式
		set.addAll(CollectionData.list(new Government(), 15));
		System.out.println(set);
	}
}
