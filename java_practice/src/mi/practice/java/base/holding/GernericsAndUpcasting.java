package mi.practice.java.base.holding;

import java.util.ArrayList;
/**
 * 在泛型的时候, 向上转换同样适用
 * 往泛型里加入其父类不需要转换
 * 默认的toString()方法返回类名,加上实例的hash code
 */
class GrannySmith extends Apple{}
class Gala extends Apple{}
class Fuji extends Apple{}
class Braeburn extends Apple{}
public class GernericsAndUpcasting {
	public static void main(String[] args){
		ArrayList<Apple> apples = new ArrayList<Apple>();
		apples.add(new GrannySmith());
		apples.add(new Gala());
		apples.add(new Fuji());
		apples.add(new Braeburn());
		for(Apple c :apples){
			System.out.println(c);
		}
	}
}
