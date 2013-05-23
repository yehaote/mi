package mi.practice.java.base.interfaces.ex.ex10;

import static mi.practice.java.base.util.Print.print;
import mi.practice.java.base.polymorphism.music.Note;

interface Instrument {
	int VALUE = 5;// 可以直接在实现类里调用

	void adjust();
}

interface Playable {
	void play(Note n);
}

class Wind implements Instrument, Playable {
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

class Percussion implements Instrument, Playable {
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

class Stringed implements Instrument, Playable {
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

public class Ex10 {
	static void tune(Playable p) {
		p.play(Note.MIDDLE_C);
	}

	static void tuneAll(Playable[] e) {
		for (Playable p : e) {
			tune(p);
		}
	}

	public static void main(String[] args) {
		Playable[] orchestra = { new Wind(), new Percussion(), new Stringed(),
				new Brass(), new WoodWind() };
		tuneAll(orchestra);
	}
}