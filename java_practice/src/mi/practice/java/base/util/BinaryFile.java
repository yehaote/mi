package mi.practice.java.base.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BinaryFile {
	public static byte[] read(File file)throws IOException{
		BufferedInputStream bf = new BufferedInputStream(new FileInputStream(file));
		try{
			// 获取有多少数据可读
			byte[] data = new byte[bf.available()];
			// 读取数据
			bf.read(data);
			return data;
		}finally{
			bf.close();
		}
	}
	
	public static byte[] read(String bfile)throws IOException{
		return read(new File(bfile).getAbsoluteFile());
	}
}
