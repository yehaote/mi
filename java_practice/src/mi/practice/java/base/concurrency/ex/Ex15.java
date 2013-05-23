package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.TimeUnit;

class Ex15Demo{
	private Object object = new Object();
	void f(){
		synchronized(object){
			for(int i=0;i<5;i++){
				System.out.println("f()"+object);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}
	}
	void g(){
		synchronized(object){
			for(int i=0;i<5;i++){
				System.out.println("g()"+object);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}
	}
	void h(){
		synchronized(object){
			for(int i=0;i<5;i++){
				System.out.println("h()"+object);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}
	}
}

class Ex15Demo2{
	private Object[] object = {new Object(),new Object(),new Object()};
	void f(){
		synchronized(object[0]){
			for(int i=0;i<5;i++){
				System.out.println("f2()"+object[0]);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}
	}
	void g(){
		synchronized(object[1]){
			for(int i=0;i<5;i++){
				System.out.println("g2()"+object[1]);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}
	}
	void h(){
		synchronized(object[2]){
			for(int i=0;i<5;i++){
				System.out.println("h2()"+object[2]);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}
	}
}
public class Ex15 {
	public static void main(String[] args) throws InterruptedException{
		final Ex15Demo demo = new Ex15Demo();
		new Thread(){
			@Override
			public void run(){
				demo.f();
			}
		}.start();
		new Thread(){
			@Override
			public void run(){
				demo.g();
			}
		}.start();
		new Thread(){
			@Override
			public void run(){
				demo.h();
			}
		}.start();
		TimeUnit.SECONDS.sleep(20);
		System.out.println("on different object");
		final Ex15Demo2 demo2 = new Ex15Demo2();
		new Thread(){
			@Override
			public void run(){
				demo2.f();
			}
		}.start();
		new Thread(){
			@Override
			public void run(){
				demo2.g();
			}
		}.start();
		new Thread(){
			@Override
			public void run(){
				demo2.h();
			}
		}.start();
	}
}
