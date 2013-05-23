package mi.practice.java.base.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * 在javaIO中流经常是一层包着一层,
 * 来获取到多种实现类的功能.
 * 也有一些简写的方法,
 * 比如用String去构建一个PrintWriter的时候, 
 * 实际上是new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName))
 */
public class FileOutputShortcut {
	static String file = "/home/waf/tmp/test";

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new StringReader(
				BufferedInputFile.read("/home/waf/tmp/test")));
		// 缩写
		PrintWriter out = new PrintWriter(file);
		int lineCount = 1;
		String s;
		while ((s = in.readLine()) != null) {
			out.println(lineCount++ + ": " + s);
		}
		out.close();
		in.close();
		System.out.println(BufferedInputFile.read(file));
	}
}