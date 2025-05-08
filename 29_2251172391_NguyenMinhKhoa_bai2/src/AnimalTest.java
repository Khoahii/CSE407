public class AnimalTest {
	public static void main(String[] args) {
		// Tạo mảng Animal chứa Dog và Cat
		Animal[] animals = { new Dog(), new Cat() };
		// In âm thanh từng con
		for (Animal a : animals) {
			System.out.println(a.makeSound());
		}
	}
}
