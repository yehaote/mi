package mi.practice.java.base.enumerated;

import java.text.DateFormat;
import java.util.Date;
/**
 * 给枚举类型的每一个值实现不同的行为的方法
 * 1. 在枚举中定义一个抽象方法
 * 2. 在每个值里面覆盖实现这个方法
 */
public enum ConstantSpecificMethod {
	DATE_TIME {
		@Override
		String getInfo() {
			// 获取系统时间
			return DateFormat.getDateInstance().format(new Date());
		}
	},
	CLASSPATH {
		@Override
		String getInfo() {
			// 获取CLASSPATH
			return System.getenv("CLASSPATH");
		}
	},
	VERSION {
		@Override
		String getInfo() {
			// 获取Java版本号
			return System.getProperty("java.version");
		}
	};

	abstract String getInfo();

	public static void main(String[] args) {
		// 获取枚举类的所有值进行迭代
		for (ConstantSpecificMethod csm : values()) {
			System.out.println(csm.getInfo());
		}
	}
}