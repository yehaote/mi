package mi.practice.java.base.generics;

//import java.util.ArrayList;
//import java.util.List;
/**
 * 
 * @author waf
 */
public class NonCovariantGenerics {
	//不能编译
//	List<Fruit> fList= new ArrayList<Apple>();
	/* 看上去上面的语句像向上转换, 实际上不是
	 * 因为List<Fruit>是包含所有Fruit及其所有子类,
	 * 而List<Apple>只能包含Apple及其所有子类.
	 * 即使Apple是Fruit的子类
	 */
}
