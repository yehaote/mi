package mi.practice.java.base.strings;

import java.util.Scanner;
import java.util.regex.MatchResult;
/**
 * scanner还支持正则的判断和取值
 * scanner.hasNext(pattern)
 * scanner.next(pattern)
 * 它跟正则查找有一个区别是到非命中就会终止
 * 它只对下一个输入进行匹配, 
 * 而输入是通过限定符分割的
 * @author waf
 */
public class ThreatAnalyzer {
	static String threatData = 
			"58.27.82.161@02/10/2005\n"
			+ "204.45.234.40@02/11/2005\n"
			+ "58.27.82.161@02/11/2005\n"
			+ "58.27.82.161@02/12/2005\n" 
			+ "58.27.82.161@02/12/2005\n"
			+ "[Next log section with different data format]";
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(threatData);
		String pattern="(\\d+[.]\\d+[.]\\d+[.]\\d+)@"+"(\\d{2}/\\d{2}/\\d{4})";
		while(scanner.hasNext(pattern)){
			scanner.next(pattern);
			MatchResult match=scanner.match();
			String ip=match.group(1);
			String data=match.group(2);
			System.out.format("Threat on %s from %s\n", data, ip);
		}
	}

}