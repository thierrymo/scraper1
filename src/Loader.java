import java.io.*;

public class Loader {

    private String codeSource;

    public Loader(String fichierCodeSource) throws IOException {
        InputStream is = new FileInputStream("./"+fichierCodeSource);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buffer = new BufferedReader(isr);

        String line = buffer.readLine();
        StringBuilder builder = new StringBuilder();

        while (line != null){
            builder.append(line).append("\n");
            line = buffer.readLine();
        }

        this.codeSource = builder.toString();
    }

    public String getCodeSource(){
        return codeSource;
    }
}
