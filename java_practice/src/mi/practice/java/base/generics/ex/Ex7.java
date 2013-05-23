package mi.practice.java.base.generics.ex;

import java.util.Iterator;

import mi.practice.java.base.generics.Fibonacci;

public class Ex7 implements Iterable<Integer>{
	private Fibonacci fibonacci;
	private int n=0;
	public Ex7(int count){
		n=count;
		fibonacci=new Fibonacci();
	}
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			@Override
			public Integer next() {
				n--;
				if(n>=0){
					return fibonacci.next();
				}
				return 0;
			}
			@Override
			public boolean hasNext() {
				return n>0;
			}
		};
	}
	
	public static void main(String[] args){
		Ex7 ex = new Ex7(18);
		for(int i:ex){
			System.out.print(i+" ");
		}
	}
}
