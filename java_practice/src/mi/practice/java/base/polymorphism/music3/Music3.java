package mi.practice.java.base.polymorphism.music3;

import static mi.practice.java.base.util.Print.*;

import java.util.Random;

import mi.practice.java.base.polymorphism.music.Note;
/**
 * 多态的精髓就在于
 * 把需要改变的大雪跟不变的分开
 * Ex6, Ex7, Ex8
 */
class Instrument {
	void play(Note n) {
		print("Instrument.play() " + n);
	}

	String what() {
		return "Instument";
	}

	void adjust() {
		print("Adjusting Instrument");
	}
	@Override
	public String toString(){
		return what();
	}
}

class Wind extends Instrument {
	@Override
	void play(Note n) {
		print("Wind.play() " + n);
	}

	@Override
	String what() {
		return "Wind";
	}

	@Override
	void adjust() {
		print("Adjusting Wind");
	}
}

class Percussion extends Instrument {
	@Override
	void play(Note n) {
		print("Percussion.play() " + n);
	}

	@Override
	String what() {
		return "Percussion";
	}

	@Override
	void adjust() {
		print("Adjusting Percussion");
	}
}

class Stringed extends Instrument {
	@Override
	void play(Note n) {
		print("Stringed.play() " + n);
	}

	@Override
	String what() {
		return "Stringed";
	}

	@Override
	void adjust() {
		print("Adjusting Stringed");
	}
}

class Brass extends Wind {
	@Override
	void play(Note n) {
		print("Brass.play() " + n);
	}

	@Override
	void adjust() {
		print("Adjusting Brass");
	}
}

class WoodWind extends Wind {
	@Override
	void play(Note n) {
		print("WoodWind.play() " + n);
	}

	@Override
	void adjust() {
		print("Adjusting WoodWind");
	}
}

class Piano extends Instrument{
	void play(Note n) {
		print("Piano.play() " + n);
	}

	String what() {
		return "Piano";
	}

	void adjust() {
		print("Adjusting Piano");
	}
}

class MusicGenerator{
	private Random rand = new Random(47);
	public Instrument next(){
		switch(rand.nextInt(6)){
			default:
			case 0: return new Wind();
			case 1: return new Percussion();
			case 2: return new Stringed();
			case 3: return new Brass();
			case 4: return new WoodWind();
			case 5: return new Piano();
		}
	}
}

public class Music3 {
	public static void tune(Instrument i) {
		i.play(Note.MIDDLE_C);
	}

	public static void tuneAll(Instrument[] e) {
		for (Instrument i : e) {
			tune(i);
		}
	}
	public static void seeAll(Instrument[] e) {
		for (Instrument i : e) {
			System.out.println(i);
		}
	}
	public static void main(String[] args) {
//		Instrument[] orchestra = { new Wind(), new Percussion(),
//				new Stringed(), new Brass(), new WoodWind() , new Piano()};
		MusicGenerator gen = new MusicGenerator();
		Instrument[] orchestra = new Instrument[20];
		for(int i=0;i<orchestra.length;i++){
			orchestra[i]=gen.next();
		}
		tuneAll(orchestra);
		seeAll(orchestra);
	}
}
