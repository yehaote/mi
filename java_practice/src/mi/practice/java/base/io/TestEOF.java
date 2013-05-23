package mi.practice.java.base.io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * FileInputStream读取文件转换成流
 * BufferedInputStream为每一次读提供缓存
 * DataInputStream可以读取各种格式, 一个int, 一个byte等
 */
public class TestEOF {
	public static void main(String[] args) throws IOException {
		DataInputStream in = new DataInputStream(new BufferedInputStream(
				new FileInputStream("/home/waf/tmp/test")));
//		DataInputStream in = new DataInputStream(
//				new FileInputStream("/home/waf/tmp/test"));
		// 判断还有多少字节存在
		while (in.available() != 0) {
			System.out.println((char) in.readByte());
		}
		in.close();
	}
}
