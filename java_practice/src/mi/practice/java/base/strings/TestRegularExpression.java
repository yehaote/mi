package mi.practice.java.base.strings;

import static mi.practice.java.base.util.Print.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Pattern 类似于 编译完的正则表达式
 * matcher.group()返回默认的命中结果, 
 * .start() 返回命中的起始游标
 * .end() 返回命中的结尾游标+1
 * @author waf
 *
 */
public class TestRegularExpression {
	//输入: abcabcabcdefabc "abc+" "(abc)+" "(abc){2,}
	public static void main(String[] args){
		if(args.length < 2){
			print("Usage:\njava TestRegularExpression "+"characterSequence regularExpression+");
			System.exit(0);
		}
		print("Input: \""+args[0]+"\"");
		for(String arg:args){
			print("Regualr expression: +\""+arg+"\"");
			Pattern p =Pattern.compile(arg);
			Matcher m=p.matcher(args[0]);
			while(m.find()){
				print("Match \""+m.group()+"\" at positions "+m.start()+"-"+(m.end()-1));
			}
		}
	}
}
