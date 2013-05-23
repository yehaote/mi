package mi.practice.java.base.strings;

import java.util.Scanner;
/**
 * Scanner用来读取更加智能，
 * 可以对一个读取的数据制定类型
 * @author waf
 */
public class BetterRead {
	public static void main(String[] args){
		Scanner stdin=new Scanner(SimpleRead.input);
		System.out.println("What's your name?");
		String name = stdin.nextLine();
		System.out.println("How old are you? What is your favorite double?");
		System.out.println("input <age> <double>");
		int age = stdin.nextInt();
		double favorite =stdin.nextDouble();
		System.out.println(age);
		System.out.println(favorite);
		System.out.format("Hi %s.\n",name);
		System.out.format("In 5 years you will be %d.\n", age+5);
		System.out.format("My favorite double is %f.",favorite/2);
	}
}
