package mi.practice.java.base.generics;

public class CaptureConversion {
	static <T> void f1(Holder<T> holder){
		T t = holder.get();
		System.out.println(t.getClass().getSimpleName());
	}
	
	static void f2(Holder<?> holder){
		f1(holder);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args){
		Holder raw = new Holder<Integer>();
		f1(raw); // Warning unchecked
		f2(raw);
		Holder rawBasic = new Holder();
		rawBasic.set(new Object()); // Warning unchecked
		f2(rawBasic);
		Holder<?> wildcarded = new Holder<Double>(1.0);
		f2(wildcarded);
	}
	
}
