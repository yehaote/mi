package mi.practice.java.base.containers;

import java.util.BitSet;
import java.util.Random;
/**
 * 一个BitSet默认是64位大小,
 * 扩容的时候是一倍倍往上翻的.
 * 64位是一个long的大小.
 * 相比数组来讲, 它的访问速度会慢很多.
 * 使用它的时候还要考虑到数据是否会浪费的问题.
 * 一个EnumSet相对于BitSet来说可能会更加好一点,
 * 因为BitSet只能是位, 而EnumSet上可以表达得更丰富一点,
 * 比如不容易出现程序上的错误.
 * 如果当执行时才能发现需要多少位来作为flag的话,
 * 这个时候EnumSet可能不会满足需求, 可以考虑BitSet.
 */
public class Bits {
	public static void printBitSet(BitSet b){
		System.out.println("bits: "+b);
		StringBuilder bbits = new StringBuilder();
		for(int j=0; j < b.size(); j++){
			bbits.append(b.get(j) ? "1" : "0");
		}
		System.out.println("bit pattern: "+bbits);
	}
	
	public static void main(String[] args){
		Random rand = new Random(47);
		
		// 一个byte8位
		byte bt = (byte)rand.nextInt();
		BitSet bb = new BitSet();
		for(int i=7; i>=0; i--){
			//?取出每一位, 从高位开始
			if(((1 << i) & bt) != 0){
				bb.set(i);
			}else{
				bb.clear(i);
			}
		}
		System.out.println("byte value: "+bt);
		printBitSet(bb);
		
		//一个short 16位
		short st =(short) rand.nextInt();
		BitSet bs = new BitSet();
		for(int i=15;i>=0;i--){
			if(((1<<i)&st) != 0){
				bs.set(i);
			}else{
				bs.clear(i);
			}
		}
		System.out.println("short value: "+st);
		printBitSet(bs);
		
		// 一个int32位
		int it = rand.nextInt();
		BitSet bi = new BitSet();
		for(int i=31; i>=0; i--){
			if(((1<<i)&it) != 0){
				bi.set(i);
			}else{
				bi.clear(i);
			}
		}
		System.out.println(it);
		printBitSet(bi);
		
		BitSet b127 = new BitSet();
		b127.set(127);
		printBitSet(b127);
		System.out.println("set bit 127 : "+b127);
		BitSet b255 = new BitSet();
		b255.set(255);
		printBitSet(b255);
		System.out.println("set bit 255 : "+b255);
		BitSet b1023 = new BitSet();
		b1023.set(1023);
		printBitSet(b1023);
		b1023.set(1024);
		printBitSet(b1023);
		System.out.println("set bit 1023 : "+b1023);
	}
}
