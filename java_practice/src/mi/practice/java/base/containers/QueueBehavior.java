package mi.practice.java.base.containers;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import mi.practice.java.base.util.Generator;
/**
 * 除了PriorityQueue以外其他的输出顺序跟输入相同.
 */
public class QueueBehavior {
	private static int count =10;
	
	static <T> void test(Queue<T> queue,Generator<T> gen){
		for(int i=0;i<count;i++){
			queue.add(gen.next());
		}
		// 查看不输出
		while(queue.peek() != null){
			// 输出
			System.out.print(queue.remove() +" ");
		}
		System.out.println();
	}
	
	static class Gen implements Generator<String>{
		String[] s = ("one two three four five six seven eight nine ten").split(" ");
		int i;
		@Override
		public String next() {
			return s[i++];
		}
	}
	
	public static void main(String[] args){
		test(new LinkedList<String>(), new Gen());
		test(new PriorityQueue<String>(), new Gen());
		test(new ArrayBlockingQueue<String>(count), new Gen());
		test(new ConcurrentLinkedQueue<String>(), new Gen());
		test(new LinkedBlockingQueue<String>(), new Gen());
		test(new PriorityBlockingQueue<String>(), new Gen());
	}
}
