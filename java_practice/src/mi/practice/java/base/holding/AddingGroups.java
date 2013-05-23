package mi.practice.java.base.holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/**
 * 在java.util包中有一些常用的方法
 * Array.asList()返回一个List
 * Collections.addAll()把往collection里添加对象
 * Collections.addAll()速度很快, 所以可以先实例化一个Collection然后用这个方法添加元素
 */
public class AddingGroups {
	public static void main(String[] args){
		Collection<Integer> collection = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		Integer[] moreInts ={6,7,8,9,10};
		collection.addAll(Arrays.asList(moreInts));
		Collections.addAll(collection, 11,12,13,14,15);
		Collections.addAll(collection, moreInts);
		List<Integer> list=Arrays.asList(16,17,18,19,20);
		list.set(1, 99);
		System.out.println(list.getClass());
//		list.add(21); //运行期异常,为啥ArrayList不能添加元素呢 因为底层是一个数组
	}
}
