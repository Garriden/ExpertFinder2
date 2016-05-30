package expert.finder.utils;

import expert.finder.graf.Graf;

import java.io.*;

/**
 * @Author Ruben Bagan Benavides.
 */

/**
 *
 */
public class ControladorPersistenciaMatriu {
    /**
     * Retorna una referencia a una martiu de clausura enmmagatzemada en la ruta pasada com a paramtre.
     * @param rutaFixer Conte el nom de la matriu de clausura.
     * @return Retorna una referencia a la matriu de clausura.
     * @throws IOException El fitxer on s'enmmagatzema la matriu de clausura no conte una matriu i/o la ruta noe s
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
     *
     * @param rutaFitxer
     * @param m
     * @throws IOException
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
