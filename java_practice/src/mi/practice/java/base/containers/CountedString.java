package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountedString {
	// 存放所有CountedString的字符
	private static List<String> created= new ArrayList<String>();
	private String s;
	private int id =0;
	public CountedString(String str){
		// 把值赋为当前字符串
		s = str;
		// 加入全局数组中
		created.add(str);
		// 通过计算全局数组中当前有多少这个字符串, 获取id
		for(String s2 : created){
			if(s2.equals(s)){
				id++;
			}
		}
	}
	
	@Override
	public String toString(){
		return "String: " + s +" id: "+id+" hashCode(): "+hashCode();
	}
	
	@Override
	public int hashCode(){
		int result = 17;
		result = 37*result+s.hashCode();
		result = 37*result+id;
		return result;
	}
	
	@Override
	public boolean equals(Object o){
		return o instanceof CountedString && 
				s.equals(((CountedString)o).s) && id == ((CountedString)o).id;
	}
	
	public static void main(String[] args){
		Map<CountedString, Integer> map =new HashMap<CountedString, Integer>();
		CountedString[] cs = new CountedString[5];
		for(int i=0;i<cs.length;i++){
			cs[i] = new CountedString("hi");
			map.put(cs[i], i);
		}
		System.out.println(map);
		for(CountedString cstring : cs){
			System.out.println("Looking up "+cstring);
			System.out.println(map.get(cstring));
		}
	}
}
