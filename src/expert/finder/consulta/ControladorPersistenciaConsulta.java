/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.consulta;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Ruben Bagan Benavides
 */
public class ControladorPersistenciaConsulta {
    public void exportar_consulta(String nomFitxer, Consulta consulta) throws IOException {
        FileOutputStream fout = new FileOutputStream(nomFitxer);
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(fout);
            oos.writeObject(consulta);
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut exportar la consulta en la ruta (" + nomFitxer +")"+
                    "perque o no existeix, o es utilitzat per un altre programa.");
        }
        fout.close();
        oos.close();
    }
    
}
