package mi.practice.java.base.enumerated.menu;
/**
 * 因为enum不能再继承其他的类, 
 * 所以我们很难对它进行分组,
 * 不过可以通过接口来实现.
 * 
 * 这样子所有的枚举都是Food,
 * 然后还各自有自己的分组.
 */
public interface Food {
	enum Appetizer implements Food{
		SALAD, SOUP, SPRING_ROLLS;
	}
	enum MainCourse implements Food{
		LASAGNE, BURRITO, PAD_THAI,
		LENTILS, HUMMOUS, VINDALOO;
	}
	enum Dessert implements Food{
		TIRAMISU, GELATO, BLACK_FOREST_CAKE,
		FRUIT, CREME_CARAMEL;
	}
	enum Coffee implements Food{
		BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
		LATTE, CAPPUCCIO, TEA, HERB_TEA;
	}
}
