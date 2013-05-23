package mi.practice.java.base.io;

import java.io.PrintWriter;
/**
 * Java的System.out是一个Stream,
 * 如果想使用Writer的话可以包装一下.
 * 第二参数代表是否自动flush,
 * 不然有的时候会看不到输出结果, 
 * 应该还不够大小输出.
 */
public class ChangeSystemOut {
	public static void main(String[] args){
		PrintWriter out = new PrintWriter(System.out, true);
		out.println("Hello, world");
	}
}
