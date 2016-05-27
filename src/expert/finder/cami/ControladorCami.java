package expert.finder.cami;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides on 19/04/2016.
 */

public class ControladorCami {
    private ArrayList<CamiClausura> camins;

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
        ArrayList<String> camins = controladorPersistenciaCami.importar(rutaFitxer);
        
        for (int i = 0; i < camins.size(); ++i) {
            String camiCodificat = camins.get(i);
            
            int posicioDescripcio = camiCodificat.indexOf('|');
            int posicioClausura = camiCodificat.indexOf('|', posicioDescripcio);
            int posicioActualitzada = camiCodificat.indexOf('|', posicioClausura);
            
            String cami = camiCodificat.substring(0, posicioDescripcio);
            String descripcio = camiCodificat.substring(posicioDescripcio+1, posicioClausura);
            boolean clausura = false;
            if(camiCodificat.substring(posicioClausura+1, posicioActualitzada).equalsIgnoreCase("Si")) {
                clausura = true;
            }
            boolean actualitzada = false;
            if (camiCodificat.substring(posicioActualitzada+1, camiCodificat.length()).equalsIgnoreCase("Si")) {
                actualitzada = true;
            }
            
            try {
                this.afegir_cami(cami, descripcio, clausura, actualitzada);
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

    // Pre:  Cert.
    // Post: S'ha afegit un nou cami al controlador on el valors dels seus atributs son els parametres.
    // Cost: O(1)
    public void afegir_cami(String cami, String descripcio, boolean teClausura, boolean clausuraDesactualitzada) throws IllegalArgumentException {
        if (cami == null || descripcio == null)
            throw new IllegalArgumentException("Error: No es pot afegir un cami perquè la descripció i/o el camí te valor nul");
        if (!Cami.cami_valid(cami)) throw new IllegalArgumentException("Error: No es pot afegir el cami perquè el cami indicat no es valid.");
        CamiClausura c = new CamiClausura(cami, descripcio, teClausura, clausuraDesactualitzada);
        this.camins.add(c);
    }

    // Pre:  Cert.
    // Post: S'ha eliminat el cami del controlador que es troba en la posicio pasada per parametre.
    // Cost: O(1)
    public void eliminar_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        this.camins.remove(posicio);
    }

    // Pre:  Cert.
    // Post: S'ha modificat la descripció d'un cami amb la nova descripció pasada per parametre, l'estat de la matriu de clausura i si utilitza o no matriu de clausura.
    // Cost: O(1)
    public void modificar_cami(int posicio, String novaDescripcio, boolean teClausura, boolean clausuraDesactualitzada) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
         if (novaDescripcio == null)
            throw new IllegalArgumentException("Error: No es pot modificar el camó perquè la nova descripció té valor nul");
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        CamiClausura cami = this.camins.get(posicio);        
        cami.set_descripcio(novaDescripcio);
        cami.set_clausura(teClausura);
        cami.set_actualitzar_clausura(clausuraDesactualitzada);
    }

    // Pre:  Cert.
    // Post: Retorna el cami ubicat en la posicio pasada per paràmetre codificat com: [cami]|[descripcio]|[teClausura]|[clausuraActualitzada]
    // Cost: O(1)
    public String get_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        CamiClausura c = this.camins.get(posicio);
        String camiCodificat = c.get_cami() + "|" + c.get_descripcio() + "|";
        if (c.te_clausura()) camiCodificat += "Si|";
        else camiCodificat += "No|";
        if (c.clausura_desactualitzada()) camiCodificat += "Si";
        else camiCodificat += "No";        
        return camiCodificat;
    }
    
    // Pre:  Cert.
    // Post: Retorna una llista de tots els camins disponibles codificats com: [cami]|[descripcio]|[teClausura]|[clausuraActualitzada]
    // Cost: O(n)
    public ArrayList<String> get_camins() {
        ArrayList<String> caminsCodificats = new ArrayList<>(this.camins.size());
        for (int i = 0; i < this.camins.size(); ++i) {
            CamiClausura c = this.camins.get(i);
            String camiCodificat = c.get_cami() + "|" + c.get_descripcio() + "|";
            if (c.te_clausura()) camiCodificat += "Si|";
            else camiCodificat += "No|";
            if (c.clausura_desactualitzada()) camiCodificat += "Si";
            else camiCodificat += "No";  
            caminsCodificats.add(camiCodificat);
        }

        return caminsCodificats;
    }
    
    // Pre:  Cert
    // Post: Actualitza l'estat de totes las matrius de clausura dels camins.
    // Cost: O(n^2)
    public void graf_node_modificat(char tipusNode) {
        for (int i = 0; i < camins.size(); ++i) {
            camins.get(i).actualitzar_clausura(tipusNode);
        }
    }
        
    // Pre:  Cert
    // Post: Actualitza l'estat de totes las matrius de clausura dels camins com a desactualitzada.
    // Cost: O(n)
    public void graf_relacio_modificat() {
        for (int i = 0; i < camins.size(); ++i) {
            camins.get(i).actualitzar_clausura('P');
        }        
    }
    

    /*
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
    */

    /*
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
    */
    
    // Pre:  Cert
    // Post: Retorna una referencia a un cami clausura en la posicio pasada per parametre.
    // Cert: O(1)
    public CamiClausura get_cami_sense_codificar (int posicio) {
        if (posicio < 0 || posicio >= camins.size())
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        return this.camins.get(posicio);
    }
}
