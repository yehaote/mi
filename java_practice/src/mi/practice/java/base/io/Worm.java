package mi.practice.java.base.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * 序列化就是把对象的信息保存下来, 即使程序不再运行.
 * 在Java中序列化对象需要实现Serializable接口.
 * 对应Stream有ObjectOutputStream和ObjectInputStream,
 * 可以通过writeObject()和readObject()读写对象.
 * 在使用的时候需要显示转换.
 * 一个对象序列化的时候它的成员对象也会被一起储存起来. 
 * 
 * 输出的对象是一个瞬时的状态, 类似一个快照.
 */
@SuppressWarnings("serial")
class Data implements Serializable{
//	private static final long serialVersionUID = -1712729332877399778L;
	private int n;
	public Data(int n){
		this.n=n;
	}
	@Override
	public String toString(){
		return Integer.toString(n);
	}
}

@SuppressWarnings("serial")
public class Worm implements Serializable{
	private static Random rand = new Random(47);
	// 包含多个数据
	private Data[] d ={
		new Data(rand.nextInt(10)),
		new Data(rand.nextInt(10)),
		new Data(rand.nextInt(10))
	};
	// 指向相同类型的一个引用, 类似一条链结构
	private Worm next;
	private char c;
	public Worm(int i, char x){
		System.out.println("Worm constructor: "+i);
		c=x;
		// 构建i个Worm, 成链
		if(--i > 0){
			next = new Worm(i, (char)(x+1));
		}
	}
	public Worm(){
		System.out.println("Default constructor");
	}
	@Override
	public String toString(){
		StringBuffer result = new StringBuffer(":");
		result.append(c);
		result.append("(");
		for(Data dat : d){
			result.append(dat);
		}
		result.append(")");
		if(next != null){
			result.append(next);
		};
		return result.toString();
	}
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException{
		Worm w = new Worm(6, 'a');
		System.out.println("w = "+w);
		// 输出到流
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("worm.out"));
		out.writeObject("Worm storage\n");
		out.writeObject(w);
		out.close();
		// 改变w
		w.c='z';
		System.out.println("w = "+w);
		// 从流读入
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("worm.out"));
		String s = (String)in.readObject();
		Worm w2 = (Worm)in.readObject();
		System.out.println(s+"w2 = "+w2);
		// 可以使用ByteArrayIn(Out)putStream输出输入对象.
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out2 = new ObjectOutputStream(bout);
		out2.writeObject("Worm storage\n");
		out2.writeObject(w);
		out2.flush();
		ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
		s = (String)in2.readObject();
		Worm w3 = (Worm)in2.readObject();
		System.out.println(s+"w3 = "+w3);
	}
}