package JavaCore.OOP;

abstract class AbstractClass {
    abstract void method();
}

class ConcreteClass extends AbstractClass {
    @Override
    void method() {
        System.out.println("Hello");
    }
}

interface Interface1 {
    void print();
}

interface Interface2 {
    void print();
}

class ConcreteClass1 implements Interface1, Interface2 {
    @Override
    public void print() {
        System.out.println("World");
    }
}
public class Abstract {
    public static void main(String[] args) {
        AbstractClass a = new ConcreteClass();
        a.method();

        Interface1 i1 = new ConcreteClass1();
        i1.print();
        Interface2 i2 = new ConcreteClass1();
        i2.print();
        ConcreteClass1 c1 = new ConcreteClass1();
        c1.print();
    }
}
