package mi.practice.java.base.concurrency.ex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class RadiationCounter{
	private AtomicInteger counter = new AtomicInteger(0);
	public int increment(){
		return counter.incrementAndGet();
	}
	public int get(){
		return counter.get();
	}
}
class Sensor implements Runnable{
	private static RadiationCounter totalCounter = new RadiationCounter();
	private static List<Sensor> sensors = new ArrayList<Sensor>();
	private static volatile boolean stop=false;
	public static int getTotalCount(){
		return totalCounter.get();
	}
	public static void sysoutLocalCounts(){
		for(Sensor sensor : sensors){
			System.out.println(sensor+" "+sensor.getLocalCount());
		}
	}
	public static void terminate(){
		stop=true;
	}
	private final int id;
	private AtomicInteger counter = new AtomicInteger(0);
	public Sensor(int i){
		this.id=i;
		sensors.add(this);
	}
	@Override
	public void run() {
		while(!stop){
			try {
				counter.incrementAndGet();
				totalCounter.increment();
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public String toString(){
		return "#"+id+" :";
	}
	public int getLocalCount(){
		return counter.get();
	}
}
public class Ex17 {
	public static void main(String[] args) throws InterruptedException{
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0;i<10;i++){
			exec.execute(new Sensor(i));
		}
		System.out.println("started");
		TimeUnit.SECONDS.sleep(3);
		System.out.println("stop");
		Sensor.terminate();
		exec.shutdown();
		if(!exec.awaitTermination(10, TimeUnit.MILLISECONDS)){
			System.out.println("Some task doesn't stoped");
		}
		System.out.println("total: "+Sensor.getTotalCount());
		Sensor.sysoutLocalCounts();
	}
}
