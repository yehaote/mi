package mi.practice.java.base.initialization;
/**
 * 演示带参数的构造方法
 */

class Rock2{
	Rock2(int i){
		System.out.print("Rock "+i+" ");
	}
}

public class SimpleConstructor2 {

	public static void main(String[] args) {
		for(int i=0;i<8;i++){
			new Rock2(i);
		}
	}

}
