package mi.practice.java.base.generics;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
/**
 * 不能说List<Widget>.class,
 * 只能使用到List.class
 * @author waf
 */
public class ClassCasting {
	@SuppressWarnings({ "unchecked", "unused" })
	public void f(String[] args) throws Exception{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(args[0]));
//		List<Widget> lw1 = List<Widget>.class.cast(in.readObject());
		List<Widget> lw2 = List.class.cast(in.readObject());
	}
}
