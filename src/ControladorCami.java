import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides on 19/04/2016.
 */

public class ControladorCami {
    private ArrayList<Cami> camins;

    public ControladorCami() {
        this.camins = new ArrayList<>();
    }

    // Pre:  descripcio != null;
    // Post: Retorna si existeix, la primera ocurrencia d'un cami amb aquesta descripcio, en cas contrari retorna null.
    // Cost: O(n)
    public Cami get_cami(String descripcio) {
        for (int i = 0; i < this.camins.size(); ++i) {
            if (this.camins.get(i).getDescripcio().equalsIgnoreCase(descripcio)) {
                return this.camins.get(i);
            }
        }
        return null;
    }

    // Pre:  posicio > 0
    // Post: Retorna un cami, si existeix un cami en la posicio pasada per parametre, en cas contrari retorna null.
    // Cost: O(1)
    public Cami get_cami(int posicio) {
        if (posicio > camins.size()) return null;
        return camins.get(posicio);
    }

    // Pre:  Cert.
    // Post: Retorna una llista de tots els camins disponibles.
    // Cost: O(1)
    public ArrayList<Cami> get_camins() {
        return this.camins;
    }

    // Pre:  c != null
    // Post: S'ha afegit un nou cami al controlador si C no existia previament. En cas contrari retorna 0 si
    //       s'afegit i -1 en cas contrari.
    // Cost: O(n)
    public int  afegir_cami(Cami c) {
        if (!this.camins.contains(c)) {
            this.camins.add(c);
            return 0;
        }
        return -1;
    }

    // Pre:  posicio > 0
    // Post: S'ha eliminat un cami del controlador amb la posicio pasada per paràmetre. Si no existia cap cami en
    //       aquesta posicio retorna -1, en cas contrari retorna 0.
    // Cost: O(1)
    public int eliminar_cami(int posicio) {
        if (posicio >= camins.size()) return -1;
        this.camins.remove(posicio);
        return 0;
    }

    // Pre:  posicio > 0; novaDescripcio != null;
    // Post: S'ha modificat la descripció d'un cami amb la nova descripció pasada per parametre. Si no existia cap cami en
    //       aquesta posicio retorna -1, en cas contrari retorna 0.
    // Cost: O(n)
    public int modificar_descripcio(int posicio, String novaDescripcio) {
        if (posicio >= camins.size()) return -1;
        this.camins.get(posicio).setDescripcio(novaDescripcio);
        return 0;
    }

    // Pre:  posicio > 0; cami != null; cami es un cami valid.
    // Post: S'ha modificat el "cami" d'un cami en la posicio pasada per parametre. Si no existia cap cami en
    //       aquesta posicio retorna -1, en cas contrari retorna 0.
    // Cost: O(n)
    public int modificar_cami(int posicio, String cami) {
        if (posicio >= camins.size()) return -1;
        this.camins.get(posicio).setCami(cami);
        return 0;
    }
}
