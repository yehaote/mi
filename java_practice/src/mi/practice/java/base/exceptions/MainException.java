package mi.practice.java.base.exceptions;

import java.io.FileInputStream;
import java.io.IOException;

public class MainException {
	//抛出所有的异常
	public static void main(String[] args) throws IOException{
		//打开一个文件
//		FileInputStream file = new FileInputStream("/home/waf/tmp/test");
		FileInputStream file = new FileInputStream("/home/waf/tmp/test333");//这个文件不存在
		//做一些操作
		// .....
		//关闭文件
		file.close();
	}
}
