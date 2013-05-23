package mi.practice.java.base.interfaces.ex;
/**
 * Ex14, Ex15
 */
interface WalkRun {
	void walk();

	void run();
}

interface ReadWrite {
	void read();

	void write();
}

interface EatDrink {
	void eat();

	void drink();
}

interface PeopleCan extends WalkRun, ReadWrite, EatDrink {
	String name();
}

class Human {
}

class People extends Human implements PeopleCan {
	public void walk() {
	}

	public void run() {
	}

	public void read() {
	}

	public void write() {
	}

	public void eat() {
	}

	public void drink() {
	}

	public String name() {
		return getClass().getSimpleName();
	}
}

abstract class Teacher{
	abstract void teach();
}

class MathTeacher extends Teacher implements PeopleCan{
	public void walk() {
	}

	public void run() {
	}

	public void read() {
	}

	public void write() {
	}

	public void eat() {
	}

	public void drink() {
	}

	public String name() {
		return getClass().getSimpleName();
	}

	void teach() {
	}
}

public class Ex14 {
	static void eatdrink(EatDrink ed) {
		ed.eat();
		ed.drink();
	}

	static void writeread(ReadWrite rw) {
		rw.read();
		rw.write();
	}

	static void walkrun(WalkRun wr) {
		wr.walk();
		wr.run();
	}

	static void peoplecan(PeopleCan pc) {
		pc.name();
	}
	
	static void teach(Teacher teacher){
		teacher.teach();
	}

	public static void main(String[] args) {
		People p = new People();
		eatdrink(p);
		walkrun(p);
		writeread(p);
		peoplecan(p);
		MathTeacher mt = new MathTeacher();
		eatdrink(mt);
		walkrun(mt);
		writeread(mt);
		peoplecan(mt);
		teach(mt);
	}
}
