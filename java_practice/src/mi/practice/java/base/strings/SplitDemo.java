package mi.practice.java.base.strings;
import static mi.practice.java.base.util.Print.*;

import java.util.Arrays;
import java.util.regex.Pattern;
/**
 * pattern.split()
 * 根据分隔符进行切分,
 * 还可以限制切分的次数通过limit
 * String.split()也是调用这个
 * @author waf
 */
public class SplitDemo {
	public static void main(String[] args){
		String input =
				"This!!unusual use!!of exclamation!!points";
		print(Arrays.toString(Pattern.compile("!!").split(input)));
		print(Arrays.toString(Pattern.compile("!!").split(input, 3)));
	}
}
