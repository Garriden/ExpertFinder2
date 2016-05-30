package expert.finder.cami;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Ruben Bagan Benavides
 */

/**
 * La funció d'aquesta classe es comunicar-se amb el controlador de persistencia de camins, subministrar l'informacio
 * necessaria al controlador grafic i es l'unica que genera instancies de cami.
 */
public class ControladorCami {
    private ArrayList<CamiClausura> camins;

    /**
     * Inicialitza el controlador de camins amb una llista de camins buit. El cost d'executar aquesta funcio es: O(1)
     */
    public ControladorCami() {
        this.camins = new ArrayList<>();
    }

    /**
     * Afageix o inicialitza la llista de camins amb nous camins al controlador a partir d'un fitxer que actua com a
     * base de dades. El nom del fitxer esta inclos en la ruta pasada com a parametre. Realitza una comprovacio de la
     * validesa per a cada cami que s'importa. El cost d'executar aquesta funcio es: O(n) on n es el nombre
     * d'entrades que te el fitxer. Els camins que importara segueixen el seguent format:
     * [cami]|[descripcio]|[clausura]|[actualitzada]
     * @param rutaFitxer Conte la ruta absoluta inclos en el nom del fitxer que pot ser tant de tipus .sav com .txt
     *                   no estan enmmagatzemats tots els camins codificats.
     * @throws IllegalArgumentException Retorna un error de gestio que prove de la capa de persistencia. Mirar el
     * importar camins.
     * @throws IOException Retorna un error de gestio que prove de la capa de persistencia. Mirar el importar camins.
     * @throws ClassNotFoundException Retorna un error de gestio que prove de la capa de persistencia. Mirar el
     * importar camins.
     */
    public void importar_camins (String rutaFitxer) throws IllegalArgumentException, IOException, ClassNotFoundException {
        ControladorPersistenciaCami controladorPersistenciaCami = new ControladorPersistenciaCami();
        ArrayList<String> caminsCodificats = controladorPersistenciaCami.importar(rutaFitxer);
        
        for (int i = 0; i < caminsCodificats.size(); ++i) {
            String camiCodificat = caminsCodificats.get(i);
            this.camins.add(new CamiClausura(camiCodificat));
        }
    }

    /**
     * Exporta el contingut d'aquest controlador en un fitxer amb extensio .sav que actuara com a base de dades de
     * camins i s'enmmagatzemara a la ruta absoluta passada per parametre amb el nom del fitxer especificat dins de
     * la ruta. Els camins enmmagatzemats estaran codificats  amb el format seguent:
     * [cami]|[descripcio]|[clausura]|[actualitzada] El cost d'executar aquesta funcio es: O(1)
     * @param rutaFitxer Conte la ruta absoluta del directori amb el nom del fitxer amb extensio .sav
     * @throws IOException Retorna un error de gestio que prove de la capa de persistencia. Mirar el exportar camins.
     * @throws IllegalArgumentException Retorna un error de gestio que prove de la capa de persistencia. Mirar el
     * exportar camins.
     */
    public void exportar_camins (String rutaFitxer) throws IOException, IllegalArgumentException {
        ControladorPersistenciaCami controladorPersistenciaCami = new ControladorPersistenciaCami();
        controladorPersistenciaCami.exportar_camins_objecte(rutaFitxer, get_camins());
    }

    /**
     * Afegiex un cami al controlador amb els atributs inicialitzats amb els valors passats per parametre. Realitza
     * una comprovacio de la validesa del cami. El cost d'executar aquesta funcio es: O(n) on n es el nombre de
     * caracters que te un cami.
     * @param cami es un string que conte la navegabilitat entre diferents nodes del graf. cami no apunta a una
     *             referencia nul-la.
     * @param descripcio proporciona una descripcio de quina es la navegabilitat del cami. descripcio no apunta a una
     *                   referencia nul-la
     * @param teClausura indica si el cami tindra una matriu de clausura associada.
     * @throws IllegalArgumentException El cami passat per parametre que intentes afegir no es cami valid.
     */
    public void afegir_cami(String cami, String descripcio, boolean teClausura) throws IllegalArgumentException {
        if (cami == null || descripcio == null) {
            throw new IllegalArgumentException("Error: No es pot modificar el cami perquè la nova descripció o el " +
                    "cami tenen valors nul");
        }
        CamiClausura c = new CamiClausura(cami, descripcio, teClausura);
        this.camins.add(c);
    }

