package mi.practice.java.base.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 当有成员不想序列化的时候可以使用实现Externalizable接口来实现,
 * 或者对成员变量加上transient关键字来阻止序列化.
 * transient关键字只有在Serializable对象中使用.
 */
@SuppressWarnings("serial")
public class Logon implements Serializable{
	private Date date = new Date();
	private String username;
	private transient String password;
	public Logon(String name, String pwd){
		username = name;
		password = pwd;
	}
	@Override
	public String toString(){
		return "logon info: \n username: "+username+
				"\n date: "+date+"\n password: "+password;
	}
	public static void main(String[] args) 
			throws IOException, InterruptedException, ClassNotFoundException{
		Logon a = new Logon("Hulk", "myLittlePony");
		System.out.println("Logon a = "+a);
		ObjectOutputStream o = new ObjectOutputStream(
				new FileOutputStream("Logon.out"));
		o.writeObject(a);
		o.close();
		TimeUnit.SECONDS.sleep(1);
		ObjectInputStream in = new ObjectInputStream(
				new FileInputStream("Logon.out"));
		System.out.println("Recovering object at "+new Date());
		Logon b =(Logon)in.readObject();
		System.out.println("Logon b = "+b);
	}
}
