package JavaCore.Excecption;

import java.lang.Exception;

public class Throw {
    public void error()throws Exception{
        throw new Exception("Error");
    }

    public static void main(String[] args) {
        Throw t = new Throw();
        try {
            t.error();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
