package JavaCore.OOP;

class Car{
    String name;
    private int show(int a) {
        return a;
    }

    public double show(double a) {
        return a;
    }
}

class car1 extends Car{
    @Override
    public double show(double a) {
        return super.show(a);
    }
    String name = super.name;

}

public class C {
    public static void main(String[] args) {
        Car c = new Car();
        car1 car1 = new car1();


        c.name = "BMW";
        System.out.println(c.name);
        System.out.println(car1.name);
    }
}

