package mi.practice.java.base.typeinfo;

import java.util.ArrayList;
/**
 * 加了NULL对象以后, 在某些地方还是需要对这个
 * NULL进行判断, 只是一些原有的方法就不需要判断了
 * 比如toString()
 * @author waf
 */
@SuppressWarnings("serial")
public class Staff extends ArrayList<Position>{
	public void add(String title, Person person){
		add(new Position(title, person));
	}
	public void add(String... titles){
		for(String title :titles){
			add(new Position(title));
		}
	}
	public Staff(String... titles){
		add(titles);
	}
	public boolean positionAvailable(String title){
		for(Position position: this){
			if(position.getTitle().equals(title) && position.getPerson()==Person.NULL){
				return true;
			}
		}
		return false;
	}
	public void fillPosition(String title, Person hire){
		for(Position position:this){
			if(position.getTitle().equals(title)&&position.getPerson()==Person.NULL){
				position.setPerson(hire);
				return;
			}
		}
		throw new RuntimeException("Position "+title+" not available");
	}
	public static void main(String[] args){
		Staff staff = new Staff("President","CTO","Marketing Manager","Product Manager",
				"Project Lead","Software Engineer","Software Engineer","Software Engineer"
				,"Software Engineer","Test Engineer","Technical Writer");
		staff.fillPosition("President", new Person("Me", "Last", "The Top, Lonely At"));
		staff.fillPosition("Project Lead", new Person("Janet", "Planner", "The Burbs"));
		if(staff.positionAvailable("Software Engineer")){
			staff.fillPosition("Software Engineer", new Person("Bob","Coder", "Bright Light City"));
		}
		System.out.println(staff);
	}
}
