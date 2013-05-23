package mi.practice.java.base.containers;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
/**
 * Java.lang.ref包提供了一些包让gc更加灵活.
 * 当你想使用一个实体又想让gc可以对它进行回收的时候,
 * 你可以使用Reference的实现类对它进行包装.
 * SoftReference->WeakReference->PhantomReference,
 * 一个比一个弱.
 * WeakReference在进行gc的时候会调用finalize(),
 * 而SoftReference不会?
 */
class VeryBig{
	private static final int SIZE = 10000;
	@SuppressWarnings("unused")
	private long[] la = new long[SIZE];
	private String ident;
	public VeryBig(String id){
		ident = id;
	}
	
	@Override
	public String toString(){
		return ident;
	}
	
	@Override
	protected void finalize(){
		System.out.println("Finalizing "+ident);
	}
}

public class References {
	private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<VeryBig>();
	public static void checkQueue(){
		Reference<? extends VeryBig> inq =rq.poll();
		if(inq !=null){
			System.out.println("In queue: "+inq.get());
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		int size = 10;
		if(args.length > 0){
			size = new Integer(args[0]);
		}
		LinkedList<SoftReference<VeryBig>> sa = new LinkedList<SoftReference<VeryBig>>();
		for(int i =0; i<size; i++){
			sa.add(new SoftReference<VeryBig>(new VeryBig("Soft "+i),rq));
			System.out.println("Just created: "+sa.getLast());
			checkQueue();
		}
		LinkedList<WeakReference<VeryBig>> wa = new LinkedList<WeakReference<VeryBig>>();
		for(int i =0; i<size; i++){
			wa.add(new WeakReference<VeryBig>(new VeryBig("Weak "+i),rq));
			System.out.println("Just created: "+wa.getLast());
			checkQueue();
		}
		SoftReference<VeryBig> s = new SoftReference<VeryBig>(new VeryBig("Soft"));
		WeakReference<VeryBig> w = new WeakReference<VeryBig>(new VeryBig("Weak"));
		System.gc();
		LinkedList<PhantomReference<VeryBig>> pa =new LinkedList<PhantomReference<VeryBig>>();
		for(int i=0; i<size;i++){
			// PhantomReference只有一个构造函数, 必须提供ReferenceQueue才能初始化
			pa.add(new PhantomReference<VeryBig>(new VeryBig("Phantom "+i), rq));
			System.out.println("Just created: "+pa.getLast());
			checkQueue();
		}
	}
}
