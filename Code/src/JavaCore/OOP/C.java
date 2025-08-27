package JavaCore.OOP;

class Car{
    String name;
}
public class C {
    public static void main(String[] args) {
        Car c = new Car();
        Car c1 = new Car();

        c.name = "BMW";
        c1.name = "Volvo";
        System.out.println(c.name);
        System.out.println(c1.name);
    }
}
