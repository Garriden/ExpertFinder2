package expert.finder.cami;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides on 13/05/2016.
 */

public class ControladorPersistenciaCami {
    public ArrayList<String> importar_camins(String rutaFitxer) throws IOException, IllegalArgumentException {
        if (!rutaFitxer.contains(".txt")) throw new IllegalArgumentException("Error: La ruta del fitxer a d'apuntar a un fitxer amb extenció .txt");
        if (rutaFitxer.charAt(rutaFitxer.length()-1) != '\\') rutaFitxer = rutaFitxer + "\\";
        Path ruta = Paths.get(rutaFitxer);
        BufferedReader reader;
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
                        "correcte, te que seguir el seguent format: [camins]|[descripcio][Salt de linea].");
            }
            camins.add(cami);
            cami = reader.readLine();
        }
        reader.close();

        return camins;
    }

    public ArrayList<String> importar_camins_objecte(String rutaFitxer) throws IOException, ClassNotFoundException, IllegalArgumentException{
        if (!rutaFitxer.contains(".sav")) throw new IllegalArgumentException("Error: La ruta del fitxer a d'apuntar a un fitxer amb extenció .sav");
        if (rutaFitxer.charAt(rutaFitxer.length()-1) != '\\') rutaFitxer = rutaFitxer + "\\";
        FileInputStream fitxerObjecte = new FileInputStream(rutaFitxer);
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

    public void exportar_camins_objecte(String rutaFitxer, ArrayList<String> camins) throws IOException, IllegalArgumentException {
        if (!rutaFitxer.contains(".sav")) throw new IllegalArgumentException("Error: La ruta del fitxer a d'apuntar a un fitxer amb extenció .sav");
        if (rutaFitxer.charAt(rutaFitxer.length()-1) != '\\') rutaFitxer = rutaFitxer + "\\";
        FileOutputStream fitxerObjecte = new FileOutputStream(rutaFitxer);
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
