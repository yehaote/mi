package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
/**
 * 当生成一个Iterator以后, 
 * 如果Collection修改了的话, 
 * 在对it进行操作会抛出异常.
 */
public class FailFast {
	public static void main(String[] args){
		Collection<String> c = new ArrayList<String>();
		Iterator<String> it = c.iterator();
		c.add("An object");
		try{
			@SuppressWarnings("unused")
			String s = it.next();
		}catch (ConcurrentModificationException e) {
			System.out.println(e);
		}
	}
}
