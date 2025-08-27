package JavaCore.Memory;

public class Memory {
    int a = 10; // cấp phát tĩnh
    int[] arr = new int[5];// cấp phát động
    static class Student{
      String name;
      Student(String name){
          this.name = name;
      }
    }

    public static void main(String[] args) {
        Memory m = new Memory();
        m.arr[0] = 10; //lưu ở heap
        Student s1 = new Student("Name"); // lưu ở heap
        Student s2 = s1;
    }
}
