class AnimalFactory2{
	public IAnimal createAnimal(String type) {
		return switch (type.toLowerCase()) {
			case "dog" -> new Dog();
			case "cat" -> new Cat();
			default -> throw new IllegalArgumentException("Không hỗ trợ loại: " + type);
		};
	}
}

public class Solution2 {
	public static void main(String[] args) {
		AnimalFactory2 factory = new AnimalFactory2();
		IAnimal animal = factory.createAnimal("cat");
		animal.makeSound();
	}
}
