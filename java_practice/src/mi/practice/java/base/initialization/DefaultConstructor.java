package mi.practice.java.base.initialization;
/**
 * 默认构造函数
 * 如果没有指定给类写一个构造方法的话, 
 * 系统会默认给类生成一个无参的构造函数
 */

class Bird{}

public class DefaultConstructor {

	public static void main(String[] args) {
		Bird bird = new Bird();
		bird.toString();
	}

}
