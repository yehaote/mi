package mi.practice.java.base.io;

import java.nio.ByteBuffer;
/**
 * 往ByteBuffer里写基本类型的时候, 
 * 最简单的方式是把ByteBuffer包装成各种基本类型的Buffer并写入数据.
 * 只有short类型需要注意下, 如果是显示的short需要进行强转.
 */
public class GetData {
	private static final int BSIZE = 1024;
	public static void main(String[] args){
		ByteBuffer bb = ByteBuffer.allocate(BSIZE);
		// 默认全分配0
		int i=0;
		while(i++ <bb.limit()){
			if(bb.get() != 0){
				System.out.println("nonzero");
			}
		}
		System.out.println("i = "+i);
		
		// 指针定位到开头
		bb.rewind();
		bb.asCharBuffer().put("Howdy!");
		char c;
		while((c=bb.getChar())!=0){
			System.out.print(c+" ");
		}
		System.out.println();
		
		bb.rewind();
		bb.asShortBuffer().put((short)471142);
		System.out.println(bb.getShort());
		
		bb.rewind();
		bb.asIntBuffer().put(99471142);
		System.out.println(bb.getInt());
		
		bb.rewind();
		bb.asLongBuffer().put(99471142);
		System.out.println(bb.getLong());
		
		bb.rewind();
		bb.asFloatBuffer().put(99471142);
		System.out.println(bb.getFloat());
		
		bb.rewind();
		bb.asDoubleBuffer().put(99471142);
		System.out.println(bb.getDouble());
	}
}
