package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Ex7Item {
	static long count=0;
	private long id =count++;
	@Override
	public String toString(){
		return getClass().getSimpleName()+" "+id;
	}
}

public class Ex7 {
	static void display(List<Ex7Item> items){
		for(Ex7Item item :items){
			System.out.println(item);
		}
		System.out.println();
	}
	public static void main(String[] args) {
		Ex7Item[] itemArray = new Ex7Item[] { new Ex7Item(), new Ex7Item(),
				new Ex7Item(), new Ex7Item(), new Ex7Item(), new Ex7Item(),
				new Ex7Item() };
		List<Ex7Item> items = new ArrayList<Ex7Item>();
		display(items);
		Collections.addAll(items, itemArray);
		display(items);
		List<Ex7Item> sub=items.subList(1, 4);
		display(sub);
//		items.removeAll(sub); //不能直接删除
		List<Ex7Item> copy=new ArrayList<Ex7Item>(items);
		copy.removeAll(sub);
		display(copy);
	}
}
