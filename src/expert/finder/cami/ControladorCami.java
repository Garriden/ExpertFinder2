package expert.finder.cami;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides on 19/04/2016.
 */

public class ControladorCami {
    private ArrayList<Cami> camins;

    // Pre:  Cert.
    // Post: Inicialitzar el controlador.
    // Cost: O(1)
    public ControladorCami() {
        this.camins = new ArrayList<>();
    }

    // Pre:  rutaFitxer != null, la ruta ja conte el fitxer de la base de dades al que apunta.
    // Post: S'ha inicialitzat el controlador o afegit tots els camins de la base de dades ubicada en la ruta passada per parametre. Cada cami de la estructura de
    //       dades té que seguir el seguent format: [cami]|[descripcio]
    // Cost: O(n)
    public void importar_camins (String rutaFitxer) throws IllegalArgumentException, IOException, ClassNotFoundException {
        ControladorPersistenciaCami controladorPersistenciaCami = new ControladorPersistenciaCami();
        ArrayList<String> camins;
        if (rutaFitxer.contains(".sav")) camins = controladorPersistenciaCami.importar_camins_objecte(rutaFitxer);
        else if (rutaFitxer.contains(".txt")) camins = controladorPersistenciaCami.importar_camins(rutaFitxer);
        else throw new IllegalArgumentException("Error: El fitxer te que tindre extencio .txt o .sav");
        for (int i = 0; i < camins.size(); ++i) {
            String cami = camins.get(i);
            try {
                this.afegir_cami(cami.substring(0, cami.indexOf('|')), cami.substring(cami.indexOf('|')+1, cami.length()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Error: Algun/s dels camins que hi ha en el fitxer que vols importar no son valids");
            }
        }
    }

    // Pre:  rutaFitxer != null, la ruta ja conte el fitxer de la base de dades al que apunta.
    // Post: S'ha exportat tots els camins del controlador al fitxer de bases de dades especificat en la ruta del fitxer.
    // Cost: O(n)
    public void exportar_camins (String rutaFitxer) throws IOException, IllegalArgumentException {
        ControladorPersistenciaCami controladorPersistenciaCami = new ControladorPersistenciaCami();
        controladorPersistenciaCami.exportar_camins_objecte(rutaFitxer, get_camins());
    }

    // Pre:  cami != null; descripcio != null
    // Post: S'ha afegit un nou cami al controlador si C no existia previament. En cas que existeixi el cami retorna una
    //       excepcio.
    // Cost: O(1)
    public void afegir_cami(String cami, String descripcio) throws IllegalArgumentException {
        if (!Cami.cami_valid(cami)) throw new IllegalArgumentException("Error: No es pot afegir el cami perquè el cami indicat no es valid.");
        Cami c = new Cami(cami, descripcio);
        this.camins.add(c);
    }

    // Pre:  Cert.
    // Post: S'ha eliminat un cami del controlador amb la posicio pasada per paràmetre.
    // Cost: O(1)
    public void eliminar_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        this.camins.remove(posicio);
    }

    // Pre:  novaDescripcio != null.
    // Post: S'ha modificat la descripció d'un cami amb la nova descripció pasada per parametre.
    // Cost: O(1)
    public void modificar_descripcio(int posicio, String novaDescripcio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        this.camins.get(posicio).set_descripcio(novaDescripcio);
    }

    // Cert: cami != null.
    // Post: S'ha modificat el "cami" d'un cami en la posicio pasada per parametre.
    // Cost: O(1)
    public void modificar_cami(int posicio, String cami) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        if (!Cami.cami_valid(cami)) throw new IllegalArgumentException("Error: No es pot afegir un cami perquè el cami indicat no es valid.");
        this.camins.get(posicio).set_cami(cami);
    }

    // Pre:  Cert.
    // Post: Retorna el cami ubicat en la posicio pasada per paràmetre codificat com: [posicio]|[cami]|[descripcio]
    // Cost: O(1)
    public String get_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        Cami c = this.camins.get(posicio);
        return (c.get_cami() + "|" + c.get_descripcio());
    }

    // Pre:  Cert.
    // Post: Retorna una llista de tots els camins disponibles codificats com: [posicio]|[cami]|[descripcio]
    // Cost: O(n)
    public ArrayList<String> get_camins() {
        ArrayList<String> caminsCodificats = new ArrayList<>(this.camins.size());
        for (int i = 0; i < this.camins.size(); ++i) {
            Cami c = this.camins.get(i);
            caminsCodificats.add(c.get_cami() + "|" + c.get_descripcio());
        }

        return caminsCodificats;
    }

    // Pre:  Cert.
    // Post: Retorna un boolea que ens indica si el cami origen es concatenable o no amb el cami desti.
    // Cost: O(1)
    public boolean es_concatenable(int posicioCamiOrigen, int posicioCamiDesti) throws ArrayIndexOutOfBoundsException {
        if (posicioCamiOrigen < 0 || posicioCamiOrigen >= this.camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio origen té que esta compresa entre el 1 i la mida de la taula");
        if (posicioCamiDesti < 0 || posicioCamiDesti >= this.camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio desti té que esta compresa entre el 1 i la mida de la taula");
        Cami camiOrigen = this.camins.get(posicioCamiOrigen);
        Cami camiDesti = this.camins.get(posicioCamiDesti);
        return camiOrigen.es_concatenable(camiDesti);
    }

    // Pre:  Cert.
    // Post: Concatena el cami de la posció origen amb el cami de la posició desti. En cas que no es pugui concatenar els dos
    //       camins retorna una excepcio.
    // Cost: O(n)
    public void concatenar_cami(int posicioCamiOrigen, int posicioCamiDesti) throws ArrayIndexOutOfBoundsException, IllegalArgumentException{
        if (posicioCamiOrigen < 0 || posicioCamiOrigen >= this.camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio origen té que esta compresa entre el 1 i la mida retornada per el controlador");
        if (posicioCamiDesti < 0 || posicioCamiDesti >= this.camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio desti té que esta compresa entre el 1 i la mida retornada per el controlador");
        if (!es_concatenable(posicioCamiOrigen, posicioCamiDesti)) throw new IllegalArgumentException("Error: Aquests dos camins son incompatibles per concatenar.");
        Cami camiOrigen = this.camins.get(posicioCamiOrigen);
        Cami camiDesti = this.camins.get(posicioCamiDesti);
        Cami c = camiOrigen.concatenar(camiDesti);
        this.camins.add(c);
    }

    // Pre:  Cert
    // Post: Inverteix el cami en la posicio pasada per paràmetre.
    // Cost: O(n)
    public void invertir_cami(int posicio)  throws ArrayIndexOutOfBoundsException  {
        if (posicio < 0 || posicio >= this.camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida retornada per el controlador");
        Cami c = this.camins.get(posicio).invertir();
        this.camins.add(c);
    }

    // Pre:  Cert
    // Post: Retorna un boolea que ens indica si el cami pasat per paràmetre vàlid o no.
    // Cost: O(1).
    public boolean validar_cami(String cami) {
        return Cami.cami_valid(cami);
    }

    // Pre:  Cert
    // Post: Retorna el nobmre de camins enmmagatzemats en el controlador.
    // Cost: O(1)
    public int get_nombre_camins() {
        return this.camins.size();
    }
}
