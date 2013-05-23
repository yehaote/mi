package mi.practice.java.base.strings;
import static mi.practice.java.base.util.Print.*;
/**
 * 在java中String是不可更改的,
 * 所谓的更改实际上返回一个新的String
 * @author waf
 */
public class Immutable {
	public static String upcase(String s){
		return s.toUpperCase();
	}
	
	public static void main(String[] args){
		String q ="howdy";
		print(q);
		String qq = upcase(q);
		print(qq);
		print(q);
	}
}
