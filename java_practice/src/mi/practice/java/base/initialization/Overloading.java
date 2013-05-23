package mi.practice.java.base.initialization;

import static mi.practice.java.base.util.Print.*;

/**
 * 演示Overloading
 * 构造方法的重载和一般方法的重载
 */

class Tree {
	int height;

	Tree() {
		print("Planting a seedling");
	}

	Tree(int initialHeight) {
		height = initialHeight;
		print("Creating new Tree that is " + height + " feet tall");
	}

	void info() {
		print("Tree is " + height + " feet tall");
	}

	void info(String s) {
		print(s + ": Tree is " + height + " feet tall");
	}
}

public class Overloading {

	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			Tree tree = new Tree(i);
			tree.info();
			tree.info("overloaded method");
		}
		
		Tree tree = new Tree();
		tree.info();
	}

}
