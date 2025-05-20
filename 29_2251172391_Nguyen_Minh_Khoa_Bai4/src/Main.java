public class Main {
	// === Product Interfaces ===
	interface LandAnimal { //-động vật trên cạn
		void makeSound();
	}

	interface WaterAnimal { //- động vật dưới nước
		void makeSound();
	}

	// === Concrete Products ===
	static class Dog implements LandAnimal {
		public void makeSound() {
			System.out.println("Gâu gâu!");
		}
	}

	static class Cat implements LandAnimal {
		public void makeSound() {
			System.out.println("Meo meo!");
		}
	}

	static class Lion implements LandAnimal {
		public void makeSound() {
			System.out.println("Gừuu gừuu!");
		}
	}

	static class Duck implements WaterAnimal {
		public void makeSound() {
			System.out.println("Quạc quạc!");
		}
	}

	// === Abstract Factory Interface ===
	interface AnimalFactory {
		LandAnimal createLandAnimal();
		WaterAnimal createWaterAnimal();
	}

	// === Concrete Factories ===
	static class DomesticFactory implements AnimalFactory { //- gia súc - gia cầm
		public LandAnimal createLandAnimal() {
			return new Dog();
		}
		public WaterAnimal createWaterAnimal() {
			return new Duck();
		}
	}

	static class WildFactory implements AnimalFactory { //- động vật hoang dã
		public LandAnimal createLandAnimal() {
			return new Lion();
		}
		public WaterAnimal createWaterAnimal() {
			return new Duck(); // Giả sử hoang dã cũng có vịt
		}
	}

	// === Client Code ===
	public static void main(String[] args) {
		// Dùng DomesticFactory(gia cam)
		AnimalFactory domestic = new DomesticFactory();
		LandAnimal pet = domestic.createLandAnimal();
		WaterAnimal bird = domestic.createWaterAnimal();
		System.out.print("Domestic land animal: ");
		pet.makeSound();   // Gâu gâu!
		System.out.print("Domestic water animal: ");
		bird.makeSound();  // Quạc quạc!

		// Dùng WildFactory(hoang da)
		AnimalFactory wild = new WildFactory();
		LandAnimal wildBeast = wild.createLandAnimal();
		WaterAnimal wildBird = wild.createWaterAnimal();
		System.out.print("Wild land animal: ");
		wildBeast.makeSound();  // Gừuu gừuu!
		System.out.print("Wild water animal: ");
		wildBird.makeSound();   // Quạc quạc!
	}
}
