package mi.practice.java.base.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
/**
 * FileWriter输出到一个文件
 * BufferedWriter缓存输出, 不用频繁占用io, 提交io的性能
 * PrintWriter打印格式输出.
 */
public class BasicFileOutput {
	static String file = "/home/waf/tmp/test";
	
	public static void main(String[] args)throws IOException{
		BufferedReader in = new BufferedReader(new StringReader(BufferedInputFile.read("/home/waf/tmp/test")));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		int lineCount = 1;
		String s;
		while((s=in.readLine())!=null){
			out.println((lineCount++)+": "+s);
		}
		out.close();
		System.out.println(BufferedInputFile.read(file));
	}
}
