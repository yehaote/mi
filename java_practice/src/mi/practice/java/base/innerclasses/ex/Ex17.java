package mi.practice.java.base.innerclasses.ex;
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
	public static TossingFactory factory = new TossingFactory() {
		
		@Override
		public Tossing getTossing() {
			return new CoinTossing();
		}
	};
}
class DiceTossing implements Tossing{
	private Random rand = new Random();
	private final int NUM=6;
	public void toss(){
		System.out.println(rand.nextInt(NUM)+1);
	}
	public static TossingFactory factory= new TossingFactory() {
		
		@Override
		public Tossing getTossing() {
			return new DiceTossing();
		}
	};
} 

public class Ex17 {
	static void tossing(TossingFactory tf){
		Tossing tossing = tf.getTossing();
		tossing.toss();
	}
	public static void main(String[] args){
		tossing(CoinTossing.factory);
		tossing(DiceTossing.factory);
		
	}
}