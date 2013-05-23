package mi.practice.java.base.generics;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class NeedCasting {
	@SuppressWarnings({ "unchecked", "unused" })
	public void f(String[] args)throws Exception{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(args[0]));
		// 因为不知道读取的是什么对象, 所以需要强转
		List<Widget> shapes=(List<Widget>) in.readObject();
	}
}
