package mi.practice.java.base.reusing;

public class SpaceShip extends SpaceShipControls {
	private String name;

	public SpaceShip(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public static void main(String[] args) {
		SpaceShip ship = new SpaceShip("NSEA Protector");
		ship.forward(100);
	}
}
