package mi.practice.java.base.typeinfo.pets;

import java.util.LinkedHashMap;
import java.util.Map;

import mi.practice.java.base.util.MapData;
import static mi.practice.java.base.util.Print.*;
/**
 * 加载所有的class, 然后使用class的instance()来代替
 * instanceof继续判断
 * @author waf
 */
public class PetCount3 {
	@SuppressWarnings("serial")
	static class PetCounter extends LinkedHashMap<Class<? extends Pet>, Integer>{
		public PetCounter(){
			//加载所有的type
			super(MapData.map(LiteralPetCreator.allTypes, 0));
		}
		/**
		 * 使用Class.instance()来代替instanceof,
		 * 相对于PetCount来说显得更加清晰
		 * @param pet
		 */
		public void count(Pet pet){
			for(Map.Entry<Class<? extends Pet>, Integer> pair: entrySet()){
				if(pair.getKey().isInstance(pet)){
					put(pair.getKey(), pair.getValue()+1);
				}
			}
		}
		@Override
		public String toString(){
			StringBuilder result = new StringBuilder();
			for(Map.Entry<Class<? extends Pet>, Integer> pair:entrySet()){
				result.append(pair.getKey().getSimpleName());
				result.append("=");
				result.append(pair.getValue());
				result.append(", ");
			}
			//去除最后两个元素, ", "
			result.delete(result.length()-2, result.length());
			return result.toString();
		}
	}
	
	public static void main(String[] args){
		PetCounter petCounter = new PetCounter();
		for(Pet pet : Pets.createArray(20)){
			printnb(pet.getClass().getSimpleName()+" ");
			petCounter.count(pet);
		}
		print();
		print(petCounter);
	}
}
