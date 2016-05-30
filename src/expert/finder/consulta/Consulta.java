package expert.finder.consulta;

/**
 *
 * @author adri Col·laboracio Ruben Bagan Benavide
 */

import java.io.Serializable;

/**
 * La funcio d'aquesta classe es materialitzar les diferentes consultes que podem realitzar en el programa. Tota
 * consulta te un resultat que es un conjunt de tuples i informacio diferent depenen del tipus de consulta. Podem
 * classificar les consultes amb 3 tipus:
 *      Tipus I: Es una consulta que donat un node origen d'un cami i un cami trobar tots els nodes desti relacionats
 *      amb aquest node origen amb el seu grau de rellevancia.
 *      Tipus II: Es una consulta que donat un node origen d'un cami i un cami trobar tots els nodes desti relacionats
 *      amb aquest node origen amb el seu grau de rellevancia. Pero a diferencia del tipus I filtrem el resultat a
 *      partir d'un grau de rellevancia proporcionat
 *      Tipus III: Es uan consulta que donat un node origen i un node desti en un cami, trobar el seu grau de
 *      rellevancia que era desconegut. Proporcionar un tercer node, que sera tambe del conjunt de nodes origen
 *      executar la consulta de Tipus II amb el mateix grau de rellevancia.
 *      Tipus IV: Tot i que aquesta consulta no es possible definirla en aquesta classe si es possible realitzarla.
 *      Aquesta consulta es el resultat d'executar una consulta de Tipus I seguit d'una consulta de Tipus II amb un
 *      node origen seleccionat a partir del resultat del Tipus I i amb el seu grau de rellevancia.
 */

public class Consulta implements Serializable {
    private String descripcio;
    private double grauRellevancia;
    private String nodeOrigen;
    private String cami;
    private String nodeDesti;
    private String nodeRelacio;
    private Resultat resultat;
    private String tipoConsulta;

    /**
     * Inicialitza una consulta que ens serveix per trobar la relacio entre un node origen que forma parte del
     * conjunt de nodes del primer node del cami entre tots els nodes desti del node final del cami. El cost
     * d'executar aquesta funcio es: O(1)
     * @param descripcio conte una breu descripcio de que fa la consulta
     * @param nodeOrigen un node qualsevol que pertany al conjunt de nodes del primer node que hi ha en el cami
     * @param cami es un string que conte la navegabilitat entre diferents nodes del graf. cami no apunta a una
     *             referencia nul-la.
     */
    public Consulta(String descripcio, String nodeOrigen, String cami) {
        this(descripcio, nodeOrigen, cami, 0.0);
        this.tipoConsulta = "Tipo I";
    }

    /**
     * Inicialitza una consulta que ens serveix per trobar la relacio entre un node origen que forma parte del
     * conjunt de nodes del primer node del cami entre tots els nodes desti del node final del cami. Despres es
     * filtra el possible resultat per el seu grau de rellevancia. El cost d'executar aquesta funcio es: O(1)
     * @param descripcio conte una breu descripcio de que fa la consulta
     * @param nodeOrigen un node qualsevol que pertany al conjunt de nodes del primer node que hi ha en el cami
     * @param cami es un string que conte la navegabilitat entre diferents nodes del graf. cami no apunta a una
     *             referencia nul-la.
     * @param grauRellevancia es un valor entre [0,1] en que podem mesurar la relacio que tenen els nodes entre ells.
     */
    public Consulta(String descripcio, String nodeOrigen, String cami, double grauRellevancia) {        
        this(descripcio, nodeOrigen, "N/A", "N/A", cami, grauRellevancia);
        this.tipoConsulta = "Tipo II";
    }

    /**
     * Inicialitza una consulta que ens serveix que a partir d'un node origen que forma part del conjunt de nodes del
     * primer node del cami i un node desti del conjunt de nodes del l'ultim node del cami. Trobar el seu grau de
     * rellevancia hi ha partir del node relacio que pertany al mateix conjunt que el node origen trobar els grau de
     * rellevancia dels nodes desti del conjunt de nodes del node final del cami que compleixen el grau trobat entre
     * els dos primers nodes.
     * @param descripcio conte una breu descripcio de que fa la consulta
     * @param nodeOrigen un node qualsevol que pertany al conjunt de nodes del primer node que hi ha en el cami
     * @param nodeDesti un node qualsevol que pertany al conjunt de nodes a l'ultim node que hi ha en el cami
     * @param nodeRelacio un node qualsevol que pertany al conjunt de nodes del primer node que hi ha en el cami
     * @param cami es un string que conte la navegabilitat entre diferents nodes del graf. cami no apunta a una
     *             referencia nul-la.
     * @param grauRellevancia es un valor entre [0,1] en que podem mesurar la relacio que tenen els nodes entre ells.
     */
    public Consulta(String descripcio, String nodeOrigen, String nodeDesti, String nodeRelacio, String cami, double grauRellevancia) {
        this.descripcio = descripcio;
        this.nodeOrigen = nodeOrigen;
        this.nodeDesti = nodeDesti;
        this.nodeRelacio = nodeRelacio;
        this.cami = cami;
        this.grauRellevancia = grauRellevancia;
        this.tipoConsulta = "Tipo III";
    }

    /**
     * Retorna la descripcio de la consulta.
     * @return Retorna la descripcio de la consulta.
     */
    public String get_descripcio() {
        return descripcio;
    }

    /**
     * Retorna el nom del node relacio.
     * @return Retorna el nom del node relacio.
     */
    public String get_node_relacio() {
        return nodeRelacio;
    }

    /**
     * Retorna el nom del node desti.
     * @return Retorna el nom del node desti.
     */
    public String get_node_desti() {
        return nodeDesti;
    }

    /**
     * Retorna el cami que s'utilitza en aquesta consulta.
     * @return Retorna el cami que s'utilitza en aquesta consulta.
     */
    public String get_cami() {
        return cami;
    }

    /**
     * Retorna el nom del node origen.
     * @return Retorna el nom del node origen.
     */
    public String get_node_origen() {
        return nodeOrigen;
    }

    /**
     * Retorna el grau de rellevancia que te aquesta consulta.
     * @return Retorna el grau de rellevancia que te aquesta consulta.
     */
    public double get_grau_rellevancia() {
        return grauRellevancia;
    }

    /**
     * Retorna una referencia al resultat d'aquesta consulta.
     * @return Retorna una referencia al resultat d'aquesta consulta.
     */
    public Resultat get_resultat() {
        return this.resultat;
    }

    /**
     * Retorna el tipus de consulta executada.
     * @return Retorna el tipus de consulta executada.
     */
    public String get_tipo_consulta() {
        return this.tipoConsulta;
    }

    /**
     * Modifica el resultat que te la consulta per la nova referencia pasada per paramtre.
     * @param resultat Conte una referencia al nou resultat de la consulta; no te que apuntar a un valor nul.
     */
    public void set_resultat(Resultat resultat) {
        this.resultat = resultat;
    }
}