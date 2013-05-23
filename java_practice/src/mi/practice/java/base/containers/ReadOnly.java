package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import mi.practice.java.base.util.Countries;
/**
 * Collections.unmodifiable*()可以返回一个只读的Container,
 *  注意: 在得到新的引用后反正错误应该把原来的引用去掉,
 *  或者是采用new而不分配引用的这种方式.
 *  当对只读的Container进行添加操作的时候会抛出UnsupportedOperationException
 */
public class ReadOnly {
	static Collection<String> data = new ArrayList<String>(Countries.names(6));
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Collection<String> c = Collections.unmodifiableCollection(new ArrayList<String>(data));
		System.out.println(c);
		//c.add("one");
		
		List<String> a = Collections.unmodifiableList(new ArrayList<String>(data));
		ListIterator<String> lit = a.listIterator();
		System.out.println(lit.next());
		//lit.add("one");
		
		Set<String> s = Collections.unmodifiableSet(new HashSet<String>(data));
		System.out.println(s);
		//s.add("one");
		
		Set<String> ss = Collections.unmodifiableSortedSet(new TreeSet<String>(data));
		
		Map<String, String> m = Collections.unmodifiableMap(new HashMap<String, String>(Countries.capitals(6)));
		System.out.println(m);
//		m.put("Ralph", "Howdy!");
		
		Map<String,String> sm = Collections.unmodifiableSortedMap(new TreeMap<String, String>(Countries.capitals(6)));
	}
}
