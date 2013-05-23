package mi.practice.java.base.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
/**
 * 对大文件区域进行锁
 * 使用区域锁的时候, 可以同时多个线程对一个文件不同的部分进行读写
 * 不能在BufferStream上获取锁, 只能在Channel上.
 * 当Channel关闭或者JVM关闭的时候, 这个锁会被释放.
 * 不过也可以自己通过release()方法释放.
 */
public class LockingMappedFiles {
	static final int LENGTH = 0x8FFFFFF; // 128MB
	static FileChannel fc;
	public static void main(String[] args) throws IOException{
		fc = new RandomAccessFile("test.dat", "rw").getChannel();
		MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);
		for(int i=0; i<LENGTH; i++){
			out.put((byte)'x');
		}
		new LockAndModify(out, 0, 0+LENGTH/3);
		new LockAndModify(out, LENGTH/2, LENGTH/2 + LENGTH/4);
	}
	
	private static class LockAndModify extends Thread{
		private ByteBuffer buff;
		private int start, end;
		public LockAndModify(ByteBuffer mbb, int start, int end) {
			this.start = start;
			this.end = end;
			mbb.limit(end);
			mbb.position(start);
			// 切片
			buff = mbb.slice();
			start();
		}
		
		@Override
		public void run(){
			try{
				FileLock fl = fc.lock(start, end, false);
				System.out.println("Locked: "+start+" to "+end);
				// 进行修改
				while(buff.position() < (buff.limit() -1)){
					buff.put((byte)(buff.get()+1));
				}
				fl.release();
				System.out.println("Released: "+start+" to "+end);
			}catch(IOException e){
				throw new RuntimeException(e);
			}
		}
	}
}
