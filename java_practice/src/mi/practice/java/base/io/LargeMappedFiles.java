package mi.practice.java.base.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射文件允许你修改一个很大的文件而不需要把它全部读到内存中来.
 * 使用Channel的map()方法可以返回一个MappedByteBuffer,
 * MappedByteBuffer继承自ByteBuffer.
 * map还可以制定position和size, 
 * 就是说你可以映射大文件中很小的一段.
 */
public class LargeMappedFiles {
	static int length = 0x8FFFFFF; // 128 MB
//	static int length = 0x5FFFFFFF;

	public static void main(String[] args) throws IOException {
		MappedByteBuffer out = new RandomAccessFile("test.dat", "rw")
				.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, length);
		for(int i=0; i<length; i++){
			out.put((byte)'x');
		}
		System.out.println("Finished writing");
		for(int i=length/2; i<length/2+6; i++){
			System.out.print((char)out.get(i));
		}
	}
}
