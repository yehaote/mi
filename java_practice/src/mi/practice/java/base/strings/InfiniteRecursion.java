package mi.practice.java.base.strings;

import java.util.ArrayList;
import java.util.List;
/**
 * 不能在toString方法里调用this,
 * 不然或出现死循环
 * @author waf
 */
public class InfiniteRecursion {
	@Override
	public String toString(){
		return "InfiniteRecursion address: "+this+" \n";
	}
	
	public static void main(String[] args){
		List<InfiniteRecursion> v = new ArrayList<InfiniteRecursion>();
		for(int i=0;i<10;i++){
			v.add(new InfiniteRecursion());
		}
		System.out.println(v);
	}
}