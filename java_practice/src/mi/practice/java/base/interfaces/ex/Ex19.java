package mi.practice.java.base.interfaces.ex;

import java.util.Random;

interface Tossing{
	void toss();
}
interface TossingFactory{
	Tossing getTossing();
}
class CoinTossing implements Tossing{
	private Random rand = new Random();
	private final int NUM=2;
	public void toss(){
		System.out.println(rand.nextInt(NUM));
	}
}
class CoinTossingFactory implements TossingFactory{
	public Tossing getTossing(){
		return new CoinTossing();
	}
}
class DiceTossingFactory implements TossingFactory{
	public Tossing getTossing(){
		return new DiceTossing();
	}
}
class DiceTossing implements Tossing{
	private Random rand = new Random();
	private final int NUM=6;
	public void toss(){
		System.out.println(rand.nextInt(NUM)+1);
	}
} 

public class Ex19 {
	static void tossing(TossingFactory tf){
		Tossing tossing = tf.getTossing();
		tossing.toss();
	}
	public static void main(String[] args){
		tossing(new CoinTossingFactory());
		tossing(new DiceTossingFactory());
		
	}
}
