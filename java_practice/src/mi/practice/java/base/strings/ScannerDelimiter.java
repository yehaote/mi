package mi.practice.java.base.strings;

import java.util.Scanner;

/**
 * Scanner默认采用空格作为限定符
 * 可以使用scanner的has方法来查询是否有下一个元素
 * scanner.useDelimiter可以使用正则表达式来指定限定符
 * 这个例子不指定限定符的话啥都不显示?
 * @author waf
 */
public class ScannerDelimiter {
	public static void main(String[] args){
		Scanner scanner = new Scanner("12, 42, 78, 99, 42");
		while(scanner.hasNextInt()){
			System.out.println(scanner.nextInt());
		}
		
		scanner = new Scanner("12, 42, 78, 99, 42");
		scanner.useDelimiter("\\s*,\\s*");
		while(scanner.hasNextInt()){
			System.out.println(scanner.nextInt());
		}
	}
}
