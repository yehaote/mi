package mi.practice.java.base.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
/**
 * Java允许重定向标准输入输出流,
 * 使用System.setIn()setOut()setErr()
 * 当输入输出数据量太大的时候重定向很好使用.
 * 
 */
public class Redirecting {
	public static void main(String[] args) throws IOException{
		PrintStream console = System.out;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream("/home/waf/tmp/TIJ/test"));
		PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("/home/waf/tmp/TIJ/test.out")));
		System.setIn(in);
		System.setOut(out);
		System.setErr(out);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s;
		while((s=br.readLine()) != null){
			System.out.println(s);
		}
		out.flush();
		out.close();// 记得关闭
		System.setOut(console);
	}
}
