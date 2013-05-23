package mi.practice.java.base.strings;

import static mi.practice.java.base.util.Print.*;
/**
 * String可以使用正则进行替换, 
 * String.replaceFirst()替换第一个命中
 * String.replaceAll()替换所有命中
 * @author waf
 */
public class Replacing {
	static String  s = Splitting.knights;
	public static void main(String[] args){
		print(s.replaceFirst("f\\w+", "located"));
		print(s.replaceAll("shrubbery|tree|herring", "banana"));
	}
}
