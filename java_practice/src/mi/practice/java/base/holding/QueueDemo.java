package mi.practice.java.base.holding;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
/**
 * Queue是经典的FIFO容器
 * Queue在并发中尤其重要, 
 * LinkedList实现了Queue
 * offer()往队列里增加元素, 增加不成功返回false
 * peek(), element()返回第一个元素并不从队列中删除他们
 * 如果队列中没有元素了, peek()返回null, element()抛出异常
 * poll(), remove()返回第一个元素, 并从队列里删除这个元素 
 * 如果队列中没有元素了, poll()返回null, remove()抛出异常
 * Queue接口继承了Collection接口
 */
public class QueueDemo {
	public static void printQ(Queue<? extends Object> queue){
		while(queue.peek() !=null){
			System.out.print(queue.remove()+" ");
		}
		System.out.println();
	}
	public static void main(String[] args){
		Queue<Integer> queue = new LinkedList<Integer>();
		Random rand = new Random(47);
		for(int i=0;i<10;i++){
			queue.offer(rand.nextInt(i+10));
		}
		printQ(queue);
		Queue<Character> qc = new LinkedList<Character>();
		for(char c : "Brontosaurus".toCharArray()){
			qc.offer(c);
		}
		printQ(qc);
	}
}
