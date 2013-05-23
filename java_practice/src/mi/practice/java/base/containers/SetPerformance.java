package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
/**
 * 在三个Set中HashSet的效率最好, 
 * 选择TreeSet和LinkedHashSet主要基于两个原因:
 * 1. treeSet保存存储有序
 * 2. LinkedHashSet给元素提供了另一种顺序, 
 * 		写入的顺序或者最近使用的顺序.
 */
public class SetPerformance {
	static List<Test<Set<Integer>>> tests = new ArrayList<Test<Set<Integer>>>();
	static {
		tests.add(new Test<Set<Integer>>("add") {
			/**
			 * 测试往set里添加数据
			 */
			@Override
			int test(Set<Integer> set, TestParam tp) {
				int loops = tp.loops;
				int size = tp.size;
				for(int i=0; i< loops;i++){
					set.clear();
					for(int j=0; j<size; j++){
						set.add(j);
					}
				}
				return loops * size;
			}
		});
		tests.add(new Test<Set<Integer>>("contains") {
			@Override
			int test(Set<Integer> set, TestParam tp) {
				int loops = tp.loops;
				int span = tp.size *2;
				for(int i=0;i<loops ;i++){
					for(int j=0;j<span;j++){
						set.contains(j);
					}
				}
				return loops * span;
			}
		});
		tests.add(new Test<Set<Integer>>("iterate") {
			@Override
			int test(Set<Integer> set, TestParam tp) {
				int loops = tp.loops;
				for(int i=0; i<loops; i++){
					Iterator<Integer> it = set.iterator();
					while(it.hasNext()){
						it.next();
					}
				}
				return loops * set.size();
			}
		});
	}
	
	public static void main(String[] args){
		if(args.length > 0){
			Tester.defaultParams = TestParam.array(args);
		}
		Tester.fieldWidth = 10;
		Tester.run(new TreeSet<Integer>(), tests);
		Tester.run(new HashSet<Integer>(), tests);
		Tester.run(new LinkedHashSet<Integer>(), tests);
	}
}
