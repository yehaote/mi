package mi.practice.java.base.exceptions.ex;

import static mi.practice.java.base.util.Print.print;

class Characteristic {
	@SuppressWarnings("unused")
	private String s;

	Characteristic(String s) {
		this.s = s;
		print("Creating Characterisitic");
	}

	protected void dispose() {
		print("disposing Characteristic");
	}
}

class Description {
	@SuppressWarnings("unused")
	private String s;

	Description(String s) {
		this.s = s;
		print("Creating Description");
	}

	protected void dispose() {
		print("disposing Description");
	}
}

class LivingCreature {
	private Characteristic p = new Characteristic("is alive");
	private Description t = new Description("Basic Living Creature");

	LivingCreature() {
		print("LivingCreature");
	}

	protected void dispose() {
		print("LivingCreature dispose");
		t.dispose();
		p.dispose();
	}
}

class Animal extends LivingCreature {
	private Characteristic p = new Characteristic("has heart");
	private Description t = new Description("Animal not Vegetable");

	Animal() {
		print("Animal()");
	}

	@Override
	protected void dispose() {
		print("Animal dispose()");
		t.dispose();
		p.dispose();
		super.dispose();
	}
}

class Amphibian extends Animal {
	private Characteristic p = new Characteristic("can live in water");
	private Description t = new Description("Both water and land");

	Amphibian() {
		print("Amphibian");
	}

	@Override
	protected void dispose() {
		print("Amphibian dispose()");
		t.dispose();
		p.dispose();
		super.dispose();
	}
}

class Frog extends Amphibian {
	private Characteristic p = new Characteristic("Croaks");
	private Description t = new Description("Eats Bugs");

	public Frog() {
		print("Forg()");
	}

	@Override
	protected void dispose() {
		print("Frog dispose()");
		t.dispose();
		p.dispose();
		super.dispose();
	}
}
public class Ex17 {
	public static void main(String[] args) {
		Frog frog = null ;
		try{
			frog= new Frog();
			print("Bye!");
			return;
		}finally{
			frog.dispose();
		}
	}
}
