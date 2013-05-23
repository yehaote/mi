package mi.practice.java.base.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * 当文件读完的时候,readLine会返回null.
 * readLine读取的内容后面的\n会被消除掉,
 * 如果想展现的时候可以考虑加回去.
 * 一般来说close()可以放到finalize()里面去,
 * 可是因为java的finalize()是不可控制的.
 * 所以需要显示调用它.
 */
public class BufferedInputFile {
	public static String read(String fileName) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = in.readLine()) != null) {
			sb.append(s + '\n');
		}
		in.close();
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		System.out.println(read("/home/waf/tmp/test"));
	}
}
