package mi.practice.java.base.control;

import static mi.practice.java.base.util.Print.*;

/**
 * while循环结合label
 */
public class LabeledWhile {

	public static void main(String[] args) {
		int i=0;
		outer: while(true){
			print("Outer while loop");
			while(true){
				i++;
				print("i = "+i);
				if(i==1){
					print("continue");
					continue;
				}
				if(i==3){
					print("continue outer");
					continue outer;
				}
				if(i==5){
					print("break");
					break;
				}
				if(i==7){
					print("break outer");
					break outer;
				}
			}
		}
	}

}
