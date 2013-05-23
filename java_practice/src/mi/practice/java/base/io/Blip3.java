package mi.practice.java.base.io;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
/**
 * 使用Externalizable反序列化的时候, 使用默认无参构造函数实例化对象. 
 * 对象必须有默认构造函数, 而且访问权限必须为public.
 * 如果继承一个实现Externalizable的类, 要考虑在writeExternal和
 * readExternal中调用父类的方法.
 */
public class Blip3 implements Externalizable{
	private int i;
	private String s;// 没有初始化
	public Blip3(){
		System.out.println("Blip3 Constructor");
	}
	public Blip3(String x, int a){
		System.out.println("Blip3(String x, int a)");
		s = x;
		i = a;
	}
	@Override
	public String toString(){
		return s+i;
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("Blip3.writeExternal");
		//把s的值和i的值储存起来
		out.writeObject(s);
		out.writeInt(i);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		System.out.println("Blip3.readExternal");
		// 把储存起来的值写入到对象
		s = (String)in.readObject();
		i = in.readInt();
	}
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException{
		System.out.println("Constructir objects:");
		Blip3 b3 = new Blip3("A String ", 47);
		System.out.println(b3);
		ObjectOutputStream o = new ObjectOutputStream(
				new FileOutputStream("Blip3.out"));
		System.out.println("Saving object:");
		o.writeObject(b3);
		o.close();
		// 取回
		ObjectInputStream in = new ObjectInputStream(
				new FileInputStream("Blip3.out"));
		System.out.println("Recoving b3:");
		b3 = (Blip3)in.readObject();
		System.out.println(b3);
	}
}