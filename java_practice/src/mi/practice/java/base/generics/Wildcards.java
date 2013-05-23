package mi.practice.java.base.generics;

public class Wildcards {
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	static void rawArgs(Holder holder, Object arg){
		holder.set(arg); // Warning
		holder.set(new Wildcards());// Same warning
//		T t = holder.get(); // T is just a define type
		Object obj=holder.get();//OK, but type information has been lost
	}
	
	@SuppressWarnings("unused")
	static void unboundedArg(Holder<?> holder, Object arg){
//		holder.set(arg); // Error
//		holder.set(new Wildcards()); // Error, the same
//		T t = holder.get(); // T is just a define type
		Object obj=holder.get();//OK, but type information has been lost
	}
	
	static <T> T exact1(Holder<T> holder){
		T t= holder.get();
		return t;
	}
	
	static <T> T exact2(Holder<T> holder, T arg){
		holder.set(arg);
		return holder.get();
	}
	
	static <T> T wildSubtype(Holder<? extends T> holder, T arg){
//		holder.set(arg);// put a smaller one in the big position, no~~~
		return holder.get(); // big outer change to smaller, no problem
	}
	
	static <T> void wildSupertype(Holder<? super T> holder, T arg){
		holder.set(arg);//put a big one in the small position, ok!
//		T t = holder.get(); // get a small, change to a big, warining
		@SuppressWarnings("unused")
		Object obj = holder.get(); // Object is the smallest, so fine
	}
	
	@SuppressWarnings({"rawtypes", "unused", "unchecked"})
	public static void main(String[] args){
		Holder raw = new Holder<Long>();
		raw=new Holder();
		Holder<Long> qualified = new Holder<Long>();
		Holder<?> unbounded = new Holder<Long>();
		Holder<? extends Long> bounded = new Holder<Long>();
		
		Long lng =1l;
		
		rawArgs(raw, lng);
		rawArgs(qualified, lng);
		rawArgs(unbounded, lng);
		rawArgs(bounded, lng);
		
		unboundedArg(raw, lng);
		unboundedArg(qualified, lng);
		unboundedArg(unbounded, lng);
		unboundedArg(bounded, lng);
		
//		@SuppressWarnings("unchecked")
		Object r1 = exact1(raw); // unchecked
		Long r2 = exact1(qualified);
		Object r3 = exact1(unbounded);
		Long r4 = exact1(bounded);
		
//		@SuppressWarnings("unchecked")
		Long r5 = exact2(raw, lng);// unchecked
		Long r6 = exact2(qualified, lng);
//		Long r7 = exact2(unbounded, lng); // Error
//		Long r8 = exact2(bounded, lng); // Error
		
//		@SuppressWarnings("unchecked")
		Long r9 = wildSubtype(raw, lng);// unchecked
		Long r10 = wildSubtype(qualified, lng);
//		Long r11 = wildSubtype(unbounded, lng); // Error
		Long r12 = wildSubtype(bounded, lng);
		
		wildSupertype(raw, lng); // unchecked
		wildSupertype(qualified, lng);
//		wildSupertype(unbounded, lng); // Error
//		wildSupertype(bounded, lng); //  Error
	}
}
