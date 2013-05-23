package mi.practice.java.base.arrays;

import mi.practice.java.base.util.CountingGenerator;
import mi.practice.java.base.util.Generator;
import mi.practice.java.base.util.RandomGenerator;
/**
 * 这里假设需要test的Class里面包含了一系列内嵌类,
 * 这里内嵌类都是Generator然后还有默认的构造函数.
 * getClass()返回所有的内嵌类
 * @author waf
 */
public class GeneratorsTest {
	public static int size=10;
	public static void test(Class<?> surroundingClass){
		// 获取class中所有的public class的成员, 
		// 如果class是基本类型跟数组返回0
		for(Class<?> type : surroundingClass.getClasses()){
			try{
				Generator<?> g= (Generator<?>) type.newInstance();
				for(int i=0;i<size;i++){
					System.out.print(g.next()+" ");
				}
				System.out.println();
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void main(String[] args){
		test(CountingGenerator.class);
		System.out.println();
		test(RandomGenerator.class);
	}
}
