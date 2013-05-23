package mi.practice.java.base.innerclasses.ex;

class Word {
	private String str;

	Word(String str) {
		System.out.println("Word("+str+")");
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}
}

interface Selector {
	boolean end();

	Object current();

	void next();
}

class Sequence {
	private Object[] items;
	private int i = 0;

	public Sequence(int length) {
		items = new Object[length];
	}

	public void add(Object o) {
		if (i < items.length) {
			items[i++] = o;
		}
	}
	
	public Selector selector(){
		return new SequenceSelector();
	}

	class SequenceSelector implements Selector {
		private int i = 0;

		@Override
		public boolean end() {
			if (i == items.length) {
				return true;
			}
			return false;
		}

		@Override
		public Object current() {
			if (i < items.length) {
				return items[i];
			}
			return null;
		}

		@Override
		public void next() {
			if (i < items.length) {
				i++;
			}
		}

	}
}

public class Ex2 {
	public static void main(String[] args){
		Sequence sequence = new Sequence(10);
		for(int i=0;i<10;i++){
			sequence.add(new Word(Integer.toString(i)));
		}
		Selector selector = sequence.selector();
		while(!selector.end()){
			System.out.println(selector.current());
			selector.next();
		}
	}
}
