package mi.practice.java.base.enumerated.menu;

import mi.practice.java.base.util.Enums;
/**
 * Meal用nest enums
 */
public enum Meal2 {
	APPETIZER(Food.Appetizer.class),
	MAINCOURSE(Food.MainCourse.class),
	DESSERT(Food.Dessert.class),
	COFFEE(Food.Coffee.class);
	
	Food[] values;
	
	Meal2(Class<? extends Food> kind){
		values=kind.getEnumConstants();
	}
	
	public Food randomSelection(){
		return Enums.random(values);
	}
	
	interface Food {
		enum Appetizer implements Food {
			SALAD, SOUP, SPRING_ROLLS;
		}

		enum MainCourse implements Food {
			LASAGNE, BURRITO, PAD_THAI, LENTILS, HUMMOUS, VINDALOO;
		}

		enum Dessert implements Food {
			TIRAMISU, GELATO, BLACK_FOREST_CAKE, FRUIT, CREME_CARAMEL;
		}

		enum Coffee implements Food {
			BLACK_COFFEE, DECAF_COFFEE, ESPRESSO, LATTE, CAPPUCCIO, TEA, HERB_TEA;
		}
	}
	
	
	public static void main(String[] args){
		for(int i=0;i<5;i++){
			// 迭代所有的分类
			for(Meal2 meal: Meal2.values()){
				// 每一个分类产生一个food
				Food food = meal.randomSelection();
				System.out.println(food);
			}
			System.out.println("------");
		}
	}
}
