package mi.practice.java.base.strings;

import static mi.practice.java.base.util.Print.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mi.practice.java.base.util.TextFile;
/**
 * replaceFirst()替换第一个命中
 * replaceAll()替换全部命中
 * appendReplacement()替换当前命中
 * appendTail()增加剩下的数据
 * 这些replace方法还可以使用$1, 这样的形式来获取当前捕获的分组
 * @author waf
 */

/*! Here’s a block of text to use as input to
 the regular expression matcher. Note that we’ll
 first extract the block of text by looking for
 the special delimiters, then process the
 extracted block. !*/

public class TheReplacements {
	public static void main(String[] args) {
		String s = TextFile
				.read("/home/waf/git/mi/java_practice/src/mi/practice/java/base/strings/TheReplacements.java");
		Matcher mIput = Pattern.compile("/\\*!(.*)!\\*/", Pattern.DOTALL)
				.matcher(s);
		if (mIput.find()) {
			s = mIput.group(1);
		}
		// 把两个以上的连续空格, 替换成一个空格
		s = s.replaceAll(" {2,}", " ");
		//(?m)开启多行模式
		//将行开头的空格去掉
		s=s.replaceAll("(?m)^ +", "");
		print(s);
		print();
		s=s.replaceFirst("[aeiou]", "(VOWEL1)");
		print(s);
		print();
		StringBuffer sbuf = new StringBuffer();
		Pattern p = Pattern.compile("[aeiou]");
		Matcher m = p.matcher(s);
		while(m.find()){
			m.appendReplacement(sbuf, m.group().toUpperCase());
		}
		//添加尾部的数据
		m.appendTail(sbuf);
		print(sbuf);
		
		String a="#abc##aaa#".replaceAll("#([^#]+)#", "$1");
		print(a);
		String b="#abc##aaa#".replaceFirst("#([^#]+)#", "$1");
		print(b);
		Pattern pattern = Pattern.compile("#([^#]+)#");
		Matcher matcher = pattern.matcher("#abc##edf##ss#");
		StringBuffer sb = new StringBuffer();
		int i=0;
		while(matcher.find()){
			matcher.appendReplacement(sb, "@$1"+i+"@");
			i++;
		}
		matcher.appendTail(sb);
		print(sb.toString());
	}
}
