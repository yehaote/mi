package mi.practice.java.base.util;

import java.util.LinkedList;
/**
 * 栈是后进新出(LIFO)的一种数据结构,
 * 可以使用LinkedList来实现, 
 * 不过不见得是最好的办法
 * peek()返回第一个元素,但是不删除它
 * pop()返回并删除地一个元素
 * 虽然存在java.util.Stack
 * 但是更加推荐这个实现
 */
public class Stack<T> {
	private LinkedList<T> storage = new LinkedList<T>();
	//所有的元素都加在最前面
	public void push(T v){
		storage.addFirst(v);
	}
	public T peek(){
		return storage.peek();
	}
	public T pop(){
		return storage.removeFirst();
	}
	public boolean empty(){
		return storage.isEmpty();
	}
	@Override
	public String toString(){
		return storage.toString();
	}
}
