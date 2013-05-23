package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mi.practice.java.base.concurrency.LiftOff;

public class Ex3 {
	static void runService(ExecutorService es){
		for(int i=0;i<5;i++){
			es.execute(new LiftOff());
		}
	}
	public static void main(String[] args){
		ExecutorService se = Executors.newCachedThreadPool();
		runService(se);
		System.out.println();
		se=Executors.newFixedThreadPool(3);
		runService(se);
		System.out.println();
		se=Executors.newSingleThreadExecutor();
		runService(se);
	}
}
