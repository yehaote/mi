package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mi.practice.java.base.util.Countries;
/**
 * 注意Collection接口里并没有get()方法,
 * 如果想要获取数据只能通过iterable
 */
public class CollectionMethods {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Collection<String> c = new ArrayList<String>();
		// 增加另外一个Collection
		c.addAll(Countries.names(6));
		// 增加一个元素
		c.add("ten");
		c.add("eleven");
		System.out.println(c);
		// 返回数据的数组
		Object[] array = c.toArray();
		// 返回一个具体类型的数组, 使用native方法建立数组, 
		// 好像只能针对对象类型
		String[] str = c.toArray(new String[0]);
		// 数组Collection里面的最大最小值,
		// Comparable接口比较大小
		System.out.println("Collections.max(c) = "+Collections.max(c));
		System.out.println("Collections.min(c) = "+Collections.min(c));
		
		Collection<String> c2 = new ArrayList<String>();
		c2.addAll(Countries.names(6));
		System.out.println(c);
		// 增加c2 的所有元素 
		c.addAll(c2);
		System.out.println(c);
		// 删除第一个,
		// 删除一个的时候如果有重复的只会删除一个
		c.remove(Countries.DATA[0][0]);
		System.out.println(c);
		// 删除第二个
		c.remove(Countries.DATA[1][0]);
		System.out.println(c);
		// 移除c2中的所有元素, 有多个的话, 也会都移除掉
		c.removeAll(c2);
		System.out.println(c);
		c.addAll(c2);
		System.out.println(c);
		String val = Countries.DATA[3][0];
		//查询一个元素是否在Collection中
		System.out.println("c.contains("+val+") = "+c.contains(val));
		//查询一个Collection是否在另一个Collection中
		System.out.println("c.containsAll("+c2+") = "+c.containsAll(c2));
		Collection<String> c3 = ((List<String>)c).subList(3, 5);
		System.out.println("c2 = "+c2);
		System.out.println("c3 = "+c3);
		// 返回所有在c2和c3中存在的元素, 交集
		// 在调用这个方法以后c2中的其他元素会被剔除掉
		c2.retainAll(c3);
		System.out.println("c2 = "+c2);
		c2.removeAll(c3);
		// 判断c2是否为空
		System.out.println("c2.isEmpty() = "+c2.isEmpty());
		c = new ArrayList<String>();
		c.addAll(Countries.names(6));
		System.out.println(c);
		c.clear();
		System.out.println("after c.clear() :"+ c);
	}
}
