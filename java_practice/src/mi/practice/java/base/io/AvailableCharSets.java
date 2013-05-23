package mi.practice.java.base.io;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;
/**
 * Java提供CharSet类来制定CharSet,
 * 查看系统的编码可以使用System.getProperty("file.encoding")来进行获取.
 * 
 */
public class AvailableCharSets {
	public static void main(String[] args){
		SortedMap<String, Charset> charSets = Charset.availableCharsets();
		Iterator<String> it = charSets.keySet().iterator();
		while(it.hasNext()){
			String csName = it.next();
			System.out.print(csName);
			Iterator<String> aliases = charSets.get(csName).aliases().iterator();
			if(aliases.hasNext()){
				System.out.print(": ");
			}
			while(aliases.hasNext()){
				System.out.print(aliases.next());
				if(aliases.hasNext()){
					System.out.print(", ");
				}
			}
			System.out.println();
		}
	}
}
