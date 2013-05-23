package mi.practice.java.base.holding.ex;

import java.util.PriorityQueue;

class Ex29Item{
}
public class Ex29 {
	public static void main(String[] args){
		PriorityQueue<Ex29Item> queue = new PriorityQueue<Ex29Item>();
		for(int i=0;i<10;i++){
			System.out.println(i);
			queue.offer(new Ex29Item()); // 不能加入多余一个对象
		}
		System.out.println(queue);
	}
}
