package mi.practice.java.base.interfaces.music4;

import static mi.practice.java.base.util.Print.*;
import mi.practice.java.base.polymorphism.music.Note;
/**
 * 包含抽象方法的类一定是抽象类
 * 抽象方法没有函数体(没有{}), 抽象方法定义结尾必须要分号
 * 抽象类可以没有抽象方法, 这样做的目的是不能直接初始化这个抽象类
 * 在继承类中一定要实现抽象方法, 不然的话继承过来的类必须也是抽象类
 */
abstract class Instrument {
	@SuppressWarnings("unused")
	private int i;

	public abstract void play(Note n);

	public String what() {
		return "Instrument";
	}

	public abstract void adjust();
}

class Wind extends Instrument {
	@Override
	public void play(Note n) {
		print("Wind.play() " + n);
	}

	@Override
	public String what() {
		return "Wind";
	}

	@Override
	public void adjust() {

	}
}

class Percussion extends Instrument {
	@Override
	public void play(Note n) {
		print("Percussion.play() " + n);
	}

	@Override
	public String what() {
		return "Percussion";
	}

	@Override
	public void adjust() {

	}
}

class Stringed extends Instrument {
	@Override
	public void play(Note n) {
		print("Stringed.play() " + n);
	}

	@Override
	public String what() {
		return "Stringed";
	}

	@Override
	public void adjust() {

	}
}

class Brass extends Wind {
	@Override
	public void play(Note n) {
		print("Brass.play() " + n);
	}

	@Override
	public void adjust() {
		print("Brass.adjust()");
	}
}

class WoodWind extends Wind {
	@Override
	public void play(Note n) {
		print("Woodwind.play() " + n);
	}

	@Override
	public String what() {
		return "WoodWind";
	}
}

public class Music4 {
	static void tune(Instrument i) {
		i.play(Note.MIDDLE_C);
	}

	static void tuneAll(Instrument[] e) {
		for (Instrument i : e) {
			tune(i);
		}
	}

	public static void main(String[] args) {
		Instrument[] orchestra = { new Wind(), new Percussion(),
				new Stringed(), new Brass(), new WoodWind() };
		tuneAll(orchestra);
	}
}
