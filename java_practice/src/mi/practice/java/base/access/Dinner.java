package mi.practice.java.base.access;

import mi.practice.java.base.access.dessert.*;
/**
 * 默认访问权限=无访问修饰符=包访问控制权限
 * 不能在包之外访问默认访问权限
 */
public class Dinner {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Cookie x = new Cookie();
		//! x.bite(); //不能访问
	}
}
