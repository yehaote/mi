package mi.practice.java.base.containers;

import java.util.WeakHashMap;
/**
 * WeakHashMap对里面的元素采用弱引用类型,
 * 在外面没有其它引用的情况下, 
 * 里面的元素可以被垃圾回收.
 * 
 */
class Element{
	private String ident;
	public Element(String id){
		ident=id;
	}
	@Override
	public String toString(){
		return ident;
	}
	@Override
	public int hashCode(){
		return ident.hashCode();
	}
	@Override
	public boolean equals(Object o){
		return o instanceof Element &&
				ident.equals(((Element)o).ident);
	}
	@Override
	protected void finalize(){
		System.out.println("Finalizing "+getClass().getSimpleName()+" "+ident);
	}
}

class Key extends Element{
	public Key(String id){
		super(id);
	}
}

class Value extends Element{
	public Value(String id){
		super(id);
	}
}

public class CanonicalMapping {
	public static void main(String[] args){
		int size =1000;
		if(args.length > 0){
			size= new Integer(args[0]);
		}
		Key[] keys = new Key[size];
		WeakHashMap<Key, Value> map = new WeakHashMap<Key, Value>();
		for(int i=0;i<size;i++){
			Key k = new Key(Integer.toString(i));
			Value v = new Value(Integer.toString(i));
			if(i % 3== 0){
				keys[i]=k;// 保存引用,让它不被回收
			}
			map.put(k, v);
		}
		System.gc();
	}
}
