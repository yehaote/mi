package mi.practice.java.base.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 在Java 1.4升级的时候提出了nio(new IO)的概念,
 * 即使你不直接使用nio的类, 其实java的一些io也都使用nio进行了优化.
 * nio主要提升的速度来自于它使用跟操作系统相类似的结构: channel 和 buffer.
 * channel可以理解为流的直接载体, 但是每次对Channel写入的时候是通过buffer完成的,
 * 而不是直接在Channel上进行写入.
 * 对于buffer Java中好像只有实现了一个叫ByteBuffer的.
 * 三个旧的IO被修改可以产生Channel,
 * 		FileIputStream, FileOutputStream, RandomAccessFile
 * Reader和Writer不能产生Channel, 但是Channels工具类可以为它们产生Channel
 */
public class GetChannel {
	private static final int BSIZE = 1024;
	public static void main(String[] args) throws IOException{
		// 写入一个文件
		FileChannel fc =new FileOutputStream("/home/waf/tmp/TIJ/data.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text ".getBytes()));
		fc.close();
		// 在一个文件最后添加
		fc = new RandomAccessFile("/home/waf/tmp/TIJ/data.txt", "rw").getChannel();
		fc.position(fc.size()); // 设置到最后的位置
		fc.write(ByteBuffer.wrap("Some more".getBytes()));
		fc.close();
		// 读取一个文件
		fc =  new FileInputStream("/home/waf/tmp/TIJ/data.txt").getChannel();
		ByteBuffer buff = ByteBuffer.allocate(BSIZE);
		fc.read(buff);
		// 转输入为输出
		buff.flip();
		while(buff.hasRemaining()){
			System.out.print((char)buff.get());
		}
	}
}
