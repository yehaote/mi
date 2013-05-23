package mi.practice.java.base.io;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Java的内置压缩IO是基于流的而不是字符
 * GZIP接口很简单, 用来压缩单个流特别好.
 */
public class GZIPcompress {
	public static void main(String[] args) throws IOException{
		if(args.length == 0){
			System.out.println(
					"Usage: \nGZIPcompress file\n" +
					"\tUses GZIP compression to compress " +
					"the file to test.gz");
			System.exit(1);
		}
		// 读取文件
		BufferedReader in  = new BufferedReader(new FileReader(args[0]));
		BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("test.gz")));
		System.out.println("Writing file");
		int c;
		// GZIP格式输出
		while((c=in.read()) != -1){
			out.write(c);
		}
		in.close();
		out.close();
		System.out.println("Reading compressed file");
		// 读取压缩完的文件
		BufferedReader in2 = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("test.gz"))));
		String s;
		while((s=in2.readLine()) != null){
			System.out.println(s);
		}
	}
}
