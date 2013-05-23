package mi.practice.java.base.polymorphism;

import static mi.practice.java.base.util.Print.*;

/**
 * 当子类覆盖父类在构造函数中调用的方法时,
 * 初始化时父类就会调用这个覆盖的方法
 * 如果这个方法里面涉及对子类成员的操作
 * 子类成员会先初始化为默认值(0, null)
 * 显示声明也是不会起效的
 * 实际的初始化流程:
 * 1.先给对象分配存储空间, primary初始化为0, 引用初始化为null
 * 2.调用父类构造函数
 * 3.初始化现有成员
 * 4.执行构造函数体
 * 在构造器里可以安全调用的函数只有final函数
 * Ex15
 */
class Glyph {
	void draw() {
		print("Glyph.draw()");
	}

	Glyph() {
		print("Glyph() before draw()");
		draw();
		print("Glyph() after draw()");
	}
}

class RoundGlyph extends Glyph {
	private int radius = 1;

	RoundGlyph(int i) {
		radius = i;
		print("RoundGlyph.RoundGlyph(), radius = " + radius);
	}

	@Override
	void draw() {
		print("RoundGlyph.draw(), radius = " + radius);
	}
}

class RectangularGlyph extends Glyph {
	private int length = 1;
	private int width = 1;

	public RectangularGlyph(int length, int width) {
		this.length = length;
		this.width = width;
		print("Rectangular.Rectangular(int, int), length = " + length
				+ ", width = " + width);

	}
	
	@Override
	void draw(){
		print("Rectangular.draw(), length = " + length
				+ ", width = " + width);
	}
}

public class PolyConstructor {
	public static void main(String[] args) {
		new RoundGlyph(5);
		new RectangularGlyph(3, 4);
	}
}
