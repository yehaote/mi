package mi.practice.java.base.typeinfo;
/**
 * Java的Class也支持泛型
 * @author waf
 *
 */
@SuppressWarnings("rawtypes")
public class GenericClassReferences {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Class intClass = int.class;
		Class<Integer> genericIntClass = int.class;
		genericIntClass =Integer.class;
//		genericIntClass=double.class;
	}
}
