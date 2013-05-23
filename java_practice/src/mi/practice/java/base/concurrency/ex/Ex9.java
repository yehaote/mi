package mi.practice.java.base.concurrency.ex;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Ex9 implements Runnable{
	private int countDown=5;
	@SuppressWarnings("unused")
	private volatile double d;//No optimization
	@Override
	public String toString(){
		return Thread.currentThread()+" : "+countDown;
	}
	@Override
	public void run(){
		while(true){
			for(int i=0;i<100000;i++){
				d+=(Math.PI+Math.E)/(double)i;
				if(i%1000 ==0){
					Thread.yield();
				}
			}
			System.out.println(this);
			if(countDown-- == 0){
				return;
			}
		}
	}
	public static void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool(new RandomPriorityThreadFactory());
		for(int i=0;i<10;i++){
			exec.execute(new Ex9());
		}
		exec.shutdown();
	}
}

class RandomPriorityThreadFactory implements ThreadFactory{
	private Random rand = new Random();

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		switch(rand.nextInt(3)){
		case 0:t.setPriority(Thread.MAX_PRIORITY);break;
		case 1:t.setPriority(Thread.NORM_PRIORITY);break;
		case 2:t.setPriority(Thread.MIN_PRIORITY);break;
		}
		return t;
	}
	
}
