package mi.practice.java.base.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * 控制序列化的过程除了实现Externalizable和
 * 使用Transient关键字以后还有一种途径.
 * 在Serializable的类中添加writeObject()和readObject()方法
 * 注意方法的参数, private(访问限定符)等信息.
 * 如果有这两个方法, 默认的序列化过程会被取消使用这两个方法代替,
 * 还可以在这两个方法中调用defaultRead(Write)Object()方法来执行原有的
 * 序列化过程.
 * 这两个方法可以单独使用.
 */
@SuppressWarnings("serial")
public class SerialCtl implements Serializable {
	private String a;
	private transient String b;

	public SerialCtl(String aa, String bb) {
		a = "Not Transient: "+aa;
		b = "Transient: "+bb;
	}

	@Override
	public String toString() {
		return a + "\n" + b;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		// 可以执行原有的方法, 这个时候b因为是transient所以不输出
		out.defaultWriteObject();
		// 输出b
		out.writeObject(b);
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		// 执行原有反序列化方法
		in.defaultReadObject();
		// 还写了个b进去也要读出来
		b = (String) in.readObject();
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		SerialCtl sc = new SerialCtl("Test1", "Test2");
		System.out.println("Before:\n" + sc);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(buf);
		out.writeObject(sc);
		// 取回
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(
				buf.toByteArray()));
		SerialCtl sc2 = (SerialCtl) in.readObject();
		System.out.println("After:\n" + sc2);
	}
}
