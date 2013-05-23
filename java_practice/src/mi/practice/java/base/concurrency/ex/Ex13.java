package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SerialNumberGenerator {
	private static volatile int serialNumber=0;
	public static synchronized int nextSerialNumber(){
		return serialNumber++;
	}
}
class CircularSet{
	private int[] array;
	private int len;
	private int index;
	public CircularSet(int size){
		array=new int[size];
		len=size;
		for(int i=0;i<len;i++){
			array[i]=-1;//因为默认的0或产生冲突
		}
	}
	public synchronized void add(int i){
		array[index]=i;
		index = ++index%len;	//环形是这么来的
	}
	public synchronized boolean contains(int val){
		for(int i=0;i<len;i++){
			if(array[i]==val){
				return true;
			}
		}
		return false;
	}
}
public class Ex13 {
	private static final int SIZE=10;
	private static CircularSet serials=new CircularSet(1000);
	private static ExecutorService exec = Executors.newCachedThreadPool();
	static class SerialChecker implements Runnable{
		@Override
		public void run(){
			while(true){
				int serial = SerialNumberGenerator.nextSerialNumber();//会出错的是这里
				if(serials.contains(serial)){
					System.out.println("Duplicate: "+serial);
					System.exit(0);
				}
				serials.add(serial);
			}
		}
	}
	public static void main(String[] args) throws NumberFormatException, InterruptedException{
		for(int i=0;i<SIZE;i++){
			exec.execute(new SerialChecker());
		}
		if(args.length > 0){
			TimeUnit.SECONDS.sleep(new Integer(args[0]));
			System.out.println("No duplicates detected");
			System.exit(0);
		}
	}
}
