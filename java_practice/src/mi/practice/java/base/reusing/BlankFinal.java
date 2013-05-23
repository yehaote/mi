package mi.practice.java.base.reusing;

/**
 * 在声明final变量的时候可以不直接初始化
 * 而在类构造函数中初始化
 */
@SuppressWarnings("unused")
class Poppet {
	private int i;

	Poppet(int ii) {
		i = ii;
	}
}

@SuppressWarnings("unused")
public class BlankFinal {
	private final int i=0;
	private final int j;//空白final声明
	private final Poppet p;//空白对象final声明
	public BlankFinal(){
		j=1;
		p = new Poppet(1);
	}
	
	public BlankFinal(int x){
		j=x;
		p= new Poppet(x);
	}
	
	public static void main(String[] args){
		new BlankFinal();
		new BlankFinal(7);
	}
	
}
