package JavaCore.Excecption;

import java.io.IOException;

public class Checked {
    public void readFile() throws IOException {
        throw new IOException("Error");
    }
    public static void main(String[] args) {
        Checked c = new Checked();
        try {
            c.readFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());        }
    }
}
