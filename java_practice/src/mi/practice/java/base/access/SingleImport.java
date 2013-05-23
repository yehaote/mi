package mi.practice.java.base.access;

import java.util.ArrayList;

public class SingleImport {
	public static void main(String[] args){
		@SuppressWarnings({ "rawtypes", "unused" })
		ArrayList arrayList = new ArrayList();
	}
}
