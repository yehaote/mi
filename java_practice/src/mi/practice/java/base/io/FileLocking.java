package mi.practice.java.base.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * 文件锁可以控制多对文件的调用, 
 * 无论是多个JVM还是系统的其他进程.
 * Java的文件锁是直接跟系统锁相同的.
 * 
 * 还可以对文件的一部分进行锁,
 * tryLock(long position, long size, boolean shared)
 * lock(long position, long size, boolean shared)
 * 无参的lock()锁定的是整个文件, 哪怕文件大小在增加, 增加的部分也会被锁
 */
public class FileLocking {
	public static void main(String[] args) throws IOException, InterruptedException{
		FileOutputStream fos = new FileOutputStream("file.txt");
		// 获取文件锁
		FileLock fl = fos.getChannel().tryLock();
		if(fl != null){
			System.out.println("Locked File");
			TimeUnit.MILLISECONDS.sleep(100);
			// 释放文件锁
			fl.release();
			System.out.println("Released Lock");
		}
		fos.close();
	}
}
