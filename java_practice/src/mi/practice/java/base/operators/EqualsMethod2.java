package mi.practice.java.base.operators;
/**
 * 默认的Object.equals()方法是对两个对象的引用
 * 进行比较, 如果没有override这个方法的, 很多情况
 * 下都不能得到你想要的结果
 */

class Value{
	int i;
}

public class EqualsMethod2 {
	public static void main(String[] args){
		Value v1 = new Value();
		Value v2 = new Value();
		v1.i=v2.i=100;
		System.out.println(v1.equals(v2));
	}
}
