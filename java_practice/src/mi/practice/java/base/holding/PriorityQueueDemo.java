package mi.practice.java.base.holding;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
/**
 * 优先级队列
 * 在队列中取出的是优先级最高的
 * 优先级队列允许重复, 元素按默认顺序排列, 越小优先级越高
 * 如果不是使用java基本类型的话, 要实现类的排序或者实现comparator
 */
public class PriorityQueueDemo {
	public static void main(String[] args){
		PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
		Random rand = new Random(47);
		for(int i=0;i<10;i++){
			priorityQueue.offer(rand.nextInt(i+10));
		}
		QueueDemo.printQ(priorityQueue);
		
		List<Integer> ints = Arrays.asList(25,22,20,18,14,9,3,1,1,2,3,9,14,18,21,23,25);
		priorityQueue=new PriorityQueue<Integer>(ints);
		QueueDemo.printQ(priorityQueue);
		priorityQueue=new PriorityQueue<Integer>(ints.size(), Collections.reverseOrder());
		priorityQueue.addAll(ints);
		QueueDemo.printQ(priorityQueue);
		
		String fact = "EDUCATION SHOULD ESCHEW OBFUSCATION";
		List<String> strings = Arrays.asList(fact.split(""));
		PriorityQueue<String> stringPQ= new PriorityQueue<String>(strings);
		QueueDemo.printQ(stringPQ);
		stringPQ = new PriorityQueue<String>(strings.size(),Collections.reverseOrder());
		stringPQ.addAll(strings);
		QueueDemo.printQ(stringPQ);
		
		Set<Character> charSet = new HashSet<Character>();
		for(char c:fact.toCharArray()){
			charSet.add(c);
		}
		PriorityQueue<Character> charcterPQ = new PriorityQueue<Character>(charSet);
		QueueDemo.printQ(charcterPQ);
	}
}
