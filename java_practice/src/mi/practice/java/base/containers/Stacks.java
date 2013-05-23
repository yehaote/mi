package mi.practice.java.base.containers;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Stack现在可以用LinkedList代替,
 * 它的设计很糟糕.
 */
enum Month{
		JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
		JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
}

public class Stacks {
	public static void main(String[] args){
		Stack<String> stack = new Stack<String>();
		for(Month m : Month.values()){
			stack.push(m.toString());
		}
		System.out.println("stack = "+stack);
		stack.addElement("The last line");
		System.out.println("element 5 = "+stack.elementAt(5));
		System.out.println("popping elements:");
		while(stack.empty()){
			System.out.print(stack.pop()+" ");
		}
		
		//使用LinkedList代替
		LinkedList<String> lstack =  new LinkedList<String>();
		for(Month m : Month.values()){
			lstack.addFirst(m.toString());
		}
		System.out.println("lstack = "+lstack);
		while(!lstack.isEmpty()){
			System.out.print(lstack.removeFirst()+" ");
		}
	}
}
