package mi.practice.java.base.typeinfo.pets;

import java.util.HashMap;
import static mi.practice.java.base.util.Print.*;
/**
 * Pet计数器
 * @author waf
 */
public class PetCount {
	/**
	 * 继承HashMap并添加了一个count方法, 
	 * 没有覆盖原有的HashMap的方法,
	 * 只是做了一个扩展
	 * @author waf
	 */
	@SuppressWarnings("serial")
	static class PetCounter extends HashMap<String, Integer>{
		public void count(String type){
			Integer quantity = get(type);
			if(quantity == null){
				put(type,1);
			}else{
				put(type, quantity+1);
			}
		}
	}
	
	public static void countPets(PetCreator creator){
		PetCounter counter = new PetCounter();
		for(Pet pet:creator.createArray(20)){
			printnb(pet.getClass().getSimpleName() +" ");
			/**
			 * 好傻的方式,
			 * 这里的if不能使用ifelse
			 * 因为instanceof对类型跟其父类型都起效
			 */
			if(pet instanceof Pet){
				counter.count("Pet");
			}
			if(pet instanceof Dog){
				counter.count("Dog");
			}
			if(pet instanceof Mutt){
				counter.count("Mutt");
			}
			if(pet instanceof Pug){
				counter.count("Pug");
			}
			if(pet instanceof Cat){
				counter.count("Cat");
			}
			if(pet instanceof EgyptianMau){
				counter.count("EgyptianMau");
			}
			if(pet instanceof Manx){
				counter.count("Manx");
			}
			if(pet instanceof Cymric){
				counter.count("Cymric");
			}
			if(pet instanceof Rodent){
				counter.count("Rodent");
			}
			if(pet instanceof Rat){
				counter.count("Rat");
			}
			if(pet instanceof Mouse){
				counter.count("Mouse");
			}
			if(pet instanceof Hamster){
				counter.count("Hamster");
			}
		}
		print();
		print(counter);
	}
	
	public static void main(String[] args){
		countPets(new ForNameCreator());
	}
}
