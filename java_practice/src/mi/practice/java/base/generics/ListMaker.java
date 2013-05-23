package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.List;
/**
 * 使用List来声明而不是实例化一个数组的时候却不报警告,
 * Arrays.newInstance()
 * @author waf
 * @param <T>
 */
public class ListMaker <T>{
	List<T> create(){
		return new ArrayList<T>();
	}
	@SuppressWarnings("unused")
	public static void main(String[] args){
		ListMaker<String> stringMaker = new ListMaker<String>();
		List<String> stringList=stringMaker.create();
	}
}
