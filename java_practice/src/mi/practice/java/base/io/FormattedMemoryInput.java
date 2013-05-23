package mi.practice.java.base.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
/**
 * 在Java中内嵌的io里的stream类基本都是基于byte的,
 * 而Reader基本都是基于char的.
 */
public class FormattedMemoryInput {
	public static void main(String[] args) throws IOException {
		try {
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(
					BufferedInputFile.read("/home/waf/tmp/test").getBytes()));
			while (true) {
				System.out.println((char) in.readByte());
			}
		} catch (EOFException e) {
			System.err.println("End of stream");
		}
	}
}
