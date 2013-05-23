package mi.practice.java.base.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mi.practice.java.base.util.TextFile;
/***
 * java版简单的grep
 * @author waf
 */
/*参数：/home/waf/git/mi/java_practice/src/mi/practice/java/base/strings/JGrep.java (?mi)\b[Ssct]\w+*/
public class JGrep {
	public static void main(String[] args){
		if(args.length<2){
			System.out.println("Usage: java JGrep file regex");
			System.exit(0);
		}
		Pattern p = Pattern.compile(args[1]);
		int index =0;
		Matcher m = p.matcher("");
		for(String line:new TextFile(args[0])){
			m.reset(line);
			while(m.find()){
				System.out.println(index++ + ": "+m.group()+": "+m.start());
			}
		}
	}
}
