package mi.practice.java.base.access;

/**
 * 当引用两个都包含相同类名的包时,
 * 使用这个同名类时会产生编译错误.
 * 如果不使用这个同名类, 而在使用其他类的时候
 * 一切正常运作.
 * 解决同名类的办法
 * 1.再进行依次清晰的引用
 * 2.使用全称来进行调用
 */
import mi.practice.java.base.access.simple.*;
import java.util.*;

//import java.util.Vector;

@SuppressWarnings("unused")
public class Ex2 {
	public static void main(String[] args){
//		Vector vector = new Vector();
		@SuppressWarnings("rawtypes")
		java.util.Vector vector = new java.util.Vector();
	}
}
