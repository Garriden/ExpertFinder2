package expert.finder.node;

import java.io.Serializable;

/**
 * @Authhor Edgar Perez
 */

/**
 * La funcio d'un node es de representar una instancia d'un node del graf ja sigui un autor, una conferencia, un
 * terme o un paper. Tots els nodes s'identifiquen amb un id i tenen un nom.
 */
public class Node implements Serializable{
    /**
     * El tipus de node son els possibles valors que pot prendre un node, com pot ser un autor, un terme, conferencia
     * o paper.
     */
    public enum TipusNode {
            AUTOR, CONFERENCIA, PAPER, TERME
    }

    private TipusNode tipus;
    private String nom;
    private int id;

    /**
     * Inicialitza una instancia de node amb els valors passats com a parametres. El cost d'executar aquesta funcio
     * es: O(1)
     * @param id conte l'identificador del node. te que ser un valor major o igual que 0.
     * @param nom conte el nom del node. No pot tindre un valor nul.
     * @param tipus conte el tipus del node, que pot ser paper, autor, conferencia o terme.
     */
    public Node(int id, String nom, TipusNode tipus){
            this.id = id;
            this.nom = nom;
            this.tipus = tipus;
    }

    /**
     * Retorna l'identificador del node implicit. El cost d'executar aquesta funcio es: O(1)
     * @return Retorna l'identificador del node implicit.
     */
    public int get_id(){
            return id;
    }

    /**
     * Retorna el nom del node implicit. El cost d'executar aquesta funcio es: O(1)
     * @return Retorna el nom del node implicit.
     */
    public String get_nom(){
            return nom;
    }

    /**
     * Retorna el tipus del node implicit. El cost d'executar aquesta funcio es: O(1)
     * @return Retorna el tipus del node implicit.
     */
    public TipusNode get_tipus_node(){
            return tipus;
    }

    /**
     * Modifica el nom del node implicit amb el nou valor pasat per el parametre. El cost d'executar aquesta funcio
     * es: O(1)
     * @param nom conte el nou nom del node. No pot tindre un valor nul.
     */
    public void set_nom(String nom){
            this.nom = nom;
    }

    /**
     * Modifica l'identificador del node implicit amb el nou valor pasat per el parametre. El cost d'executar aquesta
     * funcio es: O(1)
     * @param id conte el nou l'indentificador del node. Te que ser un valor major o igual que 0.
     */
    public void set_id(int id) {
            this.id = id;
    }
}
