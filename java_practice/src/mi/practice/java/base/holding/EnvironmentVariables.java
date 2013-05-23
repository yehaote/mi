package mi.practice.java.base.holding;

import java.util.Map.Entry;
/**
 * 打印所有的系统变量
 * 在java中获取系统变量是一个Map<String,String>
 * 
 */
public class EnvironmentVariables {
	public static void main(String[] args){
		for(Entry<String, String> entry:System.getenv().entrySet()){
			System.out.println(entry.getKey()+": "+entry.getValue());
		}
	}
}
