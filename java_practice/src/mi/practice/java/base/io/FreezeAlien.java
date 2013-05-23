package mi.practice.java.base.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 序列化对象很过程在一个包中
 */
public class FreezeAlien {
	public static void main(String[] args) 
			throws FileNotFoundException, IOException{
		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream("X.file"));
		Alien quellek = new Alien();
		out.writeObject(quellek);
		out.flush();
		out.close();
	}
}
