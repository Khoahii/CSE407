public class Main {
	public static void main(String[] args) {
		// Chuẩn bị Adaptee
		Dog dog = new Dog();
		Cat cat = new Cat();

		// Bao bọc bằng Adapter để vào interface Animal chung
		Animal dogAnimal = new DogAdapter(dog);
		Animal catAnimal = new CatAdapter(cat);

		// Client chỉ thao tác qua Animal
		hearAnimal(dogAnimal);
		hearAnimal(catAnimal);
	}

	// Client method 
	public static void hearAnimal(Animal animal) {
		animal.makeSound();
	}
}

// Target interface
interface Animal {
	void makeSound();
}

// Adaptee classes
class Dog {
	public void woof() {
		System.out.println("Woof! Woof!");
	}
}

class Cat {
	public void meow() {
		System.out.println("Meow~ Meow~");
	}
}

// Adapter classes
class DogAdapter implements Animal {
	private final Dog dog;

	public DogAdapter(Dog dog) {
		this.dog = dog;
	}

	@Override
	public void makeSound() {
		// "dịch" lời gọi makeSound() thành woof()
		dog.woof();
	}
}

class CatAdapter implements Animal {
	private final Cat cat;

	public CatAdapter(Cat cat) {
		this.cat = cat;
	}

	@Override
	public void makeSound() {
		// "dịch" lời gọi makeSound() thành meow()
		cat.meow();
	}
}
