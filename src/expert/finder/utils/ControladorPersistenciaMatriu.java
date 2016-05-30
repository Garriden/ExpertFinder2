package expert.finder.utils;
import java.io.*;

/**
 * @Author Ruben Bagan Benavides.
 */

/**
 * La funcio d'aquesta clase es la de poder importar y exportar matrius com a objecte.
 */
public class ControladorPersistenciaMatriu {
    /**
     * Retorna una referencia a una martiu de clausura enmmagatzemada en la propia arrel del projecte amb el nom
     * igual al nom de la ruta pasada per parametre. El cost d'executar aquesta funcio es: O(1).
     * @param rutaFixer Conte el nom de la matriu de clausura.
     * @return Retorna una referencia a la matriu de clausura.
     * @throws IOException El fitxer on s'enmmagatzema la matriu de clausura no conte una matriu i/o la ruta no es
     * valida.
     */
    public Matriu importar(String rutaFixer) throws IOException {
        FileInputStream fitxer = new FileInputStream(rutaFixer);
        ObjectInputStream ois;
        Matriu m;
        try {
            ois = new ObjectInputStream(fitxer);
            m = (Matriu) ois.readObject();
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut importar la matriu de clausura selvat de la ruta (" + rutaFixer +
                    "( perque o no existeix, o es utilitzat per un altre programa.");
        } catch (ClassNotFoundException e) {
            throw new IOException("Error: El fitxer binari on esta enmmagatzemat la matriu de clausura en la ruta" +
                    " (" + rutaFixer + ") " + "no s'ha pogut obtenir el graf perquè el contingut del fitxer no es un " +
                    "graf.");
        }

        ois.close();
        fitxer.close();
        return m;
    }

    /**
     * Enmmagatzema una instancia de la referencia a una matriu pasada per parametre amb nom igual que el del paramtre.
     * El cost d'executar aquesta funcio es: O(1).
     * @param rutaFitxer Conte el nom de la matriu de clausura.
     * @param m Conte una referencia a la matriu que s'ha d'enmmagatzemar
     * @throws IOException No s'ha pogut exportar perque la ruta no existeix.
     */
    public void exportar(String rutaFitxer, Matriu m) throws IOException {
        FileOutputStream fout = new FileOutputStream(rutaFitxer);
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(fout);
            oos.writeObject(m);
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut exportar la consulta en la ruta (" + rutaFitxer +")"+
                    "perque o no existeix, o es utilitzat per un altre programa.");
        }
        fout.close();
        oos.close();
    }
}
