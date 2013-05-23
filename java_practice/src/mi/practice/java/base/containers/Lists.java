package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import mi.practice.java.base.util.Countries;
import static mi.practice.java.base.util.Print.*;

public class Lists {
	@SuppressWarnings("unused")
	private static boolean b;
	@SuppressWarnings("unused")
	private static String s;
	@SuppressWarnings("unused")
	private static int i;
	@SuppressWarnings("unused")
	private static Iterator<String> it;
	@SuppressWarnings("unused")
	private static ListIterator<String> lit; // list迭代器
	
	/**
	 * 测试List的一些基本操作
	 */
	public static void basicTest(List<String> a) {
		a.add(1, "x"); // 增加元书在一号位置
		a.add("x"); // 在最后增加元素
		a.addAll(Countries.names(25)); // 增加一个Collection
		a.addAll(3, Countries.names(25)); // 在3号位置增加一个Collection
		b = a.contains("1");
		b = a.containsAll(Countries.names(25));
		// list支持随机访问, 对于ArrayList性能是很高的,
		// 而对于LinkedList开销很大
		s = a.get(1); // 根据位置获取元素
		i = a.indexOf("1"); // 根据元素查询位置
		i = a.lastIndexOf("1"); // 根据元素查最后一个位置
		b = a.isEmpty();
		it = a.iterator();
		lit = a.listIterator();
		lit = a.listIterator(3); // 获取一个List迭代器, 并从3号位置开始
		a.remove(1); // 根据位置删除元素
		a.remove("3"); // 根据元素去删除元素
		a.set(1, "y"); // 设置1号位置为y
		a.retainAll(Countries.names(25));
		a.removeAll(Countries.names(25));
		i = a.size();
		a.clear();
	}
	
	public static void iterMotion(List<String> a){
		ListIterator<String> it = a.listIterator();
		b=it.hasNext();
		b=it.hasPrevious();//*
		s=it.next();
		i=it.nextIndex();//*
		s=it.previous();//*
		i=it.previousIndex();//*
	}
	
	public static void iterManipulation(List<String> a){
		ListIterator<String> it = a.listIterator();
		it.add("47");
		// 增加一个元素后必须移到下一个元素
		it.next();
		
		it.remove();
		// 删除一个元素后必须移到下一个元素
		it.next();
		// 删除后可以set值
		it.set("47");
	}
	
	public static void testVisual(List<String> a){
		print(a);
		List<String> b = Countries.names(25);
		print("b = "+b);
		a.addAll(b);
		a.addAll(b);
		print(a);
		// Insert, remove, and replace elements
		// using a ListIterator
		ListIterator<String> x = a.listIterator(a.size()/2);
		x.add("One");
		print(a);
		print(x.next());
		x.remove();
		print(x.next());
		x.set("47");
		print(a);
		// 从后向前遍历
		x=a.listIterator(a.size());
		while(x.hasPrevious()){
			printnb(x.previous()+" ");
		}
		print();
		print("testVisual finished");
	}
	
	//LinkedList 有一些独有的方法
	public static void testzLinkedList(){
		LinkedList<String> ll = new LinkedList<String>();
		ll.addAll(Countries.names(25));
		print(ll);
		// 像一个stack(栈)一样操作它, pushing
		ll.addFirst("one");
		ll.addFirst("two");
		print(ll);
		// 查看第一个元素, 不操作
//		print(ll.peek()); // 底层也是调用getFirst, 只是做了size判断
		print(ll.getFirst());
		// 像pop一样, 出栈
//		ll.poll(); // 底层调用removeFirst
//		ll.pollFirst();// 跟上面的一样
		print(ll.removeFirst());
		print(ll.removeFirst());
		// 像队列一样操作它
		print(ll.removeLast());
		print(ll);
	}
	
	public static void main(String[] args){
		basicTest(new LinkedList<String>(Countries.names(25)));
		basicTest(new ArrayList<String>(Countries.names(25)));
		iterMotion(new LinkedList<String>(Countries.names(25)));
		iterMotion(new ArrayList<String>(Countries.names(25)));
		iterManipulation(new LinkedList<String>(Countries.names(25)));
		iterManipulation(new ArrayList<String>(Countries.names(25)));
		testVisual(new LinkedList<String>(Countries.names(25)));
		testzLinkedList();
	}
}
