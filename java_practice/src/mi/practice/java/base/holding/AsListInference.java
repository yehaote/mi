package mi.practice.java.base.holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * 不能编译snow2的原因是, Arrays.asList()
 * 在这里默认返回Powder类型, 
 * 这个时候我们不能把引用指向List<Snow>
 */
class Snow{}
class Powder extends Snow{}
class Light extends Powder{}
class Heavy extends Powder{};
class Crusty extends Snow{}
class Slush extends Snow{}
public class AsListInference {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		List<Snow> snow1 = Arrays.asList(new Crusty(), new Slush(), new Powder());
//		List<Snow> snow2 = Arrays.asList(new Light(), new Heavy()); //不能编译, 需要List<Powder>;
		List<Snow> snow3 = new ArrayList<Snow>();
		Collections.addAll(snow3, new Light(), new Heavy());
		List<Snow> snow4 =Arrays.<Snow>asList(new Light(),new Heavy());//这样可以
	}
}
