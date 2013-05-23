package mi.practice.java.base.util;

import java.util.LinkedList;
/**
 * Deque是一个双端队列,
 * 可以从头和尾放入取出元素.
 */
public class Deque<T> {
	private LinkedList<T> deque = new LinkedList<T>();
	public void addFirst(T e){
		deque.addFirst(e);
	}
	public void addLast(T e){
		deque.addLast(e);
	}
	public T getFirst(){
		return deque.getFirst();
	}
	public T getLast(){
		return deque.getLast();
	}
	public T removeFirst(){
		return deque.removeFirst();
	}
	public T removeLast(){
		return deque.removeLast();
	}
	public int size(){
		return deque.size();
	}
	@Override
	public String toString(){
		return deque.toString();
	}
	
}
