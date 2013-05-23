package mi.practice.java.base.enumerated.menu;

import mi.practice.java.base.enumerated.menu.Food.Appetizer;
import mi.practice.java.base.enumerated.menu.Food.Coffee;
import mi.practice.java.base.enumerated.menu.Food.Dessert;
import mi.practice.java.base.enumerated.menu.Food.MainCourse;

public class TypeOfFood {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Food food = Appetizer.SALAD;
		food = MainCourse.LASAGNE;
		food = Dessert.GELATO;
		food = Coffee.CAPPUCCIO;
	}
}
