package mi.practice.java.base.reusing;

import static mi.practice.java.base.util.Print.*;
import java.util.Random;
/**
 * 基本类型final 表示赋值后不可改变
 * 对象final 表示引用不可更改, 对象本身还是可以改变的
 * 数组的final 同对象的final
 */
class Value {
	int i;

	public Value(int i) {
		this.i = i;
	}
}

@SuppressWarnings("unused")
public class FinalData {
	private static Random rand = new Random(47);
	private String id;

	public FinalData(String id) {
		this.id = id;
	}
	//valueOne, VALUE_TWO没有什么区别, 都是编译期常量
	private final int valueOne = 9;
	private static final int VALUE_TWO = 99;
	//VALUE_THREE也是编译期常量, 它具有全局访问权限, 并且只有一个
	public static final int VALUE_THREE = 999;
	//final 不代表在编译期我们就知道它的值
	//非编译期常量
	private final int i4 = rand.nextInt(20);//不同对象可能不同
	static final int INT_5 = rand.nextInt(20);//只有一个
	
	private Value v1 = new Value(11);
	private final Value v2 = new Value(22);
//	private final Value v2; //这样定义只能在构造函数,或者{}里初始化v2, 不能在实例化对象后初始化v2
	private static final Value VAL_3 = new Value(33);

	private final int[] a = { 1, 2, 3, 4, 5, 6 };

	public String toString() {
		return id + ": " + "i4 = " + i4 + ", INT_5 =" + INT_5;
	}

	public static void main(String[] args) {
		FinalData fd1 = new FinalData("fd1");
		
		// fd1.valueOne++;
		fd1.v2.i++;
		fd1.v1 = new Value(9);
		for (int i = 0; i < fd1.a.length; i++) {
			fd1.a[i]++;
		}
		// fd1.v2 = new Value(0);
		// fd1.VAL_3 = new Value(1);
		// fd1.a=new int[3];
		print(fd1);
		print("Creating new FinalData");
		FinalData fd2 = new FinalData("fd2");
		print(fd1);
		print(fd2);
	}
}
