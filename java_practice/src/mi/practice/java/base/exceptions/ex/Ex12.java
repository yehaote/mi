package mi.practice.java.base.exceptions.ex;

interface Selector{
	boolean end();
	Object current();
	void next();
}

class Sequence {
	private Object[] items;
	private int next = 0;
	public Sequence(int size){
		items = new Object[size];
	}
	public void add(Object x){
		if(next<items.length){
			items[next++]=x;
		}else{
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	private class SequenceSelector implements Selector{
		private int i =0;
		public boolean end(){
			return i == items.length;
		}
		public Object current(){
			return items[i];
		}
		public void next(){
			if(i<items.length){
				i++;
			}
		}
		@SuppressWarnings("unused")
		public Sequence getSequence(){
			return Sequence.this;
		}
	}
	public Selector selector(){
		return new SequenceSelector();
	}
}
public class Ex12 {
	public static void main(String[] args){
		Sequence sequence = new Sequence(10);
		for(int i=0;i<10;i++){
			sequence.add(Integer.toString(i));
		}
		Selector selector = sequence.selector();
		while(!selector.end()){
			System.out.print(selector.current()+" ");
			selector.next();
		}
		sequence.add(11);
	}	
}
