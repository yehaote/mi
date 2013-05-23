package mi.practice.java.base.holding;

import java.util.ArrayList;
/**
 * 在java中如果没有指定范型会出警告
 */
class Apple{
	private static long counter=0L;
	private final long id = counter++;
	public long id(){
		return id;
	}
}
class Orange{}

public class ApplesAndOrangesWithoutGenerics {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args){
		ArrayList apples = new ArrayList();
		for(int i=0;i<3;i++){
			apples.add(new Apple());
		}
		apples.add(new Orange());
		for(int i=0;i<apples.size();i++){
			System.out.println(((Apple)apples.get(i)).id());
		}
		
	}
}
