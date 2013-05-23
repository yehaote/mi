package mi.practice.java.base.polymorphism;
import static mi.practice.java.base.util.Print.*;
/**
 * State pattern
 * 在一个类里有一个代表状态的成员类, 
 * 这个成员类定义成父类引用, 但是有多个继承实现
 * 在类里有方法可以把这个成员改变成不同的实现
 */
class Actor{
	public void act(){}
}
class HappyActor extends Actor{
	@Override
	public void act(){
		print("HappyActor");
	}
}
class SadActor extends Actor{
	@Override
	public void act(){
		print("SadActor");
	}
}
class Stage{
	private Actor actor = new HappyActor();
	public void change(){
		actor = new SadActor();
	}
	public void performPlay(){
		actor.act();
	}
}
public class Transmogrify {
	public static void main(String[] args){
		Stage stage = new Stage();
		stage.performPlay();
		stage.change();
		stage.performPlay();
	}
}
