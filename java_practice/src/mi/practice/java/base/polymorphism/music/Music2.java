package mi.practice.java.base.polymorphism.music;
/**
 * 多态的基本立足点是:
 * 不用为没一个类指定一个方法, 可以为一群类指定一个方法
 * 使用这群类的父类或者接口来作为参数
 * 让整个过程更加具备管理性
 */
class Stringed extends Instrument{
	@Override
	public void play(Note n){
		System.out.println("Stringed.play() "+n);
	}
}

class Brass extends Instrument{
	@Override
	public void play(Note n){
		System.out.println("Brass.play() "+n);
	}
}

public class Music2 {
	public static void tune(Wind i){
		i.play(Note.MIDDLE_C);
	}
	
	public static void tune(Stringed i){
		i.play(Note.MIDDLE_C);
	}
	
	public static void tune(Brass i){
		i.play(Note.MIDDLE_C);
	}
	
	public static void main(String[] args){
		Wind flute = new Wind();
		Stringed violin = new Stringed();
		Brass frenchHorn = new Brass();
		tune(flute);
		tune(violin);
		tune(frenchHorn);
	}
}
