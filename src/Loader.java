import java.io.*;
import java.net.URL;

public class Loader {

    private String codeSource;

    public Loader(String url) throws IOException {
        URL site = new URL(url);

        InputStreamReader isr = new InputStreamReader(site.openStream());
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
