package mi.practice.java.base.strings;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * 还有一个废弃的StringToken,
 * 在新版本中可以使用正则或者Scanner来获得功能更强的工具
 * @author waf
 */
public class ReplacingStringTokenizer {
	public static void main(String[] args){
		String input="But I'm not dead yet! I feel happy!";
		StringTokenizer stoke= new StringTokenizer(input);
		while(stoke.hasMoreElements()){
			System.out.print(stoke.nextElement()+" ");
		}
		System.out.println();
		System.out.println(Arrays.toString(input.split(" ")));
		Scanner scanner = new Scanner(input);
		while(scanner.hasNext()){
			System.out.print(scanner.next()+" ");
		}
	}
}
