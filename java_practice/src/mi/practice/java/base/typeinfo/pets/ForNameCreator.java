//: mi.practice.java.base.typeinfo.pets/ForNameCreator.java
package mi.practice.java.base.typeinfo.pets;
import java.util.*;
/**
 * 把继承Pet的类的名陈都写在一个static的成员里面,
 * 返回的时候直接返回返回静态的list
 * @author waf
 *
 */
public class ForNameCreator extends PetCreator {
  private static List<Class<? extends Pet>> types =
    new ArrayList<Class<? extends Pet>>();
  private static String[] typeNames = {
    "mi.practice.java.base.typeinfo.pets.Mutt",
    "mi.practice.java.base.typeinfo.pets.Pug",
    "mi.practice.java.base.typeinfo.pets.EgyptianMau",
    "mi.practice.java.base.typeinfo.pets.Manx",
    "mi.practice.java.base.typeinfo.pets.Cymric",
    "mi.practice.java.base.typeinfo.pets.Rat",
    "mi.practice.java.base.typeinfo.pets.Mouse",
    "mi.practice.java.base.typeinfo.pets.Hamster"
  };	
  
  @SuppressWarnings("unchecked")
  private static void loader() {
    try {
    	//Class.forName(加载class), 并转换成Class<? extends Pet>类型
      for(String name : typeNames)
        types.add(
          (Class<? extends Pet>)Class.forName(name));
    } catch(ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  static { loader(); }
  
  @Override
  public List<Class<? extends Pet>> types() {return types;}
} ///:~
