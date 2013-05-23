package mi.practice.java.base.enumerated.menu;
/**
 * 每一种类型的food选择一个组成一餐
 */
public class Meal {
	public static void main(String[] args){
		for(int i=0;i<5;i++){
			for(Course course:Course.values()){
				Food food = course.randomSelection();
				System.out.println(food);
			}
			System.out.println("------");
		}
	}
}
