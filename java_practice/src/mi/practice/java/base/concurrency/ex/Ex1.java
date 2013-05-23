package mi.practice.java.base.concurrency.ex;
class HelloRun implements Runnable{
	private static int count=0;
	private final int id=count++;
	HelloRun(){
		System.out.println("#"+id+" startup");
	}
	@Override
	public void run(){
		int i=0;
		while(i<3){
			System.out.println("#"+id+" :"+i);
			Thread.yield();
			i++;
		}
		System.out.println("#"+id+" terminates");
		return;
	}
}
public class Ex1 {
	public static void main(String[] args){
		for(int i=0;i<5;i++){
			new Thread(new HelloRun()).start();
		}
	}
}
