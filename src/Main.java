import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ruben Bagan Benavides on 07/05/2016.
 */
public class Main {
    public static void main(String[] args) {
        Path ruta = Paths.get("camins.txt");
        try {
            BufferedReader reader = Files.newBufferedReader(ruta);
            reader.read();
            int i = 0;
            while (i < 5) {
                reader.read();
                ++i;
            }
        } catch (IOException e) {
            System.out.println("NO HAY NINGUN PUTO FICHERO GG");
        }

    }
}
