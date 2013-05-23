package mi.practice.java.base.strings;

import static mi.practice.java.base.util.Print.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * m.find(index)
 * 从index开始查找
 * 这个方法会重置search position
 * 一般m.find()调用多次的时候, 
 * 会从上一次的m.find()的search position继续查找
 * @author waf
 */
public class Finding {
	public static void main(String[] args){
		Matcher m = Pattern.compile("\\w+")
				.matcher("Evening is full of the linnet's wings");
		while(m.find()){
			print(m.group()+" ");
		}
		print();
		int i=0;
		while(m.find(i)){
			print(m.group()+" ");
			i++;
		}
	}
}
