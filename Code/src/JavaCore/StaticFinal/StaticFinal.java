package JavaCore.StaticFinal;

class Static {
    static int count = 0;

    static void sayHello() {
        System.out.println("Hello");
    }
}

class Final {
    final int count = 0;

    final StringBuilder s = new StringBuilder("Hello");


}
public class StaticFinal {
    public static void main(String[] args) {
        System.out.println( Static.count);

        //gọi phương thức mà ko cần tạo đối tượng
        Static.sayHello();

        //Tạo đối tượng nhưng phương thức vẫn dùng chung
        Static c1 = new Static();
        Static c2 = new Static();
        c1.count = 10;
        System.out.println(c2.count);

        //Final
        Final f = new Final();
//        f.count=1; Lỗi ko thể gán
        System.out.println(f.count);
        f.s.append(" World");//Không thể khởi tạo lại nhưng có thể thay đổi gtri
        System.out.println(f.s);
    }
}
