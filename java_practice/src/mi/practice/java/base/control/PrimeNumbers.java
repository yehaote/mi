package mi.practice.java.base.control;

/**
 * Exercise 4:
 * 两个for循环,用%找出
 */
public class PrimeNumbers {
	public static void main(String[] args) {
		for(int i=1;i<10000;i++){
			int factors=0;
			for(int j=1;j<(i+2)/2;j++){
				if((i%j)==0)
					factors++;
			}
			if(factors<2)
				System.out.println(i +" is prime");
		}
	}

}
