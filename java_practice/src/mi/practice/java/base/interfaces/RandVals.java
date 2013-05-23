package mi.practice.java.base.interfaces;

import java.util.Random;
/**
 * 在接口中不能空白定义成员,
 * 但是它可以用动态赋值的方法进行初始化
 */
public interface RandVals {
	Random RAND = new Random(47);
	int RANDOM_INT = RAND.nextInt(10);
	long RANDOM_LONG = RAND.nextLong() * 10;
	float RANDOM_FLOAT = RAND.nextFloat() * 10;
	double RANDOM_DOUBLE = RAND.nextDouble() * 10;
//	char CHAR;
//	Character c;
}
