package mi.practice.java.base.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import static mi.practice.java.base.util.Print.*;

public class ViewBuffer {
	public static void main(String[] args){
		ByteBuffer bb = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 0, 0, 0, 'a'});
		bb.rewind();
		printnb("Byte Buffer ");
		while(bb.hasRemaining()){
			printnb(bb.position()+ " -> "+bb.get()+", ");
		}
		print();
		
		// 2 bytes
		CharBuffer cb = ((ByteBuffer)bb.rewind()).asCharBuffer();
		printnb("Char Buffer ");
		while(cb.hasRemaining()){
			printnb(cb.position()+ " -> "+cb.get()+", ");
		}
		print();
		// 4 bytes
		FloatBuffer fb = ((ByteBuffer)bb.rewind()).asFloatBuffer();
		printnb("Float Buffer ");
		while(fb.hasRemaining()){
			printnb(fb.position()+ " -> "+fb.get()+", ");
		}
		print();
		// 4 bytes
		IntBuffer ib = ((ByteBuffer)bb.rewind()).asIntBuffer();
		printnb("int Buffer ");
		while(ib.hasRemaining()){
			printnb(ib.position()+ " -> "+ib.get()+", ");
		}
		print();
		// 8 bytes
		LongBuffer lb = ((ByteBuffer)bb.rewind()).asLongBuffer();
		printnb("Long Buffer ");
		while(lb.hasRemaining()){
			printnb(lb.position()+ " -> "+lb.get()+", ");
		}
		print();
		// 8 bytes
		DoubleBuffer db = ((ByteBuffer)bb.rewind()).asDoubleBuffer();
		printnb("Double Buffer ");
		while(db.hasRemaining()){
			printnb(db.position()+ " -> "+db.get()+", ");
		}
		print();
		// 2 bytes
		ShortBuffer sb = ((ByteBuffer)bb.rewind()).asShortBuffer();
		printnb("Short Buffer ");
		while(sb.hasRemaining()){
			printnb(sb.position()+ " -> "+sb.get()+", ");
		}
		print();
	}
}
