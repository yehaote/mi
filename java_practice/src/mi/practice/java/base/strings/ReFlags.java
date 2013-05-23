package mi.practice.java.base.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 支持模式转换,
 * 多个模式结合可以使用|
 * 最常使用的三个模式:
 * Pattern.CASE_INSENSITIVE //大小写忽略
 * Pattern.MULTILINE	//多行模式
 * Pattern.COMMENTS //注解模式, 忽略空格, 忽略#到一行结束
 * @author waf
 */
public class ReFlags {
	public static void main(String[] args){
		Pattern p = Pattern.compile("^java",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
		Matcher  m = p.matcher(
				"java has regex\nJava has regex\n"+
				"JAVA has pretty good regular expressions\n" +
				"Regular expressions are in Java");
		while(m.find()){
			System.out.println(m.group());
		}
//		System.out.println(m.lookingAt());
//		System.out.println(m.matches());
	}
}
