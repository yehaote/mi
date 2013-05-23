package mi.practice.java.base.concurrency.ex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ex16Demo{
	Lock lock = new ReentrantLock(); 
	void f(){
		lock.lock();
		try{
			for(int i=0;i<5;i++){
				System.out.println("f()"+lock);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}finally{
			lock.unlock();
		}
	}
	void g(){
		lock.lock();
		try{
			for(int i=0;i<5;i++){
				System.out.println("g()"+lock);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}finally{
			lock.unlock();
		}
	}
	void h(){
		lock.lock();
		try{
			for(int i=0;i<5;i++){
				System.out.println("h()"+lock);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}finally{
			lock.unlock();
		}
	}
}

class Ex16Demo2{
	private Lock[] locks = {new ReentrantLock(),new ReentrantLock(),new ReentrantLock()};
	void f(){
		locks[0].lock();
		try{
			for(int i=0;i<5;i++){
				System.out.println("f2()"+locks[0]);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}finally{
			locks[0].unlock();
		}
	}
	void g(){
		locks[1].lock();
		try{
			for(int i=0;i<5;i++){
				System.out.println("g2()"+locks[1]);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}finally{
			locks[1].unlock();
		}
	}
	void h(){
		locks[2].lock();
		try{
			for(int i=0;i<5;i++){
				System.out.println("h2()"+locks[2]);
				Thread.yield();
				try{
					TimeUnit.SECONDS.sleep(1);
				}catch(InterruptedException e){
					System.out.println("Interrupted");
				}
			}
		}finally{
			locks[2].unlock();
		}
	}
}
public class Ex16 {
	public static void main(String[] args) throws InterruptedException{
		final Ex16Demo demo = new Ex16Demo();
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
		final Ex16Demo2 demo2 = new Ex16Demo2();
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
