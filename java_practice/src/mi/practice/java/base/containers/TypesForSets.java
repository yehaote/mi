package mi.practice.java.base.containers;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
/**
 * Set的接口跟Collection一样,
 * 不同的Set实现有不同的存储结构.
 * HashSet是为了快速查询,
 * 它的每个元素必须hashCode方法.
 * TreeSet保存了元素的一种排序, 
 * 元素必须实现Comparable接口.
 * LinkedHashMap在HashSet的基础上保留了
 * 元素添加的顺序, 元素也一样要实现hashCode接口.
 * 元素还需要实现equals()方法.
 * 为了好的编程习惯, 当你覆盖hashCode的时候也应该
 * 覆盖equals()方法.
 */
class SetType{
	int i;
	public SetType(int n){
		i=n;
	}
	@Override
	public boolean equals(Object o){
		return o instanceof SetType && (i== ((SetType)o).i);
	}
	@Override
	public String toString(){
		return Integer.toString(i);
	}
}

class HashType extends SetType{
	public HashType(int n){
		super(n);
	}
	@Override
	public int hashCode(){
		return i;
	}
}

class TreeType extends SetType implements Comparable<TreeType>{
	public TreeType(int n) {
		super(n);
	}
	@Override
	public int compareTo(TreeType o) {
		// 注意这里没有使用o.i-i
		// 主要是要考虑到越界的情况.
		// i-j, 当i很大, j很小(负数, 绝对值很大)的时候, 
		// i-j 就会溢出
		return (o.i < i ? -1 : (o.i == i ? 0 : 1));
	}
}

public class TypesForSets {
	static <T> Set<T> fill(Set<T> set, Class<T> type){
		try{
			for(int i=0;i<10;i++){
				set.add(type.getConstructor(int.class).newInstance(i));
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return set;
	}
	
	static <T> void test(Set<T> set, Class<T> type){
		fill(set, type);
		//尝试多复制几份
		fill(set, type);
		fill(set, type);
		System.out.println(set);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		test(new HashSet<HashType>(), HashType.class);
		test(new LinkedHashSet<HashType>(), HashType.class);
		test(new TreeSet<TreeType>(), TreeType.class);
		// 不能这么用, 这样不能起到想像的效果
		// 因为SetType, TreeType都没有覆盖HashCode方法,
		// 所以采用native的hashcode导致逻辑上错误.
		test(new HashSet<SetType>(), SetType.class);
		test(new HashSet<TreeType>(), TreeType.class);
		test(new LinkedHashSet<SetType>(), SetType.class);
		test(new LinkedHashSet<TreeType>(), TreeType.class);
		// TreeSet的成员一定要实现Comparable
		// 没有实现Comparable接口, 类型转换会出错.
		try{
			test(new TreeSet<SetType>(), SetType.class);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try{
			test(new TreeSet<HashType>(), HashType.class);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
