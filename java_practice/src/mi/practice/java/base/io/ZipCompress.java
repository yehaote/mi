package mi.practice.java.base.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Zip跟GZip最大的区别是在于Zip可以很方便地压缩多个文件.
 * 经典的有两种检验和(checksum)Adler32(比较快)和CRC32(比较慢, 但是更加正确)
 * 注意流的包装顺序.
 * Java中的Zip不支持密码.
 * 读写Zip文件的时候注意使用putNextEntry()方法和getNextEntry()方法.
 * GZip和Zip不仅仅仅限于文件, 对流都适用.
 */
public class ZipCompress {
	public static void main(String[] args) throws IOException{
		/// 输出zip文件
		// 文件输出流
		FileOutputStream f = new FileOutputStream("test.zip");
		// 有检验和的输出流
		CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
		// zip压缩输出流
		ZipOutputStream zos = new ZipOutputStream(csum);
		// 带缓存的输出流
		BufferedOutputStream out = new BufferedOutputStream(zos); 
		zos.setComment("A test of java zipping");
		for(String arg : args){
			System.out.println("Writing file "+arg);
			BufferedReader in = new BufferedReader(new FileReader(arg));
			// 添加一个文件
			zos.putNextEntry(new ZipEntry(arg));
			int c;
			while((c = in.read() ) != -1){
				out.write(c);
			}
			in.close();
			// 输出缓存
			out.flush();
		}
		// 输出结束
		out.close();
		
		/// 读取zip文件
		// Checksum验证在文件关闭以后
		System.out.println("Checksum: "+csum.getChecksum().getValue());
		// 解压文件
		System.out.println("Reading file");
		// 文件流
		FileInputStream fi = new FileInputStream("test.zip");
		// 带检验和的流
		CheckedInputStream csumi = new CheckedInputStream(fi, new Adler32());
		// 压缩流
		ZipInputStream in2 = new ZipInputStream(csumi);
		BufferedInputStream bis = new BufferedInputStream(in2);
		ZipEntry ze;
		// 依次读取Zip里面的文件
		while((ze=in2.getNextEntry()) != null){
			System.out.println("Reading file "+ze);
			int x;
			// 注意这里也是直接从流里读取的
			while((x=bis.read()) != -1){
				System.out.write(x);
			}
		}
		if(args.length == 1){
			System.out.println("Checksum: "+csumi.getChecksum().getValue());
		}
		bis.close();
		
		// 打开Zip文件的另一种形式
		ZipFile zf = new ZipFile("test.zip");
		Enumeration<? extends ZipEntry> e =zf.entries();
		while(e.hasMoreElements()){
			ZipEntry ze2 = e.nextElement();
			System.out.println("File: "+ze2);
		}
	}
}
