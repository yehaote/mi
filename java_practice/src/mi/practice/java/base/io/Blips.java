package mi.practice.java.base.io;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
/**
 * 如果你想控制类的序列化过程,
 * 可以继承Externalizable接口来完成.
 * 比如类的有一部分数据不想序列化, 
 * 或者是说不想序列化类的成员.
 * 
 * 当实现Externalizable反序列化的时候, 
 * 类的构造函数会被调用, 这跟Serializable是不一样的.
 * 当反序列的时候先调用构造函数再调用readExternal()方法
 */
class Blip1 implements Externalizable{
	// public访问权限构造函数
	public Blip1(){
		System.out.println("Blip1 Constructor");
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("Blip1.writeExternal");
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		System.out.println("Blip1.readExternal");
	}
}

class Blip2 implements Externalizable{
	// 默认访问权限构造函数, 包访问控制权限
	Blip2(){
		System.out.println("Blip2 Constructor");
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("Blip2.writeExternal");
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		System.out.println("Blip2.readExternal");
	}
}
public class Blips {
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		System.out.println("Constructing objects:");
		Blip1 b1 = new Blip1();
		Blip2 b2 = new Blip2();
		ObjectOutputStream o = new ObjectOutputStream(
				new FileOutputStream("Blips.out"));
		System.out.println("Saving objects:");
		o.writeObject(b1);
		o.writeObject(b2);
		o.close();
		// 取回
		ObjectInputStream in = new ObjectInputStream(
				new FileInputStream("Blips.out"));
		System.out.println("Recovering b1:");
		b1 = (Blip1)in.readObject();
		System.out.println("Recovering b2:");
		// 会报错, 没有合法的构造函数
//		b2 = (Blip2)in.readObject();
		in.close();
	}
}
 