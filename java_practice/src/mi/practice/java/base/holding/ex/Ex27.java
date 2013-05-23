package mi.practice.java.base.holding.ex;

import java.util.LinkedList;
import java.util.Queue;

class Command{
	private String operator;
	Command(String operator){
		this.operator=operator;
	}
	void operation(){
		System.out.println(getClass().getSimpleName()+" "+operator);
	}
}
class CommandQueue{
	static Queue<Command> getCommandQueue(){
		Command cmd1 = new Command("open");
		Command cmd2 = new Command("check");
		Command cmd3 = new Command("run");
		Command cmd4 = new Command("stop");
		Command cmd5 = new Command("close");
		Queue<Command> queue = new LinkedList<Command>();
		queue.offer(cmd1);
		queue.offer(cmd2);
		queue.offer(cmd3);
		queue.offer(cmd4);
		queue.offer(cmd5);
		return queue;
	}
}
public class Ex27 {
	public static void main(String[] args){
		Queue<Command> queue= CommandQueue.getCommandQueue();
		while(queue.peek()!=null){
//			Command cmd = queue.remove();
			Command cmd = queue.poll();
			cmd.operation();
		}
	}
}
