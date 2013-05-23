package mi.practice.java.base.reusing;
/**
 * 演示子类调用父类有参构造函数
 * 子类构造函数必须显示调用其有参构造函数, 
 * 而且必须在构造函数一开始马上调用
 */
class Game{
	Game(int i){
		System.out.println("Game constructor "+i);
	}
}

class BoardGame extends Game{
	BoardGame(int i){
		super(i);
		System.out.println("BoardGame constructor "+i);
	}
}

public class Chess extends BoardGame{
	Chess(){
		super(11);
		System.out.println("Chess constructor");
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Chess chess = new Chess();
	}
}
