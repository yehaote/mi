package mi.practice.java.base.innerclasses.ex;

interface Ex22Selector {
	boolean end();

	Object current();

	void next();
}

class Ex22Sequence {
	private Object[] items;
	private int next = 0;

	public Ex22Sequence(int size) {
		items = new Object[size];
	}

	public void add(Object x) {
		if (next < items.length) {
			items[next++] = x;
			// items[next]=x;
			// next++;
		}
	}

	private class Ex22SequenceSelector implements Ex22Selector {
		private int i = 0;
		public boolean end() {
			return i == items.length;
		}
		public Object current() {
			return items[i];
		}
		public void next() {
			if (i < items.length) {
				i++;
			}
		}
		@SuppressWarnings("unused")
		public Ex22Sequence getSequence() {//返回引用的父类
			return Ex22Sequence.this;
		}
	}
	private class Ex22SequenceReverseSelector implements Ex22Selector {
		private int i = items.length-1;
		public boolean end() {
			return i < 0;
		}
		public Object current() {
			return items[i];
		}
		public void next() {
			if (i >= 0) {
				i--;
			}
		}
		@SuppressWarnings("unused")
		public Ex22Sequence getSequence() {//返回引用的父类
			return Ex22Sequence.this;
		}
	}
	
	public Ex22Selector selector() {
		return new Ex22SequenceSelector();
	}
	
	public Ex22Selector reversSelector(){
		return new Ex22SequenceReverseSelector();
	}

}

public class Ex22 {
	public static void main(String[] args) {
		Ex22Sequence sequence = new Ex22Sequence(10);
		for (int i = 0; i < 10; i++) {
			sequence.add(Integer.toString(i));
		}
		Ex22Selector selector = sequence.selector();
		// Selector selector = new Sequence.SequenceSelector(); //不能直接初始化
		while (!selector.end()) {
			System.out.print(selector.current() + " ");
			selector.next();
		}
		System.out.println();
		Ex22Selector revers = sequence.reversSelector();
		while (!revers.end()) {
			System.out.print(revers.current() + " ");
			revers.next();
		}
	}
}
