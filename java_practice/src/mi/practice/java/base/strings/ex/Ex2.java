package mi.practice.java.base.strings.ex;

import java.util.ArrayList;
import java.util.List;

/**
 * 采用super.toString代替this
 * @author waf
 *
 */
public class Ex2 {
	@Override
	public String toString(){
		return "InfiniteRecursion address: "+super.toString()+" \n";
	}
	
	public static void main(String[] args){
		List<Ex2> v = new ArrayList<Ex2>();
		for(int i=0;i<10;i++){
			v.add(new Ex2());
		}
		System.out.println(v);
	}
}
