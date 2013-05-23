package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Collections.nCopies()把一个引用复制几份并放入一个list中返回, 
 * 这个list对象其实是一个CopiesList.
 * Collections.fill()会把现有的list中的值都替换成另一个值.
 * 这里的复制都是复制的引用, fill的时候也是一样, 都是引用的分发.
 * ToString方法后面的无符号16进制数是这个对象的hashcode,
 * 通过对象的hashCode()方法生成.
 * @author waf
 */
class StringAddress {
	private String s;

	public StringAddress(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return super.toString() + " " + s;
	}
}

public class FillingLists {
	public static void main(String[] args) {
		List<StringAddress> list = new ArrayList<StringAddress>(
				Collections.nCopies(4, new StringAddress("Hello")));
		System.out.println(list);
		Collections.fill(list, new StringAddress("World"));
		System.out.println(list);
	}
}
