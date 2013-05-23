package mi.practice.java.base.interfaces.music5;

import mi.practice.java.base.polymorphism.music.Note;
import static mi.practice.java.base.util.Print.*;

/**
 * 在接口里所有的方法都是没有实现的
 * 只有声明, 接口更像类间的一个协议(protocol)
 * 接口里仍然可以包含成员, 但是它的成员默认是static和final的
 * 你可以在接口里显示声明方法为public,不过这些方法默认就是public的
 */
interface Instrument {
	int VALUE = 5;//可以直接在实现类里调用

	void play(Note n);

	void adjust();
}

class Wind implements Instrument {
	@Override
	public void play(Note n) {
		print(this + ".play() " + n);
	}

	@Override
	public String toString() {
		return "Wind";
	}

	@Override
	public void adjust() {
		print(this + ".adjust()");
	}
}

class Percussion implements Instrument {
	@Override
	public void play(Note n) {
		print(this + ".play() " + n);
	}

	@Override
	public String toString() {
		return "Percussion";
	}

	@Override
	public void adjust() {
		print(this + ".adjust()");
	}
}

class Stringed implements Instrument {
	@Override
	public void play(Note n) {
		print(this + ".play() " + n);
	}

	@Override
	public String toString() {
		return "Stringed";
	}

	@Override
	public void adjust() {
		print(this + ".adjust()");
	}
}

class Brass extends Wind {
	@Override
	public String toString() {
		return "Brass";
	}
}

class WoodWind extends Wind {
	@Override
	public String toString() {
		return "WoodWind";
	}
}

public class Music5 {
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
