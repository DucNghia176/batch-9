package JavaCore.OOP;

class A {
    String name = "A";
    A() {
        System.out.println("A");
    }
}

class B extends A {
    String name = "B";
    B() {
        System.out.println("B");
    }

    void show() {
        System.out.println("This name: " +this.name);
        System.out.println("Super name: " +super.name);
    }
}
public class ThisSuper {
    public static void main(String[] args) {
        B b = new B();
        b.show();
    }
}
