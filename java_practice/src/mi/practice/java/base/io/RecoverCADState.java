package mi.practice.java.base.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
/**
 * 看结果可以发现只有Line的color带了过来.
 * 其他的color都是默认初始化的color.
 * 如果你想要序列化静态变量, 必须手动序列化.
 * 
 * Java对象的序列化是不能跨语言的,
 * 这个时候考虑使用XML.
 */
public class RecoverCADState {
	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				"CADState.out"));
		List<Class<? extends Shape>> shapeTypes = (List<Class<? extends Shape>>) in
				.readObject();
		Line.deserializeStaticState(in);
		List<Shape> shapes = (List<Shape>) in.readObject();
		System.out.println(shapes);
	}
}
