package mi.practice.java.base.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
/**
 * 在一个数据包含多个byte的情况下, 
 * 写入到流中的时候需要去考虑编码顺序的问题, 
 * 也就是所谓的Endian.
 * Endian 一般有两种: BigEndian 和 LittleEndian
 * 	高位在前和低位在前
 * Java默认使用BigEndian, 
 * 在ByteBuffer中可以使用order()方法来改变默认的Endian.
 */
public class Endians {
	public static void main(String[] args){
		ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
		bb.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(bb.array()));
		bb.rewind();
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(bb.array()));
		bb.rewind();
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(bb.array()));
	}
}
