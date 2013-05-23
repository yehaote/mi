package mi.practice.java.base.strings;

import java.util.Arrays;
/**
 * String.split()可以根据正则表达式去切分
 * \\W 非单词, 数字好像也算单词
 * \\w 单词
 * \\d 数字
 * @author waf
 */
public class Splitting {
	public static String knights=
			"Then, when you have found the shrubbery, you must "+
			"cut down the mightiest tree in the forest... "+
			"with... a herring!";
	public static void split(String regex){
		System.out.println(Arrays.toString(knights.split(regex)));
	}
	public static void main(String[] args){
		split(" ");
		split("\\W+");
		split("n\\W+");
	}
}
