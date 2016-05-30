/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.consulta;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Ruben Bagan Benavides
 */

/**
 * La funcio d'aquesta clase es la de permetre importar i exportar consultes com a objecte.
 */
public class ControladorPersistenciaConsulta {
    /**
     * Exporta el conjunt de consultes pasades com a parametre a un objecte anomenat Consultes.av que es crea en la
     * propia arrel del programa.
     * @param consultes Conte un conjunt de consultes. El valor no pot ser nul.
     * @throws IOException
     */
    public void exportar_consulta(ArrayList<Consulta> consultes) throws IOException {
        FileOutputStream fout = new FileOutputStream("Consultes.sav");
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(fout);
            oos.writeObject(consultes);
            fout.close();
            oos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Importa un conjunt de consultes
     * @return
     * @throws IOException
     */
    public ArrayList<Consulta> importar_consulta() throws IOException {
        FileInputStream fitxer = new FileInputStream("Consultes.sav");
        ObjectInputStream ois;
        ArrayList<Consulta> consultes;
        try {
            ois = new ObjectInputStream(fitxer);
            consultes = (ArrayList<Consulta>) ois.readObject();
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut importar el conjunt de consultes enmmagatzemat en la " +
                    "propia arrel del programa amb nom Consultes.sav. Perque o no  existeix, o es utilitzat per un " +
                    "altre programa.");
        } catch (ClassNotFoundException e) {
            throw new IOException("Error: El fitxer binari on estan enmmagatzemades les consultes en l'arrel del " +
                    "programa amb nom Consultes.sav no s'han pogut obtenir perquè el contingut del fitxer no es un " +
                    "conjunt de consultes.");
        }

        ois.close();
        fitxer.close();
        return consultes;
    }


    
}
