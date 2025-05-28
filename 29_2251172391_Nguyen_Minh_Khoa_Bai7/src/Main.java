// Implementor
interface SoundBehavior {
	void makeSound(String sound);
}

// ConcreteImplementors
class SpeakerSound implements SoundBehavior {
	public void makeSound(String sound) {
		System.out.println("Speaker: " + sound);
	}
}

class NetworkSound implements SoundBehavior {
	public void makeSound(String sound) {
		System.out.println("Sending sound over network: " + sound);
	}
}

// Abstraction
abstract class Animal {
	protected SoundBehavior soundBehavior;

	public Animal(SoundBehavior soundBehavior) {
		this.soundBehavior = soundBehavior;
	}

	public abstract void makeSound();
}

// RefinedAbstractions
class Dog extends Animal {
	public Dog(SoundBehavior soundBehavior) {
		super(soundBehavior);
	}

	@Override
	public void makeSound() {
		soundBehavior.makeSound("Woof! Woof!");
	}
}

class Cat extends Animal {
	public Cat(SoundBehavior soundBehavior) {
		super(soundBehavior);
	}

	@Override
	public void makeSound() {
		soundBehavior.makeSound("Meow~ Meow~");
	}
}

// Client
public class Main {
	public static void main(String[] args) {
		SoundBehavior speaker = new SpeakerSound();
		SoundBehavior network = new NetworkSound();

		Animal dog1 = new Dog(speaker);
		Animal dog2 = new Dog(network);

		Animal cat1 = new Cat(speaker);

		dog1.makeSound(); // Speaker: Woof! Woof!
		dog2.makeSound(); // Sending sound over network: Woof! Woof!
		cat1.makeSound(); // Speaker: Meow~ Meow~
	}
}
