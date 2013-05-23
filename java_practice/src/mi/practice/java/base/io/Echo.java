package mi.practice.java.base.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 在Unix系统中有标准IO的概念, 
 * 所有的程序都可以从标准输入读取数据,
 * 从标准输出输出数据, 错误信息可以写入到标准错误.
 * 这样可以把所有的程序通过标准IO联系在一起.
 * Java中的标准IO, System.in, System.out, System.err,
 * System.out和System.err都已经用PrintStream包装好了可以很方便的使用,
 * System.in只是一个InputStream, 使用的时候最好再包装一下.
 */
public class Echo {
	public static void main(String[] args) throws IOException{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String s;
		while((s=stdin.readLine()) !=null && s.length() != 0){
			System.out.println(s);
		}
	}
}
