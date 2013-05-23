package mi.practice.java.base.initialization;
/**
 * 构造方法的演示
 */

class Rock{
	Rock(){
		System.out.print("Rock ");
	}
}

public class SimpleConstructor {

	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			new Rock();
		}
	}

}
