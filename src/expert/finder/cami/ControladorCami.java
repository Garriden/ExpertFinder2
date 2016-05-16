package expert.finder.cami;

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

    // Pre:  Cert.
    // Post: S'ha inicialitzat el controlador amb tots els camins passats per parametre. Cada cami de la estructura de
    //       dades té que seguir el seguent format: [cami]|[descripcio]
    // Cost: O(n)
    public ControladorCami(ArrayList<String> camins) throws NullPointerException, IllegalArgumentException {
        this.camins = new ArrayList<>(camins.size());
        for (int i = 0; i < camins.size(); ++i) {
            String cami = camins.get(i);
            if (cami == null) throw new NullPointerException("Error: El cami numero " + i+1 + " no pot tindre un valor nul");
            if (cami.indexOf('|') == -1) throw new IllegalArgumentException("Error: El cami té que seguir un format: [cami]|[descripcio]");
            this.afegir_cami(cami.substring(0, cami.indexOf('|')), cami.substring(cami.indexOf('|')+1, cami.length()));
        }
    }

    // Pre:  Cert.
    // Post: S'ha afegit un nou cami al controlador si C no existia previament. En cas que existeixi el cami retorna una
    //       excepcio.
    // Cost: O(1)
    public void afegir_cami(String cami, String descripcio) throws NullPointerException, IllegalArgumentException {
        if (cami == null) throw new NullPointerException("Error: El cami no pot tindre un valor nul");
        if (descripcio == null) throw new NullPointerException("Error: La descripcio no pot tindre un valor nul");
        if (!Cami.cami_valid(cami)) throw new IllegalArgumentException("Error: No es pot afegir un cami perquè el cami indicat no es valid.");
        Cami c = new Cami(cami, descripcio);
        this.camins.add(c);
    }

    // Pre:  Cert.
    // Post: S'ha eliminat un cami del controlador amb la posicio pasada per paràmetre.
    // Cost: O(1)
    public void eliminar_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida retornada per el controlador");
        this.camins.remove(posicio);
    }

    // Pre:  Cert.
    // Post: S'ha modificat la descripció d'un cami amb la nova descripció pasada per parametre.
    // Cost: O(1)
    public void modificar_descripcio(int posicio, String novaDescripcio) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida retornada per el controlador");
        if (novaDescripcio == null) throw new NullPointerException("Error: La descripcio no pot tindre un valor nul");
        this.camins.get(posicio).set_descripcio(novaDescripcio);
    }

    // Cert: Cert.
    // Post: S'ha modificat el "cami" d'un cami en la posicio pasada per parametre.
    // Cost: O(1)
    public void modificar_cami(int posicio, String cami) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida retornada per el controlador");
        if (cami == null) throw new NullPointerException("Error: El cami no pot tindre un valor nul");
        if (!Cami.cami_valid(cami)) throw new IllegalArgumentException("Error: No es pot afegir un cami perquè el cami indicat no es valid.");
        this.camins.get(posicio).set_cami(cami);
    }

    // Pre:  Cert.
    // Post: Retorna el cami ubicat en la posicio pasada per paràmetre codificat com: [posicio]|[cami]|[descripcio]
    // Cost: O(1)
    public String get_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida retornada per el controlador");
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
        if (posicioCamiOrigen < 0 || posicioCamiOrigen >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio origen té que esta compresa entre el 1 i la mida retornada per el controlador");
        if (posicioCamiDesti < 0 || posicioCamiDesti >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio desti té que esta compresa entre el 1 i la mida retornada per el controlador");
        Cami camiOrigen = this.camins.get(posicioCamiOrigen);
        Cami camiDesti = this.camins.get(posicioCamiDesti);
        return camiOrigen.es_concatenable(camiDesti);
    }

    // Pre:  Cert.
    // Post: Concatena el cami de la posció origen amb el cami de la posició desti. En cas que no es pugui concatenar els dos
    //       camins retorna una excepcio.
    // Cost: O(n)
    public void concatenar_cami(int posicioCamiOrigen, int posicioCamiDesti) throws ArrayIndexOutOfBoundsException, IllegalArgumentException{
        if (posicioCamiOrigen < 0 || posicioCamiOrigen >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio origen té que esta compresa entre el 1 i la mida retornada per el controlador");
        if (posicioCamiDesti < 0 || posicioCamiDesti >= camins.size())
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
        if (posicio < 0 || posicio >= camins.size())
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
