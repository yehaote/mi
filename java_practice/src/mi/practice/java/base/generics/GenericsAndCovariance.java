package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsAndCovariance {
	public static void main(String[] args){
		List<? extends Fruit> fList = new ArrayList<Apple>();
		//编译出错, 不能增加任何类型的object
//		fList.add(new Apple());
//		fList.add(new Fruit());
//		fList.add(new Object());
		fList.add(null);//可以增加null, 但是没什么用
		// 我们知道它返回至少是Fruit
		@SuppressWarnings("unused")
		Fruit f = fList.get(0);
	}
}