    /**
     * S'elimina el cami que es troba en la posicio passada per parametre dins de la llista de camins del controlador
     * El cost d'executar aquesta funcio es: O(1)
     * @param posicio conte la posicio de la llista del cami a eliminar.
     * @throws ArrayIndexOutOfBoundsException L'identificador per obtenir un cami té que estar compresa entre el 1 i
     * la mida de la taula.
     */
    public void eliminar_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size()) {
            throw new ArrayIndexOutOfBoundsException("Error: L'identificador per obtenir un cami té que estar " +
                    "compresa entre el 1 i la mida de la taula.");
        }
        this.camins.remove(posicio);
    }

    /**
     * Modifica el atributs del cami que es troba en la posicio pasada per parametre dins de la llista de camins del
     * controlador. Modifica els valors encara que siguin iguals que els originals. Realitza una comprovacio de la
     * validesa del cami abans de començar a modificar els atributs. El cost d'executar aquesta funcio es: O(n) on n
     * es el nombre de caracters que te el nou cami.
     * @param posicio conte la posicio de la llista del cami a eliminar.
     * @param nouCami es un string que conte la navegabilitat entre diferents nodes del graf. cami no apunta a una
     *                referencia nul-la.
     * @param novaDescripcio proporciona una descripcio de quina es la navegabilitat del cami. descripcio no apunta a una
     *                       referencia nul-la
     * @param teClausura indica si el cami tindra una matriu de clausura associada.
     * @param recalcularClausura indica si fa falta recalcular la matriu de clausura.
     * @throws IllegalArgumentException Errors en la validesa del cami, els valors passats contenen valors nuls o no
     * pot activar recalcular la matriu de clausura si aquest cami no te.
     * @throws ArrayIndexOutOfBoundsException L'identificador per obtenir un cami té que estar compresa entre el 1 i
     * la mida de la taula.
     */
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
        cami.set_cami(nouCami);
        cami.set_descripcio(novaDescripcio);
        cami.set_clausura(teClausura);
        if (teClausura) {
            cami.set_actualitzar_clausura(recalcularClausura);
        }
    }

    /**
     * Obte un cami codificat a partir del cami que es troba en la posicio passada per parametre dins de la llista de
     * camins del controlador. El cami codificat segueix el seguent format: [Cami]|[Descripcio]|[Clausura]|[Actualitzada]
     * El cost d'executar aquesta funcio es: O(1).
     * @param posicio conte la posicio de la llista del cami a eliminar.
     * @return retorna una string amb el contingut del cami codificat en la posicio pasada per parametre.
     * @throws ArrayIndexOutOfBoundsException L'identificador per obtenir un cami té que estar compresa entre el 1 i
     * la mida de la taula.
     */
    public String get_cami(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size()) {
            throw new ArrayIndexOutOfBoundsException("Error: L'identificador per obtenir un cami té que estar " +
                    "compresa entre el 1 i la mida de la taula.");
        }
        return this.camins.get(posicio).codificar_cami();
    }


    /**
     * Retorna una llista de tots els camins que te el controlador codificats amb el seguent format:
     * [Cami]|[Descripcio]|[Clausura]|[Actualitzada]. El cost d'executar aquesta funcio es: O(n) on n es el nombre de
     * camins que te el controlador.
     * @return Retorna una llista de tots els camins que te el controlador codificats.
     */
    public ArrayList<String> get_camins() {
        ArrayList<String> caminsCodificats = new ArrayList<>(this.camins.size());
        for (CamiClausura camin : this.camins) {
            caminsCodificats.add(camin.codificar_cami());
        }

        return caminsCodificats;
    }

    /**
     * Retorna el nombre de camins que te el controlador.
     * @return Retorna el nombre de camins que te el controlador.
     */
    public int get_mida() {
        return this.camins.size();
    }

    /**
     * Retorna una boolea que indica si hi ha algun cami en el controlador on la seva descripcio es igual a la
     * descripcio passada per parametre. El cost d'executar aquesta funcio es: O(n) on n es el nombre de camins que
     * te el controlador.
     * @param descripcio proporciona una descripcio de quina es la navegabilitat del cami. descripcio no apunta a una
     *                   referencia nul-la
     * @return Retorna cert si existeix un cami en el controlador amb una descripcio igual a la descripcio del
     * parametre, en cas contrari retorna false.
     * @throws IllegalArgumentException Si la descripcio te valor nul
     */
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

    /**
     * Si en el graf s'ha eliminat un node de qualsevol tipus llavors al cridar aquesta funcio modifica l'estat del
     * boolea de l'us de la matriu de clausura de tots els camins que hi ha en el controlador indicant si estan
     * actualitzades o no. El cost d'executar aquesta funcio es: O(n^2) on n es el nombre de camins que te el controlador.
     * @param tipusNode El tipus de node te que ser una lletra capital dels nodes existents en el graf, ja sigui: P,
     *                  T,C o A.
     */
    public void graf_node_modificat(char tipusNode) {
        for (CamiClausura camin : this.camins) {
            camin.actualitzar_clausura(tipusNode);
        }
    }

    /**
     * Si en el graf s'ha eliminat una relacio qualsevol de les tres matrius d'adjacencia llavors al cridar aquesta
     * funcio modifica l'estat del boolea de l'us de la matriu de clausura de tots els camins que hi ha en el
     * controlador indicant si estan actualitzades o no. El cost d'executar aquesta funcio es: O(n) on n es
     * el nombre de camins que te el controlador.
     */
    public void graf_relacio_modificat() {
        for (CamiClausura camin : this.camins) {
            camin.actualitzar_clausura('P');
        }        
    }

    /**
     * Retorna una referencia d'un cami sense codificar a partir del cami que es troba en la posicio passada per
     * parametre dins de la llista de camins del controlador. El cost d'executar aquesta funcio es: O(1).
     * @param posicio conte la posicio de la llista del cami a eliminar.
     * @return Retorna una referencia del cami que es troba en la posicio pasada en per parametre.
     * @throws ArrayIndexOutOfBoundsException L'identificador per obtenir un cami té que estar compresa entre el 1 i
     * la mida de la taula.
     */
    public CamiClausura get_cami_sense_codificar (int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size()) {
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de la taula");
        }
        return this.camins.get(posicio);
    }

    /**
     * Retorna el id del fitxer de clausura del cami.
     * @param posicio conte la posicio de la llista del cami a eliminar.
     * @return C:\Users\Phenom\IdeaProjects\ExpertFinder\src\expert\finder\cami\ControladorCami.java
     * @throws ArrayIndexOutOfBoundsException L'identificador per obtenir un cami té que estar compresa entre el 1 i
     * la mida de la taula.
     */
    public int get_id_fitxer_clausura(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= camins.size()) {
            throw new ArrayIndexOutOfBoundsException("Error: la posicio té que esta compresa entre el 1 i la mida de " +
                    "la taula");
        }
        return this.camins.get(posicio).get_id_fitxer_clausura();
    }
}
