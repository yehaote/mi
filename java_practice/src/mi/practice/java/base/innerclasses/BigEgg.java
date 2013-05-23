package mi.practice.java.base.innerclasses;
import static mi.practice.java.base.util.Print.*;
/**
 * 内部类是不会覆盖的, 
 * BigEgg继承了Egg,
 * 也有一个自己的内部类叫做Yolk,
 * 可是父类构造的时候还是会调用父类的Yolk
 * 这两个类完全是两个不同的类
 */
class Egg{
	protected Yolk y;
	protected class Yolk{
		public Yolk(){
			print("Egg.Yolk()");
		}
	}
	public Egg(){
		print("New Egg()");
		y = new Yolk();
	}
}

public class BigEgg extends Egg{
	public class Yolk{
		public Yolk(){
			print("BigEgg.Yolk()");
		}
	}
	public static void main(String[] args){
		new BigEgg();
	}
}
