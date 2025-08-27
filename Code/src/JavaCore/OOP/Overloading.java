package JavaCore.OOP;

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b ;
    }
}

class Parents {
   void show() {
       System.out.println("Parents");
   }

   static void showStatic() {
       System.out.println("Parents static");
   }

   private void showPrivate() {
       System.out.println("Parents private");
   }

   final void showFinal() {
       System.out.println("Parents final");
   }
}

class Child extends Parents {
    @Override
    void show() {
        System.out.println("Child");
    }

    static void showStatic() {
        System.out.println("Child static");
    }
}

public class Overloading {
    public static void main(String[] args) {
        Calculator c = new Calculator();
        System.out.println(c.add(1, 2));
        System.out.println(c.add(1.0, 2.0));

        Parents p = new Child();
        p.show();
        Parents.showStatic();
        Child.showStatic();
    }
}
