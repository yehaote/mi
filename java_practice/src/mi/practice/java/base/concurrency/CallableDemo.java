package mi.practice.java.base.concurrency;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 如果想要有返回值, 可以用使用Callable接口
 * 然后使用ExecutorService.submit();提交
 * submit()方法返回一个Future对象
 * Future.isDone()去查看是否完成
 * Future.get()返回结果
 * 如果Future还没有isDone, get()方法将会阻塞直到得到返回结果
 * 还可以使用get()带TimeOut参数的方法
 * Executors.callable()可以输入一个runable返回一个callable
 */
class TaskWithResult implements Callable<String>{
	private int id;
	public TaskWithResult(int id){
		this.id=id;
	}
	@Override
	public String call(){
		return "result of TaskWithResult "+id;
	}
}
public class CallableDemo {
	public static void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool();
		ArrayList<Future<String>> result = new ArrayList<Future<String>>();
		for(int i=0;i<10;i++){
			result.add(exec.submit(new TaskWithResult(i)));
		}
		for(Future<String> fs:result){
			try{
				// get() block until completion
				System.out.println(fs.get());
			}catch(InterruptedException e){
				System.out.println(e);
				return;
			}catch(ExecutionException e){
				System.out.println(e);
			}finally{
				exec.shutdown();
			}
		}
	}
}
