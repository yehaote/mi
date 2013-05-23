package mi.practice.java.base.io;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
/**
 * 当把一个ByteBuffer包装成一个IntBuffer以后,
 * 其实IntBuffer背后还是ByteBuffer提供数据服务.
 * 所有的IntBuffer或者ByteBuffer进行更改的动作都会一起起效.
 */
public class IntBufferDemo {
	private static final int BSIZE = 1024;
	public static void main(String[] args){
		ByteBuffer bb = ByteBuffer.allocate(BSIZE);
		IntBuffer ib = bb.asIntBuffer();
		ib.put(new int[]{11, 42, 47, 99, 143, 811, 1016});
		System.out.println(ib.get(3));
		ib.put(3, 1811);
		ib.flip();
		while(ib.hasRemaining()){
			int i = ib.get();
			System.out.println(i);
		}
	}
}
