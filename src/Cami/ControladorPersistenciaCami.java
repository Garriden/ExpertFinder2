package Cami;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides on 13/05/2016.
 */

public class ControladorPersistenciaCami {
    public ArrayList<String> importar_camins(String rutaFitxer) throws IOException {
        Path ruta = Paths.get(rutaFitxer);
        BufferedReader reader = null;
        try {
             reader = Files.newBufferedReader(ruta);
        } catch (IOException e) {
            throw new IOException("Error: No es poden importar/exportar camins perque en la ruta indicada no existeix o" +
                    " no es pot obrir el fitxer: " + rutaFitxer);
        }
        ArrayList<String> camins = new ArrayList<>();
        String cami = reader.readLine();
        if (cami == null) throw new IOException("Error: La ruta del fitxer on estan enmmagatzemats els camins esta buit: " + rutaFitxer);
        while (cami != null) {
            int posicioTab = cami.indexOf("|");
            if (posicioTab == -1) {
                throw new IOException("Error: El format d'entrada del fitxer on estan enmmagatzemats els camins no es " +
                        "correcte [camins]|[descripcio].");
            }
            camins.add(cami);
            cami = reader.readLine();
        }
        reader.close();

        return camins;
    }

    public ArrayList<String> importar_camins_objecte(String rutaFitxer) throws IOException, ClassNotFoundException{
        FileInputStream fitxerObjecte = new FileInputStream(rutaFitxer + "camins.sav");
        ObjectInputStream objectInputStream;
        ArrayList<String> camins;
        try {

            objectInputStream = new ObjectInputStream(fitxerObjecte);
            camins = (ArrayList<String>) objectInputStream.readObject();
        }catch (IOException e) {
            throw new IOException("Error: No s'ha pogut exportar els camins a la ruta: " + rutaFitxer + "camins.sav pot ser: " +
                    "perque o no tens permisos d'administrador, un altre programa esta utilitzat aquest fitxer o la ruta no existeix.");
        } catch (ClassNotFoundException e) {
            throw new IOException("Error: El fitxer binari on estan enmmagatzemats els camins en la ruta: " + rutaFitxer + "camins.sav " +
                    "no s'han pogut obtenir els camins perquè el contingut del fitxer no hi ha camins.");
        }

        fitxerObjecte.close();
        objectInputStream.close();
        return camins;
    }

    public void exportar_camins_objecte(String rutaFitxer, ArrayList<String> camins) throws IOException {
        FileOutputStream fitxerObjecte = new FileOutputStream(rutaFitxer+"camins.sav");
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(fitxerObjecte);
            objectOutputStream.writeObject(camins);
        }catch (IOException e) {
            throw new IOException("Error: No s'ha pogut exportar els camins a la ruta: " + rutaFitxer + "camins.sav pot ser: " +
                    "perque o no tens permisos d'administrador, un altre programa esta utilitzat aquest fitxer o la ruta no existeix.");
        }
        fitxerObjecte.close();
        objectOutputStream.close();
    }
}
