package mi.practice.java.base.interfaces;
import static mi.practice.java.base.util.Print.*;
/**
 * 接口中的成员因为是static的, 
 * 所以它们在加载的时候就会进行初始化
 * 接口的成员不是接口的一部分, 它们的值存在为接口开辟的静态存储区
 */
public class TestRandVals {
	public static void main(String[] args){
		print(RandVals.RANDOM_INT);
		print(RandVals.RANDOM_LONG);
		print(RandVals.RANDOM_FLOAT);
		print(RandVals.RANDOM_DOUBLE);
	}
}
