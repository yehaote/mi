package mi.practice.java.base.containers;
/**
 * 在实现hashCode方法的时候有几点要注意的地方:
 * 1. 无论hashCode在这个对象什么时候调用, 都应返回一样的值.
 * 2. hash值不要基于对象中可变的数值, 因为数值改变后hash值就不一样.
 * 3. 原有的是基于对象的地址得到的hash值, 在设计的时候需要变得不太一样.
 * 		不要使用每个对象的标识符, 而采用其他的算法. 
 * 
 * String的hash值基于它的字符串的值计算得到.
 * 可以很清楚地得到我们想要的结果, 相同内容的字符串是相等的内容.
 */
public class StringHashCode {
	public static void main(String[] args){
		String[] hellos="Hello Hello".split(" ");
		System.out.println(hellos[0].hashCode());
		System.out.println(hellos[1].hashCode());
	}
}
