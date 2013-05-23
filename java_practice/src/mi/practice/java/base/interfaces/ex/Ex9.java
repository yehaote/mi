package mi.practice.java.base.interfaces.ex;

import static mi.practice.java.base.util.Print.print;
import mi.practice.java.base.polymorphism.music.Note;

abstract class  Instrument {
	int VALUE = 5;//可以直接在实现类里调用
	abstract void play(Note n);
	abstract void adjust();
	@Override
	public String toString(){
		return "Instrument";
	}
}

class Wind extends Instrument {
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

class Percussion extends Instrument {
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

class Stringed extends Instrument {
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

class Ex9 {
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
