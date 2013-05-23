package mi.practice.java.base.typeinfo;
/**
 * java的Class支持通配符,
 * ?代表任意
 * @author waf
 */
public class WildcardClassReferences {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Class<?> intClass = int.class;
		intClass=double.class;
	}
}
