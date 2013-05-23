package mi.practice.java.base.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
/**
 * 可以使用CharBuffer来对ByteBuffer进行包装,
 * 不过一定要注意String的编码问题.
 */
public class BufferToText {
	private static final String TEST_DATA="/home/waf/tmp/TIJ";
	
	public static final int BSIZE = 1024;
	
	public static void main(String[] args)throws IOException{
		FileChannel fc = new FileOutputStream(TEST_DATA+"data2.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text".getBytes()));
		fc.close();
		
		fc = new FileInputStream(TEST_DATA+"data2.txt").getChannel();
		ByteBuffer buff = ByteBuffer.allocate(BSIZE);
		fc.read(buff);
		buff.flip();
		System.out.println(buff.asCharBuffer()); // 输出乱码
		
		buff.rewind();
		String encoding = System.getProperty("file.encoding");
		System.out.println("Decode using"+encoding+": "+Charset.forName(encoding));
		
		fc = new FileOutputStream(TEST_DATA+"data2.txt").getChannel();
		// 需要指定编码才能正确
		fc.write(ByteBuffer.wrap("Some text".getBytes("UTF-16BE")));
		
		fc = new FileInputStream(TEST_DATA+"data2.txt").getChannel();
		buff = ByteBuffer.allocate(BSIZE);
		fc.read(buff);
		buff.flip();
		System.out.println(buff.asCharBuffer()); // 这样就不会乱码了

		// 使用CharBuffer写入
		fc = new FileOutputStream(TEST_DATA+"data2.txt").getChannel();
		buff = ByteBuffer.allocate(24); // More than need
		buff.asCharBuffer().put("Some text");
		fc.write(buff);
		fc.close();
		
		// 读取显示, 这样也能正常显示
		fc = new FileInputStream(TEST_DATA+"data2.txt").getChannel();
		buff.clear();
		fc.read(buff);
		buff.flip();
		System.out.println(buff.asCharBuffer());
	}
}
