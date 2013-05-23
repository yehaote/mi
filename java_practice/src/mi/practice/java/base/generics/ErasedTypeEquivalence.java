package mi.practice.java.base.generics;

import java.util.ArrayList;
/**
 * ArrayList<Integer>和ArrayList<String>的Class类型是相同的
 * 
 * @author waf
 */
public class ErasedTypeEquivalence {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args){
		Class c1 = new ArrayList<Integer>().getClass();
		Class c2 = new ArrayList<String>().getClass();
		System.out.println(c1==c2);
	}
}
