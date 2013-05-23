package mi.practice.java.base.holding.ex;

import java.util.PriorityQueue;
import java.util.Random;

public class Ex28 {
	public static void main(String[] args){
		PriorityQueue<Double> doubles = new PriorityQueue<Double>();
		Random rand = new Random(47);
		for(int i=0;i<10;i++){
			doubles.offer(rand.nextDouble());
		}
		System.out.println(doubles);
		System.out.println(doubles.size());
		while(doubles.peek()!=null){
			System.out.println(doubles.poll());
		}
//		for(int i=0;i<doubles.size();i++){ // 不能这么写, 因为doubles.size()是在变化的, poll了以后size会变小
	}
}
