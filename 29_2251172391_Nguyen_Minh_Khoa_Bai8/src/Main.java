import java.util.ArrayList;
import java.util.List;

// 1. Mediator Interface
interface AnimalMediator {
	void send(String message, AnimalBase sender);
}

// 2. Abstract Colleague
abstract class AnimalBase {
	protected AnimalMediator mediator;
	protected String name;

	public AnimalBase(String name, AnimalMediator mediator) {
		this.name = name;
		this.mediator = mediator;
	}

	// Gửi thông điệp vào mediator
	public void sendMessage(String message) {
		System.out.println("[" + name + "] gửi: \"" + message + "\"");
		mediator.send(message, this);
	}

	// Nhận thông điệp từ mediator (phương thức trừu tượng)
	public abstract void receive(String message);
}

// 3. Concrete Colleagues
class Dog extends AnimalBase {
	public Dog(String name, AnimalMediator mediator) {
		super(name, mediator);
	}

	@Override
	public void receive(String message) {
		System.out.println("    (Dog " + name + " nghe được: \"" + message + "\")");
	}
}

class Cat extends AnimalBase {
	public Cat(String name, AnimalMediator mediator) {
		super(name, mediator);
	}

	@Override
	public void receive(String message) {
		System.out.println("    (Cat " + name + " kêu đáp hoặc chỉ nghe: \"" + message + "\")");
	}
}

class Bird extends AnimalBase {
	public Bird(String name, AnimalMediator mediator) {
		super(name, mediator);
	}

	@Override
	public void receive(String message) {
		System.out.println("    (Bird " + name + " hót vang: \"" + message.toUpperCase() + "\")");
	}
}

// 4. Concrete Mediator


class AnimalMediatorImpl implements AnimalMediator {
	// Danh sách các colleague mà mediator quản lý
	private List<AnimalBase> animals = new ArrayList<>();

	// Cho phép đăng ký Animal vào mediator
	public void registerAnimal(AnimalBase animal) {
		animals.add(animal);
	}

	@Override
	public void send(String message, AnimalBase sender) {
		// Khi nhận thông điệp từ một Animal, mediator sẽ "phân phối" đến tất cả Animal khác
		for (AnimalBase animal : animals) {
			if (animal != sender) {
				animal.receive(message);
			}
		}
	}
}

// 5. Client
public class Main {
	public static void main(String[] args) {
		// Tạo mediator
		AnimalMediatorImpl mediator = new AnimalMediatorImpl();

		// Tạo các Animal và đăng ký vào mediator
		AnimalBase dog = new Dog("Fido", mediator);
		AnimalBase cat = new Cat("Whiskers", mediator);
		AnimalBase bird = new Bird("Tweety", mediator);

		mediator.registerAnimal(dog);
		mediator.registerAnimal(cat);
		mediator.registerAnimal(bird);

		// Mỗi animal gửi một thông điệp
		dog.sendMessage("Woof! Woof!");
		System.out.println();
		cat.sendMessage("Meow~ Meow~");
		System.out.println();
		bird.sendMessage("Chirp chirp");
	}
}
