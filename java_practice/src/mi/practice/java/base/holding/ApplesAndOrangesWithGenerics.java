package mi.practice.java.base.holding;

import java.util.ArrayList;
/**
 * 泛型可以有效地控制一个ArrayList中的元素的类型
 * 当在制定的泛型里加入不同类型的数据时候, 会出现编译期异常
 */
public class ApplesAndOrangesWithGenerics {
	public static void main(String[] args){
		ArrayList<Apple> apples = new ArrayList<Apple>();
		for(int i=0;i<3;i++){
			apples.add(new Apple());
		}
//		apples.add(new Orange());//使用泛型以后不能往apple的arrayList中加入orange
		for(int i=0;i<apples.size();i++){
			System.out.println(apples.get(i).id());
		}
		//使用foreach
		for(Apple apple : apples){
			System.out.println(apple.id());
		}
	}
}
