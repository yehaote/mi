package mi.practice.java.base.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * matcher.reset()
 * 可以重新给这个matcher设置匹配的字符串,
 * 无参的方法返回这个字符串的开头
 * @author waf
 */
public class Resetting {
	public static void main(String[] args){
		Matcher m =Pattern.compile("[frb][aiu][gx]")
				.matcher("fix the rug with bags");
		while(m.find()){
			System.out.print(m.group()+" ");
		}
		System.out.println();
		m.reset("fix the rig with rags");
		while(m.find()){
			System.out.print(m.group()+" ");
		}
	}
}
