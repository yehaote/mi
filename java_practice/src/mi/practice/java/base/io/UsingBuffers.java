package mi.practice.java.base.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
/**
 * 跟Channel交互的是ByteBuffer,
 * 其他的CharBuffer, LongBuffer之类只是对ByteBuffer进行了封装.
 * mark()把当前的position信息记录下来,
 * reset()把当前position信息改为mark的值.
 * rewind()把position的值置为0, 它还会把mark的值位置-1, 注意!
 */
public class UsingBuffers {
	private static void symmetricScramble(CharBuffer buffer){
		while(buffer.hasRemaining()){
			// 记录当前位置信息
			buffer.mark();
			char c1 = buffer.get();
			char c2 = buffer.get();
			// 跳回到记录位置的信息
			buffer.reset();
			buffer.put(c2).put(c1);
		}
	}
	
	public static void main(String[] args){
		char[] data = "UsingBuffers".toCharArray();
		ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
		CharBuffer cb = bb.asCharBuffer();
		cb.put(data);
		System.out.println(cb.rewind());
		symmetricScramble(cb);
		System.out.println(cb.rewind());
		symmetricScramble(cb);
		System.out.println(cb.rewind());
	}
}
