package mi.practice.java.base.initialization;

class Book{
	boolean checkOut=false;
	Book(boolean checkOut){
		this.checkOut=checkOut;
	}
	
	void checkIn(){
		checkOut=false;
	}
	
	protected void finalize(){
		if(checkOut){
			System.out.println("Error: checked Out");
			//Normally, you'll also do this:
			//super.finalize();//Call the base-class version
		}
	}
}

public class TerminationCondition {
	public static void main(String[] args){
		Book novel = new Book(true);
		//Proper cleanup:
		novel.checkIn();
		// Drop the reference, forget to clean up:
		new Book(true);
		// Force garbage collection & finalization:
		System.gc();
	}
}
