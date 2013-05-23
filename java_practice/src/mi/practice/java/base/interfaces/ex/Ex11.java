package mi.practice.java.base.interfaces.ex;

import mi.practice.java.base.interfaces.interfaceprocessor.Apply;
import mi.practice.java.base.interfaces.interfaceprocessor.Processor;


class StringReplacer{
	public String refactor(String input){
		StringBuilder sb = new StringBuilder();
		for(char c : input.toCharArray()){
			sb.append((++c));
		}
		return sb.toString();
	}
}
class StringReplacerAdapter implements Processor{
	private StringReplacer sr;
	public StringReplacerAdapter(StringReplacer sr){
		this.sr= sr;
	}
	@Override
	public String name(){
		return sr.getClass().getSimpleName();
	}
	@Override
	public String process(Object input){
		return sr.refactor((String)input);
	}
}

public class Ex11 {
	static String s ="a1b2c3d4";
	public static void main(String[] args){
		Apply.process(new StringReplacerAdapter(new StringReplacer()), s);
	}
}
