interface IAnimal{
	void makeSound();
}

class Cat implements IAnimal{
	public void makeSound() {
		System.out.println("meo meo");
	}
}

class Dog implements IAnimal{
	public void makeSound() {
		System.out.println("gau gau");
	}
}
//- Khởi tạo nhà máng AniamlFactory
abstract class AnimalFactory {
	public abstract IAnimal createAnimal(); //- trả về một con vật nào đó (kiểu IAnimal)
}

//- Khởi tạo nhà máy động vật cụ thể
class CatFactory extends AnimalFactory{
	public IAnimal createAnimal() {
		return new Cat(); //-trả về đúng kiểu là Cat
	}
}

class DogFactory extends AnimalFactory{
	public IAnimal createAnimal() {
		return new Dog(); //-trả về đúng kiểu là Dog
	}
}

public class Solusion1 {
	public static void main(String[] args) {
		AnimalFactory catFactory = new CatFactory();
		AnimalFactory dogFactory = new DogFactory();

		IAnimal cat = catFactory.createAnimal();
		IAnimal dog = dogFactory.createAnimal();

		cat.makeSound();
		dog.makeSound();
	}
}
