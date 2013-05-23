package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.List;

interface Selector{
	boolean end();
	Object current();
	void next();
}
class Sequence {
	private List<Object> items;
	public Sequence(){
		items = new ArrayList<Object>();
	}
	public void add(Object x){
		items.add(x);
	}
	private class SequenceSelector implements Selector{
		private int i =0;
		public boolean end(){
			return i == items.size();
		}
		public Object current(){
			return items.get(i);
		}
		public void next(){
			if(i<items.size()){
				i++;
			}
		}
	}
	public Selector selector(){
		return new SequenceSelector();
	}
	
}
public class Ex3{
	public static void main(String[] args){
		Sequence sequence = new Sequence();
		for(int i=0;i<10;i++){
			sequence.add(Integer.toString(i));
		}
		Selector selector = sequence.selector();
		while(!selector.end()){
			System.out.print(selector.current()+" ");
			selector.next();
		}
	}
}
