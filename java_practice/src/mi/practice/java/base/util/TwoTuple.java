package mi.practice.java.base.util;
/**
 * 还可以指定多个类型
 * @author waf
 * @param <A>
 * @param <B>
 */
public class TwoTuple<A,B> {
	public final A first;
	public final B second;
	public TwoTuple(A a, B b){
		first=a;
		second=b;
	}
	@Override
	public String toString(){
		return "("+first+", "+second+")";
	}
}
