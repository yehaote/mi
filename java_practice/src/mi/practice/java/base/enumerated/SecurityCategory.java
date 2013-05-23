package mi.practice.java.base.enumerated;

import mi.practice.java.base.util.Enums;

/**
 * 给Enum分类的方法还用一种是嵌套的enum
 */
enum SecurityCategory {
	STOCK(Security.Stock.class), BOND(Security.Bond.class);
	Security[] values;

	SecurityCategory(Class<? extends Security> kind) {
		values = kind.getEnumConstants();
	}

	// 这里的接口是必要的, 为enum提供一种通用的对象表示
	interface Security {
		enum Stock implements Security {
			SHORT, LONG, MARGIN
		}

		enum Bond implements Security {
			MUNICIPAL, JUNK
		}
	}

	public Security randomSelection() {
		return Enums.random(values);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			// 随机获取一个分类
			SecurityCategory category = Enums.random(SecurityCategory.class);
			// 从分类中随机选择一个
			System.out.println(category + ": " + category.randomSelection());
		}
	}
}
