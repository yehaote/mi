package mi.practice.java.base.containers;

import static mi.practice.java.base.util.Print.*;
/**
 * 永远不会产生相应的值,
 * Math.random()只会产生介于0-1之间的数,
 * 并不包括它们本身
 */
public class RandomBounds {
	static void usage(){
		print("Usage:");
		print("\tRandomBounds lower");
		print("\tRandomBounds upper");
		System.exit(1);
	}
	public static void main(String[] args){
		if(args.length != 1){
			usage();
		}
		if(args[0].equals("lower")){
			while(Math.random() != 0.0){
				; // 继续产生
			}
			print("Produce 0.0!");
		}else if(args[0].equals("upper")){
			while(Math.random() != 1.0){
				; // 继续产生
			}
			print("Produced 1.0!");
		}else{
			usage();
		}
	}
}
