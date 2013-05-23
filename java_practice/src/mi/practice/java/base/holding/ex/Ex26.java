package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mi.practice.java.base.util.TextFile;

public class Ex26 {
	public static void main(String[] args) {
		List<String> words = new TextFile(
				"src/mi/practice/java/base/holding/SetOperations.java", "\\W+");
		Map<String, ArrayList<Integer>> wordTracker=new HashMap<String, ArrayList<Integer>>();
		for(int i=0;i<words.size();i++){
			String word = words.get(i);
			ArrayList<Integer> list = wordTracker.get(word);
			if(list==null){
				list = new ArrayList<Integer>();
				list.add(i);
				wordTracker.put(word, list);
			}else{
				list.add(i);
			}
		}
		System.out.println(wordTracker);
		int size=0;
		for(List<Integer> list: wordTracker.values()){
			size+=list.size();
		}
		String[] contents = new String[size];
		for(String str:wordTracker.keySet()){
			List<Integer> tracker=wordTracker.get(str);
			for(Integer i:tracker){
				contents[i]=str;
			}
		}
		for(String str:contents){
			System.out.print(str+" ");
		}
		System.out.println();
	}
}
