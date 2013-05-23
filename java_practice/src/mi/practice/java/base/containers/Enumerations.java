package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;
/**
 * Vector就相当与现在的ArrayList,
 * Enumeration更像Iterator接口.
 * 而HashTable则使用HashMap代替.
 */
public class Enumerations {
	public static void main(String[] args){
		Vector<String> v = new Vector<String>();
		Enumeration<String> e = v.elements();
		while(e.hasMoreElements()){
			System.out.print(e.nextElement()+", ");
		}
		e=Collections.enumeration(new ArrayList<String>());
	}
}
