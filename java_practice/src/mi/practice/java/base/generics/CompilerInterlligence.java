package mi.practice.java.base.generics;

import java.util.Arrays;
import java.util.List;

public class CompilerInterlligence {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		List<? extends Fruit> fList = Arrays.asList(new Apple());
		Apple a = (Apple) fList.get(0); //没有警告
		// 参数是Object, 不会因为是<? extends Fruit>而指定为<? extends Fruit>
		fList.contains(new Apple());
		fList.indexOf(new Apple());
	}
}
