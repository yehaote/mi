package mi.practice.java.base.concurrency.ex;

import java.util.Timer;
import java.util.TimerTask;

public class Ex14 {
	public static void main(String[] args){
		for(int i=0;i<100;i++){
			final int id=i;
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println(this+"completed"+" id:"+id);
				}
			}, i*1000);
		}
	}
}
