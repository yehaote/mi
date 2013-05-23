package mi.practice.java.base.interfaces.classprocess;

import java.util.Arrays;
import static mi.practice.java.base.util.Print.*;
/**
 * 覆盖方法的时候, 返回的类型是可以共变的
 * 就是说可以返回继承于原函数的类, 
 * 远函数返回Object, 所以说覆盖方法返回任何对象都可以
 * 但是方法参数不是共变的
 * strategy design pattern(策略设计模式)
 * Processor对象是策略
 */
class Processor {
	public String name() {
		return getClass().getSimpleName();
	}

	Object process(Object input) {
		return input;
	}
}

class Upcase extends Processor {
	@Override
	String process(Object input) {
		return ((String) input).toUpperCase();
	}
}

class Downcase extends Processor {
	@Override
	String process(Object input) {
		return ((String) input).toLowerCase();
	}
}

class Splitter extends Processor {
	@Override
	String process(Object input) {
		return Arrays.toString(((String) input).split(" "));
	}
}

public class Apply {
	public static void process(Processor p, Object s) {
		print("Using processor " + p.name());
		print(p.process(s));
	}

	public static String s = "Disagreement with beliefs is by definition incorrect";

	public static void main(String[] args) {
		process(new Upcase(), s);
		process(new Downcase(), s);
		process(new Splitter(), s);
	}
}
