package expert.finder.cami;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides
 */

public class ControladorCami {
    private ArrayList<CamiClausura> camins;

    // Pre:  Cert.
    // Post: Inicialitza el controlador sense cap cami.
    // Cost: O(1)
    public ControladorCami() {
        this.camins = new ArrayList<>();
    }

    // Pre:  rutaFitxer != null, la ruta ja conte el fitxer de la base de dades al que apunta.
    // Post: S'han afegit tots els camins de la base de dades ubicada en la ruta passada per parametre al controlador.
    // Cost: O(n)
    public void importar_camins (String rutaFitxer) throws IllegalArgumentException, IOException, ClassNotFoundException {
        ControladorPersistenciaCami controladorPersistenciaCami = new ControladorPersistenciaCami();
        ArrayList<String> caminsCodificats = controladorPersistenciaCami.importar(rutaFitxer);
        
        for (int i = 0; i < caminsCodificats.size(); ++i) {
            String camiCodificat = caminsCodificats.get(i);
            this.camins.add(new CamiClausura(camiCodificat));
        }
    }

    // Pre:  rutaFitxer != null, la ruta ja conte el fitxer de la base de dades al que apunta.
    // Post: S'han exportat tots els camins del controlador al fitxer de bases de dades especificat en la ruta del
    //       fitxer.
    // Cost: Omega(1) ~ O(n)
    public void exportar_camins (String rutaFitxer) throws IOException, IllegalArgumentException {
        ControladorPersistenciaCami controladorPersistenciaCami = new ControladorPersistenciaCami();
        controladorPersistenciaCami.exportar_camins_objecte(rutaFitxer, get_camins());
    }

    // Pre:  Cert.
    // Post: S'ha afegit un nou cami al controlador on el valors dels seus atributs son els parametres.
    // Cost: O(1)
    public void afegir_cami(String cami, String descripcio, boolean teClausura) throws IllegalArgumentException {
        if (cami == null || descripcio == null) {
            throw new IllegalArgumentException("Error: No es pot modificar el cami perquè la nova descripció o el " +
                    "cami tenen valors nul");
        }
        CamiClausura c = new CamiClausura(cami, descripcio, teClausura);
        this.camins.add(c);
    }

    // Pre:  Cert.
    // Post: S'ha eliminat el cami del controlador que es troba en la posicio pasada per parametre.
    // Cost: O(1)
    public void eliminar_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size()) {
            throw new ArrayIndexOutOfBoundsException("Error: L'identificador per obtenir un cami té que estar " +
                    "compresa entre el 1 i la mida de la taula.");
        }
        this.camins.remove(posicio);
    }

    // Pre:  Cert.
    // Post: S'han modificat les propietats d'un cami que poden ser: el cami, la seva descripcio i si utiltiza matriu
    //       de clausura.
    // Cost: O(1)
    public void modificar_cami(int posicio, String nouCami, String novaDescripcio, boolean teClausura, boolean
            recalcularClausura) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
         if (novaDescripcio == null || nouCami == null) {
             throw new IllegalArgumentException("Error: No es pot modificar el cami perquè la nova descripció o el " +
                     "cami tenen valors nul");
         }
        if (posicio < 0 || posicio >= camins.size()) {
            throw new ArrayIndexOutOfBoundsException("Error: L'identificador per obtenir un cami té que estar " +
                    "compresa entre el 1 i la mida de la taula.");
        }
        if (!teClausura && recalcularClausura) {
            throw new IllegalArgumentException("Error: No es pot recalcular la matriu la clausura si el cami no te " +
                    "habilitada aquesta opcio.");
        }
        CamiClausura cami = this.camins.get(posicio);
        cami.set_descripcio(novaDescripcio);
        cami.set_cami(nouCami);
        cami.set_clausura(teClausura);
        if (teClausura) {
            cami.set_actualitzar_clausura(recalcularClausura);
        }
    }

    // Pre:  Cert.
    // Post: Retorna el cami ubicat en la posicio pasada per paràmetre codificat de la següent forma:
    //       [cami]|[descripcio]|[teClausura]|[clausuraActualitzada]
    // Cost: O(1)
    public String get_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size()) {
            throw new ArrayIndexOutOfBoundsException("Error: L'identificador per obtenir un cami té que estar " +
                    "compresa entre el 1 i la mida de la taula.");
        }
        return this.camins.get(posicio).codificar_cami();
    }
    
    // Pre:  Cert.
    // Post: Retorna una llista de tots els camins disponibles del controlador codificats de la següent forma:
    //       [cami]|[descripcio]|[teClausura]|[clausuraActualitzada]
    // Cost: O(n)
    public ArrayList<String> get_camins() {
        ArrayList<String> caminsCodificats = new ArrayList<>(this.camins.size());
        for (CamiClausura camin : this.camins) {
            caminsCodificats.add(camin.codificar_cami());
        }

        return caminsCodificats;
    }

    // Pre: Cert.
    // Post: Retorna cert si existeix un cami en la llista de camins que té el controlador amb la mateixa descripció
    //       que la del parametre.
    // Cost: O(n)
    public boolean existeix_cami(String descripcio) throws IllegalArgumentException {
        if (descripcio == null) {
            throw new IllegalArgumentException("Error: No es pot modificar el cami perquè la nova descripcio té " +
                    "valor nul");
        }
        for (CamiClausura camin : this.camins) {
            if (camin.get_descripcio().equalsIgnoreCase(descripcio)) {
                return true;
            }
        }
        return false;
    }

    // Pre:  Cert
    // Post: Actualitza l'estat de totes las matrius de clausura de tots els camins enmmagatzemats en el controlador.
    // Cost: O(n^2)
    public void graf_node_modificat(char tipusNode) {
        for (CamiClausura camin : this.camins) {
            camin.actualitzar_clausura(tipusNode);
        }
    }
        
    // Pre:  Cert
    // Post: Actualitza l'estat de totes las matrius de clausura de tots els camins enmmagatzemats en el controlador.
    // Cost: O(n)
    public void graf_relacio_modificat() {
        for (CamiClausura camin : this.camins) {
            camin.actualitzar_clausura('P');
        }        
    }
    
    // Pre:  Cert
    // Post: Retorna una referencia a un cami clausura en la posicio pasada per parametre.
    // Cert: O(1)
    public CamiClausura get_cami_sense_codificar (int posicio) {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        return this.camins.get(posicio);
    }
}
