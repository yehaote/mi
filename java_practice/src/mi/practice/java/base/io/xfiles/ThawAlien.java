package mi.practice.java.base.io.xfiles;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
/**
 * 在不同的包中读取序列化的对象,
 * 如果要想对序列化的对象进行调用的时候, 
 * 必须包含对应的class文件, 
 * 不然的话会报ClassNotFoundException.
 * JVM中必须包含对应的.class文件.
 */
public class ThawAlien {
	public static void main(String[] args)
		throws Exception{
		// 前提
		Class<?> c=Class.forName("mi.practice.java.base.io.Alien");
		System.out.println(c);
		// 读取对象
		ObjectInputStream in = new ObjectInputStream(
				new FileInputStream("X.file"));
		Object mystery = in.readObject();
		System.out.println(mystery.getClass());
	}
}
