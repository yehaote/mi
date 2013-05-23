package mi.practice.java.base.io.ex;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * 缓存的io输出要比不缓存的快一些, 
 * 在这个例子里.
 */
public class Ex14 {
	public static void main(String[] args) throws IOException {
		String data = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
				+ "ddddddddddddddddddddddddddddddddddddddddddddddddddddddd"
				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
				+ "fffffffffffffffffffffffffffffffffffffffffffffffffffgfff"
				+ "gggggggggggggggggggggggggggggggggggggggggggggggggggghgg";
		long noBufferedBegin = System.nanoTime();
		FileOutputStream noBuffered = new FileOutputStream(
				"/home/waf/tmp/TIJ/nobuffedDataWrite");
		noBuffered.write(data.getBytes());
		long noBufferedEnd = System.nanoTime();
		System.out.println("nobuffered cost : "
				+ (noBufferedEnd - noBufferedBegin));

		long bufferedBegin = System.nanoTime();
		BufferedOutputStream buffered = new BufferedOutputStream(
				new FileOutputStream("/home/waf/tmp/TIJ/nobuffedDataWrite"));
		buffered.write(data.getBytes());
		long bufferedEnd = System.nanoTime();
		System.out.println("buffered cost :" + (bufferedEnd - bufferedBegin));
	}
}
