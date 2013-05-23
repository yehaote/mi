//: typeinfo/pets/PetCreator.java
// Creates random sequences of Pets.
package mi.practice.java.base.typeinfo.pets;
import java.util.*;
/**
 * 模板设计模式
 * @author waf
 *
 */
public abstract class PetCreator {
  private Random rand = new Random(47);
  // The List of the different types of Pet to create:
  /**
   * 抽象方法, 获取所需要的Pet的class类型
   * @return
   */
  public abstract List<Class<? extends Pet>> types();
  public Pet randomPet() { // Create one random Pet
	  //随机取一个类型
    int n = rand.nextInt(types().size());
    try {
    	//返回一个新的对象
      return types().get(n).newInstance();
    } catch(InstantiationException e) {
    	//
      throw new RuntimeException(e);
    } catch(IllegalAccessException e) {
    	//不合法的访问
    	//一般都是没有访问权限, 
    	//构造函数的权限问题
      throw new RuntimeException(e);
    }
  }	
  
  /**
   * 返回一个Pet数组, 大小由传入参数决定
   * 每个Pet实体调用randomPet进行产生
   * @param size
   * @return
   */
  public Pet[] createArray(int size) {
    Pet[] result = new Pet[size];
    for(int i = 0; i < size; i++)
      result[i] = randomPet();
    return result;
  }
  /**
   * 返回一个PetList, 大小由传入参数决定
   * 产生一个Pet数组, 然后转换成ArrayList输出
   * @param size
   * @return
   */
  public ArrayList<Pet> arrayList(int size) {
    ArrayList<Pet> result = new ArrayList<Pet>();
    Collections.addAll(result, createArray(size));
    return result;
  }
} ///:~
