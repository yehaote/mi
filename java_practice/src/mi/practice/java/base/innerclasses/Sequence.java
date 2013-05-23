package mi.practice.java.base.innerclasses;
/**
 * 内部类看上去像一种命名隐藏和代码组织的架构
 * c++里面的确实是如此,
 * 在java中还有一个很重要的特性, 
 * 内部类对外类享有所有访问权限
 * 内部类其实有一个对外部类的引用
 * (非static)内部类必须跟外部类结合起来初始化, 
 * 必选先初始化一个外部类, 再初始化内部类
 * 不然会编译出错
 * Ex4
 */
interface Selector{
	boolean end();
	Object current();
	void next();
}

public class Sequence {
	private Object[] items;
	private int next = 0;
	public Sequence(int size){
		items = new Object[size];
	}
	public void add(Object x){
		if(next<items.length){
			items[next++]=x;
//			items[next]=x;
//			next++;
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
	public static void main(String[] args){
		Sequence sequence = new Sequence(10);
		for(int i=0;i<10;i++){
			sequence.add(Integer.toString(i));
		}
		Selector selector = sequence.selector();
//		Selector selector = new Sequence.SequenceSelector(); //不能直接初始化
		while(!selector.end()){
			System.out.print(selector.current()+" ");
			selector.next();
		}
	}
}
