package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
/**
 * list跟set都是继承collection的接口
 */
class MovieGenerator{
	private static final String[] MOVIES= {"Snow White","Bashful","Doc","Dopey","Grumpy","Happy","Sleepy"};
	private int i=0;
	public String next(){
		if(i<MOVIES.length){
			return  MOVIES[i++];
		}
		return null;
	}
}
public class Ex4 {
	static void fill(Collection<String> collection){
		MovieGenerator generator = new MovieGenerator();
		while(true){
			String character = generator.next();
			if(character!=null){
				collection.add(character);
			}else{
				break;
			}
		}
	}
	public static void main(String[] args){
		//顺序跟加入顺序一致
		List<String> arrayList = new ArrayList<String>();
		List<String> linkedList= new LinkedList<String>();
		//不一样, 根据值查找速度快
		Set<String> hashSet = new HashSet<String>();
		Set<String> linkedHashSet = new LinkedHashSet<String>();
		//根据字母排序
		Set<String>	treeSet = new TreeSet<String>();
		fill(arrayList);
		fill(linkedList);
		fill(hashSet);
		fill(linkedHashSet);
		fill(treeSet);
		System.out.println(arrayList);
		System.out.println(linkedList);
		System.out.println(hashSet);
		System.out.println(linkedHashSet);
		System.out.println(treeSet);
	}
}
