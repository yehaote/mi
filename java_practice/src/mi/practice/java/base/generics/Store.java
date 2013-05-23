package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.Random;

import mi.practice.java.base.util.Generator;

class Product {
	private final int id;
	private String description;
	private double price;

	public Product(int IDnumber, String descr, double price) {
		id = IDnumber;
		description = descr;
		this.price = price;
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return id + ": " + description + ", price: $" + price;
	}

	public void priceChange(double change) {
		price += change;
	}

	public static Generator<Product> generator = new Generator<Product>() {
		private Random rand = new Random(47);

		@Override
		public Product next() {
			return new Product(rand.nextInt(1000), "Test", Math.round(rand
					.nextDouble() * 1000.0) + 0.99);
		}
	};
}

@SuppressWarnings("serial")
class Shelf extends ArrayList<Product>{
	public Shelf(int nProducts){
		Generators.fill(this, Product.generator, nProducts);
	}
}

@SuppressWarnings("serial")
class Aisle extends ArrayList<Shelf>{
	public Aisle(int nShelves, int nProducts){
		for(int i=0;i<nShelves;i++){
			add(new Shelf(nProducts));
		}
	}
}

class CheckoutStand{}
class Office{}

@SuppressWarnings("serial")
public class Store extends ArrayList<Aisle>{
	@SuppressWarnings("unused")
	private ArrayList<CheckoutStand> checkouts=new ArrayList<CheckoutStand>();
	@SuppressWarnings("unused")
	private Office office= new Office();
	public Store(int nAisle, int nShelves, int nProducts){
		for(int i=0;i<nAisle;i++){
			add(new Aisle(nShelves, nProducts));
		}
	}
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		for(Aisle a:this){
			for(Shelf s:a){
				for(Product p:s){
					result.append(p);
					result.append("\n");
				}
			}
		}
		return result.toString();
	}
	public static void main(String[] args){
		System.out.println(new Store(14, 5, 10));
	}
}
