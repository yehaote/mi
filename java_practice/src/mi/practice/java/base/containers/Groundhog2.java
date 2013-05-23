package mi.practice.java.base.containers;
/**
 * 在使用hash的时候除了要覆盖hashCode方法以外, 
 * 还要记得覆盖equals()方法.
 * hashCode不是严格判定对象相等的,
 * equals()定义了对象相等的判定条件.
 * 当Hash值一样的时候, 对象不一定相等,
 * 但是对象相等的时候hashcode一定相等.
 */
public class Groundhog2 extends Groundhog{
	public Groundhog2(int n){
		super(n);
	}
	@Override
	public int hashCode(){
		return number;
	}
	@Override
	public boolean equals(Object o){
		return o instanceof Groundhog2 && (number == ((Groundhog2)o).number);
	}
}
