package mi.practice.java.base.operators;

import java.util.Random;
/**
 * 模拟掷硬币
 */
public class CoinFlip {

	public static void main(String[] args) {
		Random rand = new Random(47);
		for(int i=0;i<10;i++){
			if(rand.nextBoolean()){
				System.out.println("heads");
			}else{
				System.out.println("tails");
			}
		}
	}

}
