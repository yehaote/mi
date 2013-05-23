package mi.practice.java.base.io;

import java.io.IOException;
import java.io.RandomAccessFile;
/**
 * RandomAccessFile同时实现读和写,
 * 并支持多种模式打开一个文件.
 * 还可以使用seek()跳转到固定的位置.
 * 
 * 可以考虑使用nio的memory-mapped files来代替RandomAccessFile
 */
public class UsingRandomAccessFile {
	static String file = "/home/waf/tmp/TIJ/rtest.dat";
	static void display() throws IOException{
		RandomAccessFile rf = new RandomAccessFile(file, "r");
		for(int i=0; i<7; i++){
			System.out.println("Value "+i+": "+rf.readDouble());
		}
		System.out.println(rf.readUTF());
		rf.close();
	}
	
	public static void main(String[] args) throws IOException{
		RandomAccessFile rf = new RandomAccessFile(file, "rw");
		for(int i=0;i<7;i++){
			rf.writeDouble(i*1.414);
		}
		rf.writeUTF("The end of the file");
		rf.close();
		display();
		rf = new RandomAccessFile(file, "rw");
		// 一个double 8个byte, 跳到5好double位置,并改写它
		rf.seek(5*8);
		rf.writeDouble(47.0001);
		rf.close();
		display();
	}
}
