package expert.finder.utils;

import expert.finder.node.Node;

/**
 * @Author: Ruben Bagan Benavides
 */

/**
 * La funcio d'aquesta clase es la de representar una tuple d'un resultat. Aquesta tuple consta d'una referencia a
 * un node i el seu grau de rellevancia.
 */
public class Tuple {
    // Enmamgatzema una referencia a un node del graf.
    private Node node;

    // Guarda el grau de rellevancia d'aquest node
    private double grauRellevancia;

    /**
     * Inicialitza una instancia d'una tuple d'un resultat on enmmagatzema una referencia a un node i el seu grau de
     * rellevancia. El cost d'executar aquesta funcio es: O(1)
     * @param node es una referencia a un node del graf. Node no pot apuntar a una referencia nul-la.
     * @param grauRellevancia es el grau de rellevancia del node. El valor del grau de rellevancia [0..1]
     * @return Inicialitza el constructor amb aquest Node i grau de rellevancia.
     *
     */
    public Tuple(Node node, double grauRellevancia){
        this.node = node;
        this.grauRellevancia = grauRellevancia;
    }

    /**
     * Retorna una referencia al node. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna una referencia al node.
     */
    public Node get_node() {
        return this.node;
    }

    /**
     * Retorna el grau de rellevancia del node d'aquesta tuple. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna el grau de rellevancia del node d'aquesta tuple.
     */
    public double get_grau_rellevancia() {
        return this.grauRellevancia;
    }

    /**
     * Modifica la referencia del node al que apunta la tupla a la nova referencia pasada per parametre. El cost
     * d'executar aquesta funcio es: O(1).
     * @param node és una referencia a un node del graf. Node no pot apuntar a una referencia nul-la.
     */
    public void set_node(Node node) {
        this.node = node;
    }

    /**
     * Modifica el grau de rellevancia que te la tuple per aquest node per el grau de rellevancia passat per paramtre.
     * El cost d'executar aquesta funcio es: O(1).
     * @param grauRellevancia és el grau de rellevancia del node. El valor del grau de rellevancia [0..1].
     */
    public void set_grau_rellevancia(double grauRellevancia) {
        this.grauRellevancia = grauRellevancia;
    }
}
