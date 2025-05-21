public class Main {
	// === SoundBehavior Interface ===
	interface SoundBehavior {
		void makeSound();
	}

	// === Concrete Sound Behaviors ===
	static class CatSound implements SoundBehavior {
		public void makeSound() {
			System.out.println("Meow");
		}
	}

	static class DogSound implements SoundBehavior {
		public void makeSound() {
			System.out.println("Woof");
		}
	}

	static class BirdSound implements SoundBehavior {
		public void makeSound() {
			System.out.println("Chirp");
		}
	}

	static class DefaultSound implements SoundBehavior {
		public void makeSound() {
			System.out.println("Generic Animal Sound");
		}
	}

	// === Product: Animal ===
	static class Animal {
		private final String name;
		private final int age;
		private final String type;
		private final boolean canSwim;
		private final boolean canFly;
		private final SoundBehavior soundBehavior;

		public Animal(String name, int age, String type, boolean canSwim, boolean canFly, SoundBehavior soundBehavior) {
			this.name = name;
			this.age = age;
			this.type = type;
			this.canSwim = canSwim;
			this.canFly = canFly;
			this.soundBehavior = soundBehavior;
		}

		public void makeSound() {
			soundBehavior.makeSound();
		}

		@Override
		public String toString() {
			return "Animal{name='" + name + "', age=" + age + ", type='" + type + "', canSwim=" + canSwim + ", canFly=" + canFly + "}";
		}
	}

	// === Builder Interface ===
	interface AnimalBuilder {
		AnimalBuilder setName(String name);
		AnimalBuilder setAge(int age);
		AnimalBuilder setType(String type);
		AnimalBuilder setCanSwim(boolean canSwim);
		AnimalBuilder setCanFly(boolean canFly);
		AnimalBuilder setSoundBehavior(SoundBehavior soundBehavior);
		Animal build();
	}

	// === Concrete Builder ===
	static class ConcreteAnimalBuilder implements AnimalBuilder {
		private String name;
		private int age;
		private String type;
		private boolean canSwim;
		private boolean canFly;
		private SoundBehavior soundBehavior;

		public AnimalBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public AnimalBuilder setAge(int age) {
			this.age = age;
			return this;
		}

		public AnimalBuilder setType(String type) {
			this.type = type;
			return this;
		}

		public AnimalBuilder setCanSwim(boolean canSwim) {
			this.canSwim = canSwim;
			return this;
		}

		public AnimalBuilder setCanFly(boolean canFly) {
			this.canFly = canFly;
			return this;
		}

		public AnimalBuilder setSoundBehavior(SoundBehavior soundBehavior) {
			this.soundBehavior = soundBehavior;
			return this;
		}

		public Animal build() {
			if (soundBehavior == null) {
				soundBehavior = new DefaultSound();
			}
			return new Animal(name, age, type, canSwim, canFly, soundBehavior);
		}
	}

	// === Director ===
	static class AnimalDirector {
		public Animal createCat() {
			return new ConcreteAnimalBuilder()
							.setName("Cat")
							.setAge(3)
							.setType("Land")
							.setCanSwim(false)
							.setCanFly(false)
							.setSoundBehavior(new CatSound())
							.build();
		}

		public Animal createDog() {
			return new ConcreteAnimalBuilder()
							.setName("Dog")
							.setAge(5)
							.setType("Land")
							.setCanSwim(true)
							.setCanFly(false)
							.setSoundBehavior(new DogSound())
							.build();
		}

		public Animal createBird() {
			return new ConcreteAnimalBuilder()
							.setName("Bird")
							.setAge(2)
							.setType("Air")
							.setCanSwim(false)
							.setCanFly(true)
							.setSoundBehavior(new BirdSound())
							.build();
		}
	}

	// === Main Method ===
	public static void main(String[] args) {
		AnimalDirector director = new AnimalDirector();

		Animal cat = director.createCat();
		Animal dog = director.createDog();
		Animal bird = director.createBird();

		System.out.println(cat);
		cat.makeSound();

		System.out.println(dog);
		dog.makeSound();

		System.out.println(bird);
		bird.makeSound();

		// Custom Animal (Builder trực tiếp)
		Animal fish = new ConcreteAnimalBuilder()
						.setName("Fish")
						.setAge(1)
						.setType("Water")
						.setCanSwim(true)
						.setCanFly(false)
						.setSoundBehavior(() -> System.out.println("Blub blub"))
						.build();

		System.out.println(fish);
		fish.makeSound();
	}
}
