package mi.practice.java.base.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 序列化类似于是一个对象的快照.
 * 在一个输出流里面会尽可能的重用对象?
 */
@SuppressWarnings("serial")
class House implements Serializable {
}

@SuppressWarnings("serial")
class Animal implements Serializable {
	private String name;
	private House preferredHouse;

	Animal(String nm, House house) {
		name = nm;
		preferredHouse = house;
	}

	@Override
	public String toString() {
		return name + "[" + super.toString() + "], " + preferredHouse + "\n";
	}
}

public class MyWorld {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		House house = new House();
		List<Animal> animals = new ArrayList<Animal>();
		// 三个Animal共用一个house
		animals.add(new Animal("Bosco the dog", house));
		animals.add(new Animal("Ralph the hamster", house));
		animals.add(new Animal("Molly the cat", house));
		System.out.println("animals: " + animals);
		ByteArrayOutputStream buf1 = new ByteArrayOutputStream();
		ObjectOutputStream o1 = new ObjectOutputStream(buf1);
		o1.writeObject(animals);
		o1.writeObject(animals); // 写两次
		// 输出到另一个流
		ByteArrayOutputStream buf2 = new ByteArrayOutputStream();
		ObjectOutputStream o2 = new ObjectOutputStream(buf2);
		o2.writeObject(animals);
		// 取回
		ObjectInputStream in1 = new ObjectInputStream(new ByteArrayInputStream(
				buf1.toByteArray()));
		ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(
				buf2.toByteArray()));
		List animals1 = (List) in1.readObject(), animals2 = (List) in1
				.readObject(), animals3 = (List) in2.readObject();
		// 从序列化里读出来的对象跟写入的对象不是一个实体,
		// animals1 和 animals2 的house引用相同, 并且连animal的引用都相同
		// animals3 的三个animal引用相同的house
		System.out.println("animals1: " + animals1);
		System.out.println("animals2: " + animals2);
		System.out.println("animals3: " + animals3);
	}
}
