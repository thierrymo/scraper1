import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Loader loader=new Loader("https://www.allocine.fr/seance/salle_gen_csalle=C0146.html");
        System.out.println(loader.getCodeSource());




    }
}
