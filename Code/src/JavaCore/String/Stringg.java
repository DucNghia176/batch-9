package JavaCore.String;

import java.util.Scanner;

public class Stringg {
    public static void main(String[] args) {
        //tạo
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = new String("Hello");

        //ss
        System.out.println("s1 == s2: " + (s1 == s2)); //cùng pool
        System.out.println("s1 == s3: " + (s1 == s3)); //khác object
        System.out.println("s1.equals(s2): " + s1.equals(s2)); //cùng nd
        System.out.println("s1.equals(s3): " + s1.equals(s3)); //cùng nd

        //bất biến
        String s4 = s1.concat(" World");
        System.out.println(s4);

        //ss
        //== so sánh địa chỉ
        System.out.println("s1 == s2: " + (s1 == s2));
        System.out.println("s1 == s3: " + (s1 == s3));
        //equals so sánh gtri
        System.out.println("s1.equals(s2): " + s1.equals(s2));
        System.out.println("s1.equals(s3): " + s1.equals(s3));
    }
}
