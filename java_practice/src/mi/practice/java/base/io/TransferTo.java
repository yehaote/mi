package mi.practice.java.base.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
/**
 * 可以使用transferTo(), transferFrom()来对两个channel进行拷贝
 */
public class TransferTo {
	public static void main(String[] args) throws IOException{
		if(args.length != 2){
			System.out.println("arguments: sourcefile destfile");
			System.exit(1);
		}
		FileChannel in = new FileInputStream(args[0]).getChannel(),
					out = new FileOutputStream(args[1]).getChannel();
		in.transferTo(0, in.size(), out);
		//或者
//		out.transferFrom(in, 0, in.size());
	}
}
