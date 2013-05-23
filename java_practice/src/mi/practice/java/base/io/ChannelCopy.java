package mi.practice.java.base.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 还可以使用ByteBuffer.allocateDirect()来实例化ByteBuffer,
 * ? 直接产生一个操作系统级的ByteBuffer?
 */
public class ChannelCopy {
	private static final int BSIZE = 1024;
	public static void main(String[] args) throws IOException{
		if(args.length != 2){
			System.out.println("arguments: sourcefile destfile");
			System.exit(1);
		}
		FileChannel in = new FileInputStream(args[0]).getChannel(),
					out = new FileOutputStream(args[1]).getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
		while(in.read(buffer) != -1){
			// 转输入为输出
			buffer.flip();
			out.write(buffer);
			buffer.clear();// 记得一定要清理
		}
	}
}
