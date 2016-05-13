package Cami;

import Cami.Cami;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides on 13/05/2016.
 */

public class ControladorIECami {
    public ArrayList<Cami> importar_camins(String rutaFitxer) throws IOException {
        ArrayList<Cami> camins = new ArrayList<>();
        Path ruta = Paths.get(rutaFitxer);
        BufferedReader reader = null;
        try {
             reader = Files.newBufferedReader(ruta);
        } catch (IOException e) {
            throw new IOException("Error: No es poden importar/exportar camins perque en la ruta indicada no existeix o" +
                    " no es pot obrir el fitxer: " + rutaFitxer);
        }

        String cami = reader.readLine();
        if (cami == null) {
            throw new IOException("Error: La ruta del fitxer on estan enmmagatzemats els camins esta buit: " + rutaFitxer);
        }
        while (cami != null) {
            int posicioTab = cami.indexOf("|");
            if (posicioTab == -1) {
                throw new IOException("Error: El format d'entrada del fitxer on estan enmmagatzemats els camins no es " +
                        "correcte [camins]|[descripcio].");
            }
            Cami c = new Cami((cami.substring(posicioTab+1,cami.length())),(cami.substring(0, posicioTab)));
            camins.add(c);
            cami = reader.readLine();
        }

        reader.close();


        return camins;
    }

    public void exportar_camins(String rutaFitxer, ArrayList<Cami> camins) throws IOException {
        try {
            Path ruta = Paths.get(rutaFitxer);
            BufferedWriter writer = Files.newBufferedWriter(ruta);
            for (int i = 0; i < camins.size(); ++i) {
                Cami c = camins.get(i);
                writer.write(c.getCami());
                writer.write("|");
                writer.write(c.getDescripcio());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new IOException("Error: No es poden importar/exportar camins perque en la ruta indicada no existeix o" +
                    " no es pot obrir el fitxer: " + rutaFitxer);
        }
    }
}
