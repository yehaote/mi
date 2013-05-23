package mi.practice.java.base.control;

import java.util.Random;
/**
 * Exercise 3:
 */
public class ClassifyNumber2 {
	public static void main(String[] args) {
		Random rand1 = new Random();
		Random rand2 = new Random();
		while(true){
			int m=rand1.nextInt();
			int n=rand2.nextInt();
			if(m<n)
				System.out.println("Less than");
			else if(m>n)
				System.out.println("Greater than");
			else
				System.out.println("Equals");
		}
	}
}
