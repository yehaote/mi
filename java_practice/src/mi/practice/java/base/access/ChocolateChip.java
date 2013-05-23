package mi.practice.java.base.access;

import mi.practice.java.base.access.dessert.Cookie;

public class ChocolateChip extends Cookie{
	public ChocolateChip(){
		System.out.println("ChocolateShip constructor");
	}
	
	public void chomp(){
//		bite(); // 不能访问bite()方法
	}
	
	public static void main(String[] args){
		ChocolateChip x = new ChocolateChip();
		x.chomp();
	}
}
