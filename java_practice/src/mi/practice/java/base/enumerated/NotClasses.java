package mi.practice.java.base.enumerated;

enum LikeClasses {
	WINKEN {
		@Override
		void behavior() {
			System.out.println("Behavior1");
		}
	},
	BLINKEN {
		@Override
		void behavior() {
			System.out.println("Behavior2");
		}
	},
	NOD {
		@Override
		void behavior() {
			System.out.println("Behavior3");
		}
	};
	abstract void behavior();
}

public class NotClasses {
	// void f1(LikeClasses.WINKEN instance){} // 不能这么写, 枚举不是class
}

// 编译生成的文件
/*
 * Compiled from "NotClasses.java"
 * abstract class mi.practice.java.base.enumerated.LikeClasses extends
 * java.lang.Enum{
 * public static final mi.practice.java.base.enumerated.LikeClasses WINKEN;
 * public static final mi.practice.java.base.enumerated.LikeClasses BLINKEN;
 * public static final mi.practice.java.base.enumerated.LikeClasses NOD;
 * static {};
 * abstract void behavior();
 * public static mi.practice.java.base.enumerated.LikeClasses[] values();
 * public static mi.practice.java.base.enumerated.LikeClasses
 * valueOf(java.lang.String);
 * mi.practice.java.base.enumerated.LikeClasses(java.lang.String, int,
 * mi.practice.java.base.enumerated.LikeClasses);
 * }
 */
// 一个这样的枚举总共会生产以下class
// LikeClasses$1.class
// LikeClasses$2.class
// LikeClasses$3.class
// LikeClasses.class

