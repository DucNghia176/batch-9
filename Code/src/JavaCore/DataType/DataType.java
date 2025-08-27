package JavaCore.DataType;

import java.util.Scanner;

public class DataType {
    public static void main(String[] args) {
        //ss kiểu dữ liệu nguyên thủy
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        System.out.println("a == b: " + (a==b));

        //ss kiểu dữ liệu object
        Integer obj1 = Integer.valueOf(5);
        Integer obj2 = Integer.valueOf(5);
        System.out.println("obj1 == obj2: " + (obj1 == obj2));
        System.out.println("obj1.equals(obj2): " + obj1.equals(obj2));

        //ss nguyên thủy và object
        Integer obj3 = 5;
        int c = 5;
        System.out.println("obj3 == c: " + (obj3 == c));
    }
}