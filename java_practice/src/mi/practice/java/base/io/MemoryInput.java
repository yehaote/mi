package mi.practice.java.base.io;

import java.io.IOException;
import java.io.StringReader;
/**
 * 用StringReader读取每一个字符,
 * 读进来是int转成char显示.
 * 在Java中char是基于UTF-8的
 */
public class MemoryInput {
	public static void main(String[] args)throws IOException{
		StringReader in = new StringReader(BufferedInputFile.read("/home/waf/tmp/test"));
		int c;
		while((c=in.read())!=-1){
			System.out.println((char)c);
		}
	}
}
