package mi.practice.java.base.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * DataIn(Out)putStream
 * 读取写入一些格式,
 * 本来stream一般只能写byte.
 * 这个可以读写int, double等基本类型,
 * 还是使用writeUTF(), readUTF()对"String"进行操作.
 * Java保证数据在不同的平台上都有可以支持读写, 
 * 对数据的跨平台性一直是java很有价值的地方.
 * 
 * WriteUTF, ReadUTF对字符串读写utf-8编码,
 * UTF-8对ASCII字符采用一个byte, 
 * 对非ASCII字符采用2-3个byte.
 * 字符串的长度保存在字符的前两个byte.
 */
public class StoringAndRecoveringData {
	public static void main(String[] args) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream("/home/waf/tmp/TIJ/data.txt")));
		out.writeDouble(3.14159);
		out.writeUTF("That was pi");
		out.writeDouble(1.41413);
		out.writeUTF("Square root of 2");
		out.close();
		DataInputStream in = new DataInputStream(new BufferedInputStream(
				new FileInputStream("/home/waf/tmp/TIJ/data.txt")));
		System.out.println(in.readDouble());
		System.out.println(in.readUTF());
		System.out.println(in.readDouble());
		System.out.println(in.readUTF());
	}
}
