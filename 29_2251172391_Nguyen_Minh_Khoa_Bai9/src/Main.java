interface SoundStrategy {
    void makeSound();
}

class Bark implements SoundStrategy {
    @Override
    public void makeSound() {
        System.out.println("Woof! Woof!");
    }
}

class Meow implements SoundStrategy {
    @Override
    public void makeSound() {
        System.out.println("Meow~ Meow~");
    }
}

class Chirp implements SoundStrategy {
    @Override
    public void makeSound() {
        System.out.println("Chirp chirp!");
    }
}

// 3. Context
class Animal {
    private String name;
    private SoundStrategy soundStrategy;

    public Animal(String name, SoundStrategy soundStrategy) {
        this.name = name;
        this.soundStrategy = soundStrategy;
    }

    public void setSoundStrategy(SoundStrategy soundStrategy) {
        this.soundStrategy = soundStrategy;
    }

    public void makeSound() {
        System.out.print(name + " says: ");
        soundStrategy.makeSound();
    }
}

// 4. Client
public class Main {
    public static void main(String[] args) {
        // Tạo các strategy
        SoundStrategy bark = new Bark();
        SoundStrategy meow = new Meow();
        SoundStrategy chirp = new Chirp();

        // Tạo các Animal với strategy mặc định
        Animal dog = new Animal("Dog", bark);
        Animal cat = new Animal("Cat", meow);
        Animal bird = new Animal("Bird", chirp);

        // Demo
        dog.makeSound();   // Dog says: Woof! Woof!
        cat.makeSound();   // Cat says: Meow~ Meow~
        bird.makeSound();  // Bird says: Chirp chirp!

        System.out.println();

        // Thay đổi cách phát âm của dog thành 'Chirp'
        dog.setSoundStrategy(chirp);
        dog.makeSound();   // Dog says: Chirp chirp!
    }
}
