package mi.practice.java.base.interfaces.interfaceprocessor;

import java.util.Arrays;
/***
 * 
 */
public abstract class StringProcessor implements Processor{
	@Override
	public String name(){
		return getClass().getSimpleName();
	}
	@Override
	public abstract String process(Object input);
	public static String s ="If she weighs the same as a duck, she's made of wood";
	public static void main(String[] args){
		Apply.process(new Upcase(), s);
		Apply.process(new Downcase(), s);
		Apply.process(new Splitter(), s);
	}
}

class Upcase extends StringProcessor{
	@Override
	public String process(Object input) {
		return ((String)input).toUpperCase();
	}
}
class Downcase extends StringProcessor{
	@Override
	public String process(Object input){
		return ((String)input).toLowerCase();
	}
}
class Splitter extends StringProcessor{
	@Override
	public String process(Object input){
		return Arrays.toString(((String)input).split(" "));
	}
}
