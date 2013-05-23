package mi.practice.java.base.strings;
import static mi.practice.java.base.util.Print.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * matcher.find() 找到匹配的一段内容
 * matcher.lookingAt()  从字段串开头开始找到一串匹配的值, 必须是从0开始
 * matcher.matches() 把整个字符串作为一个整体判断是否匹配
 * @author waf
 */
public class StartEnd {
	public static String input=
			"As long as there is injustice, whenever a\n" +
			"Targathian baby cries out, wherever a distress\n" +
			"signal sounds among the stars ... We’ll be there.\n" +
			"This fine ship, and this fine crew ...\n" +
			"Never give up! Never surrender!";
	
	private static class Display{
		private boolean regexPrinted = false;
		private String regex;
		Display(String regex){
			this.regex= regex;
		}
		void display(String message){
			if(!regexPrinted){
				print(regex);
				regexPrinted=true;
			}
			print(message);
		}
	}
	
	static void examine(String s, String regex){
		Display d = new Display(regex);
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		while(m.find()){
			d.display("find() '"+m.group()+"' start="+m.start()+" end="+m.end());
		}
		if(m.lookingAt()){
			d.display("lookingAt() start="+m.start()+" end= "+m.end());
		}
		if(m.matches()){
			d.display("matches() start="+m.start()+" end="+m.end());
		}
	}
	
	public static void main(String[] args){
		for(String in:input.split("\n")){
			print("input : "+in);
			for(String regex:new String[]{"\\w*ere\\w*","\\w*ere","T\\w+","Never.*?!"}){
				examine(in, regex);
			}
		}
	}
}
