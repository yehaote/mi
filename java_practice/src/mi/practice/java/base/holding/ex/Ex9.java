package mi.practice.java.base.holding.ex;

import java.util.Iterator;

class Ex9Sequence {
	private Object[] items;
	private int next = 0;
	public Ex9Sequence(int size){
		items = new Object[size];
	}
	public void add(Object x){
		if(next<items.length){
			items[next++]=x;
		}
	}
	
	private class SequenceIterator implements Iterator<Object>{
		private int i =0;
		@Override
		public boolean hasNext() {
			if(i<items.length){
				return true;
			}
			return false;
		}
		@Override
		public Object next() {
			return items[i++];
		}

		@Override
		public void remove() {
		}
	}
	public Iterator<Object> iterator(){
		return new SequenceIterator();
	}
	
}
public class Ex9 {
	public static void main(String[] args){
		Ex9Sequence sequence = new Ex9Sequence(10);
		for(int i=0;i<10;i++){
			sequence.add(Integer.toString(i));
		}
		Iterator<Object> it = sequence.iterator();
		while(it.hasNext()){
			System.out.print(it.next()+" ");
		}
	}
}
