package expert.finder.cami;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides on 13/05/2016.
 */

/**
 * Aquesta clase te la funcio d'importar i exportar camins amb un format: [cami]|[descripcio]|[clausura]|[actualitzada]
 * Els camins importats es llegeixen a partir d'un fitxer guardat en format .txt o .sav i s'exporten amb un fitxer de
 * tipus .sav
 */

public class ControladorPersistenciaCami {
    private ArrayList<String> importar_camins(String rutaFitxer) throws IOException {
        Path ruta = Paths.get(rutaFitxer);
        BufferedReader reader;
        try {
             reader = Files.newBufferedReader(ruta);
        } catch (IOException e) {
            throw new IOException("Error: No es pot importar els camins del fitxer .txt amb els camins perque la ruta" +
                    " indicada(" + rutaFitxer + "): no existeix o no es pot obrir el fitxer");
        }
        ArrayList<String> camins = new ArrayList<>();
        String camiCodificat = reader.readLine();
        if (camiCodificat == null) throw new IOException("Error: La ruta ("+ rutaFitxer+") del fitxer on estan " +
                "enmmagatzemats els camins esta buit");
        while (camiCodificat != null) {
            int posicioDescripcio = camiCodificat.indexOf('|');
            int posicioClausura = camiCodificat.indexOf('|', posicioDescripcio);
            int posicioActualitzada = camiCodificat.indexOf('|', posicioClausura);
            if (posicioDescripcio == -1 || posicioClausura == -1 || posicioActualitzada == -1) {
                throw new IOException("Error: El format d'entrada del fitxer on estan enmmagatzemats els camins no es" +
                        " correcte, te que seguir el seguent format: " +
                        "[Cami]|[Descripcio]|[teClausura]|[clausuraActualitzada][Salt de linea].");
            }
            String cami = camiCodificat.substring(0, posicioDescripcio);
            String descripcio = camiCodificat.substring(posicioDescripcio+1, posicioClausura);
            String clausura = camiCodificat.substring(posicioClausura+1, posicioActualitzada);
            String actualitzada = camiCodificat.substring(posicioActualitzada+1, camiCodificat.length());
            camins.add(cami + descripcio + clausura + actualitzada);
            camiCodificat = reader.readLine();
        }
        reader.close();

        return camins;
    }

    private ArrayList<String> importar_camins_objecte(String rutaFitxer) throws IOException, ClassNotFoundException {
        FileInputStream fitxerObjecte = new FileInputStream(rutaFitxer);
        ObjectInputStream objectInputStream;
        ArrayList<String> camins;
        try {

            objectInputStream = new ObjectInputStream(fitxerObjecte);
            camins = (ArrayList<String>) objectInputStream.readObject();
        }catch (IOException e) {
            throw new IOException("Error: No es pot importar el fitxer .sav amb els camins perque en la ruta indicada("
                    + rutaFitxer + "): no existeix o no es pot obrir el fitxer");
        } catch (ClassNotFoundException e) {
            throw new IOException("Error: El fitxer binari on estan enmmagatzemats els camins en la ruta (" +
                    rutaFitxer +") no s'han pogut obtenir els camins perquè el contingut del fitxer no hi ha camins.");
        }

        fitxerObjecte.close();
        objectInputStream.close();
        return camins;
    }

    /**
     * Exporta el contingut del vector de camins codificats passat per parametre els cuals segueixen el seguent format:
     * [Cami]|[Descripcio]|[Clausura]|[Actualitzada]. El nom del fitxer desti sera amb extencio .sav el cual esta
     * inclos en la propia ruta del fitxer pasada per parametre.
     * @param rutaFitxer conte una ruta absoluta del sistema la cual conte tambe el nom del fitxer amb extencio .sav;
     *                   te que tindre valor != null.
     * @param camins conte una llista dels camins codificats; te que tindre valor != null
     * @throws IOException No es pot importa el fitxer .sav
     * @throws IllegalArgumentException La ruta del fitxer no acaba amb extencio .sav
     */
    public void exportar_camins_objecte(String rutaFitxer, ArrayList<String> camins) throws IOException, IllegalArgumentException {
        if (!rutaFitxer.contains(".sav")) {
            throw new IllegalArgumentException("Error: La ruta del fitxer te que ser absoluta i a mes el fitxer on " +
                    "apunta te que ser amb extencio .sav");
        }
        FileOutputStream fitxerObjecte = new FileOutputStream(rutaFitxer);
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(fitxerObjecte);
            objectOutputStream.writeObject(camins);
        }catch (IOException e) {
            throw new IOException("Error: No es pot importar el fitxer .sav amb els camins perque en la ruta indicada("
                    + rutaFitxer + "): no existeix o no es pot obrir el fitxer");
        }
        fitxerObjecte.close();
        objectOutputStream.close();
    }

    /**
     * Retorna una llista de camins codificats amb el format: [Cami]|[Descripcio]|[Clausura]|[Actualitzada]. Els
     * camins son llegits del fitxer que esta inclos en la ruta passada per parametre. Aquest fitxer te que tindre
     * extencio .sav o .txt.
     * @param rutaFitxer conte una ruta absoluta del sistema la cual conte tambe el nom del fitxer amb extencio .sav
     *                   o .txt; te que tindre valor != null.
     * @return Retorna una llista de camins codificats amb el format: [Cami]|[Descripcio]|[Clausura]|[Actualitzada]
     * @throws IOException No es pot obrir el fitxer o el contingut del fitxer no es valid.
     * @throws ClassNotFoundException El fitxer no te extencio .txt o .sav o la ruta te valor nul.
     */
    public ArrayList<String> importar(String rutaFitxer) throws IOException, ClassNotFoundException {
        if (rutaFitxer == null) throw new IllegalArgumentException("Error Importar: La ruta del fitxer no pot tenir " +
                "un valor nul");
        ArrayList<String> camins;
        if (rutaFitxer.contains(".txt")) camins = importar_camins(rutaFitxer);
        else if (rutaFitxer.contains(".sav")) camins = importar_camins_objecte(rutaFitxer);
        else throw new IllegalArgumentException("Error: La ruta del fitxer te que ser absoluta i a mes el fitxer on " +
                    "apunta te que ser amb extencio .txt o .sav");
        return camins;
    }        


}
