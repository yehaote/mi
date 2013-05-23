package mi.practice.java.base.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
/**
 * IO测试里面,
 * 对于Mapped file的初始化对于Stream来说开销会更大一点.
 * 不过对于大文件的读写上来看, Mapped性能会好很多.
 */
public class MappedIO {
	private static int numOfInts = 4000000; // 4,000,000
	private static int numOfUbuffInts = 200000; // 200,000
//	private static int numOfInts = 40000;
//	private static int numOfUbuffInts = 2000;
	private abstract static class Tester{
		private String name;
		public Tester(String name){
			this.name=name;
		}
		public void runTest(){
			System.out.print(name +": ");
			try{
				long start = System.nanoTime();
				test();
				double duration = System.nanoTime() -start;
				System.out.format("%.2f\n", duration/1.0e9);
			}catch(IOException e){
				throw new RuntimeException(e);
			}
		}
		
		public abstract void test() throws IOException;
	}
	
	private static Tester[] tests = {
		new Tester("Stream Write") {			
			@Override
			public void test() throws IOException {
				// Stream形式写入numOfInts个int
				DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("temp.tmp")));
				for(int i=0; i< numOfInts; i++){
					dos.writeInt(i);
				}
				dos.close();
			}
		},
		new Tester("Mapped Write") {			
			@Override
			public void test() throws IOException {
				// 为什么要使用RandomAccessFile
				/**
				 * 所有的输出使用file mapping 必须使用RandomAccessFile
				 */
				// Mapped file形式写入numOfInts个int, 当int在10k级以上的时候比Stream要快很多
				FileChannel fc = new RandomAccessFile("temp.tmp", "rw").getChannel();
				IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
				for(int i=0; i<numOfInts; i++){
					ib.put(i);
				}
				fc.close();
			}
		},
		new Tester("Stream Read") {			
			@Override
			public void test() throws IOException {
				// Stream形式读取文件
				DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("temp.tmp")));
				for(int i=0; i< numOfInts; i++){
					dis.readInt();
				}
				dis.close();
			}
		},
		new Tester("Mapped Read") {			
			@Override
			public void test() throws IOException {
				// Mapped形式读取文件
				FileChannel fc = new FileInputStream("temp.tmp").getChannel();
				IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
				for(int i=0; i<numOfInts; i++){
					ib.get();
				}
				fc.close();
			}
		},
		new Tester("Stream Read/Write") {			
			@Override
			public void test() throws IOException {
				RandomAccessFile raf = new RandomAccessFile(new File("temp.tmp"),"rw");
				raf.writeInt(1);
				for(int i=0; i<numOfUbuffInts; i++){
					// 跳转到文件最后, 边读边写
					// seek()跳转到文件绝对位置
					raf.seek(raf.length() -4);
					int element = raf.readInt();
					raf.writeInt(element);
				}
				raf.close();
			}
		},
		new Tester("Mapped Read/Write"){
			@Override
			public void test() throws IOException{
				FileChannel fc = new RandomAccessFile(new File("temp.tmp"),"rw").getChannel();
				IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
				ib.put(0);
				for(int i= 1; i<numOfUbuffInts; i++){
					int element = ib.get(i-1);
					ib.put(element);
				}
				fc.close();
			}
		}
	};
	
	public static void main(String[] args){
		for(Tester test : tests){
			test.runTest();
		}
	}
}
