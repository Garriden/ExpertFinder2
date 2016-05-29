package expert.finder.graf;
import expert.finder.node.Node;
import expert.finder.utils.Matriu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author: Marc Garrido Col·laboracio: Ruben Bagan Benavides
 */

/**
 * La funcio d'aquesta clase es la de representar un graf, composat per nodes i relaciones. Aquest graf contempla les
 * seguents cardinalitats:
 *      - un autor pot existir i no escriure cap paper.
 *      - un paper sempre te que se escrit per un o mes autors.
 *      - un paper nomes pot estar relacion amb una conferencia
 *      - un paper pot tindre cap o molts termes
 *      - un terme pot estar relacionat amb cap o molts papers
 *      - una conferencia pot tindre cap o molts papers.
 */
public class Graf implements Serializable{
    /**
     * Matriu esparsa d'adjecencia que conte les relacions paper x autor
      */
    protected Matriu paperAutor;
    /**
     * Matriu esparsa d'adjecencia que conte les relacions paper x conferencia
     */
    protected Matriu paperConferencia;
    /**
     * Matriu esparsa d'adjecencia que conte les relacions paper x terme
     */
    protected Matriu paperTerme;

    // ArrayList's que contenen tots els nodes [paper,terme,conferencia,autor] ordenats per id.
    protected ArrayList<Node> paper;
    protected ArrayList<Node> conferencia;
    protected ArrayList<Node> autor;
    protected ArrayList<Node> terme;


    /**
     * Inicialitza una instancia de graf amb tots els atributs inicialitzas amb els valors dels parametres. Cada
     * atribut apunta a una referencia d'un parametre. El cost d'executar aquesta funcio es: O(1).
     * @param paperAutor conte una referencia a una matriu amb les relacions paper x autor, no pot tenir valor nul.
     * @param paperConferencia conte una referencia a una matriu amb les relacions paper x conferencia, no pot tenir
     *                         valor nul.
     * @param paperTerme conte una referencia a una matriu amb les relacions paper x terme, no pot tenir valor nul.
     * @param paper conte una referencia a un llista de nodes de tipus paper ordenats per el seu identificador.
     * @param terme conte una referencia a un llista de nodes de tipus terme ordenats per el seu identificador.
     * @param autor conte una referencia a un llista de nodes de tipus autor ordenats per el seu identificador.
     * @param conferencia conte una referencia a un llista de nodes de tipus conferencia ordenats per el seu
     *                    identificador.
     */
    public Graf(Matriu paperAutor, Matriu paperConferencia, Matriu paperTerme, ArrayList<Node> paper,
                ArrayList<Node> terme, ArrayList<Node> autor, ArrayList<Node> conferencia)
    {
        this.paperAutor = paperAutor;
        this.paperConferencia = paperConferencia;
        this.paperTerme = paperTerme;

        this.paper = paper;
        this.conferencia = conferencia;
        this.autor = autor;
        this.terme = terme;
    }

    /**
     * Afageix un nou node al graf implicit amb nom i tipus de node amb els valors dels parametres. Aquest nou node no
     * te cap relacio amb  altres nodes. Al afegir node de tipus diferent a paper s'incrementa en 1 les columnes de
     * les matrius d'adjecencia, si aquest es un paper sincrementa la fila de cada matriu.  L'identificador d'aquest
     * node l'inicialitza automaticament el graf. El cost d'executar aquesta funcio es: O(1).
     * @param tipusNode el tipus del node te que ser un dels tipus valids: autor, paper, conferencia o terme
     * @param nomNode conte el nom del node que s'afegeix el graf.
     * @throws IllegalArgumentException Retorna un error si algun parametre es nul o no es del tipus correcte.
     */
    public void afegir_node(Node.TipusNode tipusNode, String nomNode) throws IllegalArgumentException {
        if (tipusNode == null || nomNode == null) {
            throw new IllegalArgumentException("Error: El tipos del node i/o el nom del node no poden tenir valor nul");
        }
        switch (tipusNode) {
            case AUTOR:
                int id = this.autor.size();
                this.autor.add(new Node(id, nomNode, tipusNode));
                this.paperAutor.afegir_columna();
                break;
            case PAPER:
                id = this.paper.size();
                this.paper.add(new Node(id, nomNode, tipusNode));
                this.paperAutor.afegir_fila();
                this.paperTerme.afegir_fila();
                this.paperConferencia.afegir_fila();
                break;
            case TERME:
                id = this.terme.size();
                this.terme.add(new Node(id, nomNode, tipusNode));
                paperTerme.afegir_columna();
                break;
            case CONFERENCIA:
                id = this.conferencia.size();
                this.conferencia.add(new Node(id, nomNode, tipusNode));
                this.paperConferencia.afegir_columna();
                break;
            default:
                throw new IllegalArgumentException("Error: El tipus del node no es correcte, té que ser: Autor, " +
                        "Paper, Conferencia o Terme.");
        }
    }

    /**
     * S'elimina del graf implícit un node igual al node passat per parametre. Al eliminar un node si es de tipus
     * paper s'elimina tambe totes les relacions que te amb els altres nodes es a dir s'elimina una fila de cada
     * matriu. Si el tipus es diferent a paper s'elimina la columna de la matriu corresponent. Si s'elimina un autor
     * i aquest es l'ultim autor d'un paper o mes retorna un error i impadeix eliminar-lo. El cost d'executar aquesta
     * funcio es: O(n).
     * @param node conte informacio del node que s'ha d'eliminar. Aquest node no pot apunta a una referencia nul-la.
     * @throws IllegalArgumentException Retorna un error si el parametre te valor nul, l'identificador del node no es
     * valid, el tipus del node es incorrecte o no es pot eliminar el autor.
     */
    public void eliminar_node(Node node) throws IllegalArgumentException {
        if (node == null) {
            throw new IllegalArgumentException("Error: El node no pot tenir valor nul");
        }
        switch (node.get_tipus_node()) {
            case AUTOR:
                if (node.get_id() < 0 || node.get_id() >= this.autor.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus autor té que estar  " +
                            "dins l'interval");
                }

                HashMap<Integer, HashMap<Integer, Double>> data = this.paperAutor.get_hashmap();
                for (Integer fila : data.keySet()) {
                    HashMap<Integer, Double> columnes = data.get(fila);
                    for (Integer columna : columnes.keySet()) {
                        if (columna == node.get_id() && columnes.size() == 1) {
                            throw new IllegalArgumentException("Error: No es pot eliminar aquest autor perquè es  " +
                                    "l'últim autor per algun paper/s");
                        }
                    }
                }

                this.paperAutor.eliminar_columna(node.get_id());
                this.autor.remove(node.get_id());
                for (int i = node.get_id(); i < this.autor.size(); ++i) {
                    this.autor.get(i).set_id(i);
                }
                break;
            case PAPER:
                if (node.get_id() < 0 || node.get_id() >= this.paper.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus paper té que estar  " +
                            "dins l'interval");
                }
                this.paperAutor.eliminar_fila(node.get_id());
                this.paperTerme.eliminar_fila(node.get_id());
                this.paperConferencia.eliminar_fila(node.get_id());
                this.paper.remove(node.get_id());
                for (int i = node.get_id(); i < paper.size(); ++i) {
                    paper.get(i).set_id(i);
                }
                break;
            case TERME:
                if (node.get_id() < 0 || node.get_id() >= this.terme.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus terme té que estar  " +
                            "dins l'interval");
                }
                this.paperTerme.eliminar_columna(node.get_id());
                this.terme.remove(node.get_id());
                for (int i = node.get_id(); i < terme.size(); ++i) {
                    terme.get(i).set_id(i);
                }
                break;
            case CONFERENCIA:
                if (node.get_id() < 0 || node.get_id() >= this.conferencia.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus conferencia té que  " +
                            "estar dins l'interval");
                }
                this.paperConferencia.eliminar_columna(node.get_id());
                this.conferencia.remove(node.get_id());
                for (int i = node.get_id(); i < conferencia.size(); ++i) {
                    conferencia.get(i).set_id(i);
                }
                break;
            default:
                throw new IllegalArgumentException("Error: El tipus del node no es correcte, té que ser: Autor, " +
                        "Paper,  Conferencia o Terme.");
        }
    }

    /**
     * Modifica la descripcio del node del graf implicit per la descripcio del node passat per parametre amb
     * l'identificador i tipus igual que el node passat per paramtre. El cost d'executar aquesta funcio es: O(1).
     * @param node conte informacio del node que s'ha de modificar. Aquest node no pot apunta a una referencia nul-la.
     * @throws IllegalArgumentException retorna un error si el parametre te valor nul, l'identificador del node no es
     * valid o el tipus del node es incorrecte.
     */
    public void actualizar_node(Node node) throws IllegalArgumentException {
        if (node == null) {
            throw new IllegalArgumentException("Error: El node no pot tenir valor nul");
        }
        switch (node.get_tipus_node()) {
            case AUTOR:
                if (node.get_id() < 0 || node.get_id() >= this.autor.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus autor té que estar  " +
                            "dins l'interval");
                }
                this.autor.get(node.get_id()).set_nom(node.get_nom());
                break;
            case PAPER:
                if (node.get_id() < 0 || node.get_id() >= this.paper.size()) {                    
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus paper té que estar  " +
                            "dins l'interval");
                }
                this.paper.get(node.get_id()).set_nom(node.get_nom());
                break;
            case TERME:
                if (node.get_id() < 0 || node.get_id() >= this.terme.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus terme té que estar  " +
                            "dins l'interval");
                }
                this.terme.get(node.get_id()).set_nom(node.get_nom());
                break;
            case CONFERENCIA:
                if (node.get_id() < 0 || node.get_id() >= this.conferencia.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus conferencia té que  " +
                            "estar dins l'interval");
                }
                this.conferencia.get(node.get_id()).set_nom(node.get_nom());
                break;
            default:
                throw new IllegalArgumentException("Error: El tipus del node no es correcte, té que ser: Autor, " +
                        "Paper,  Conferencia o Terme.");
        }
    }

    /**
     * Retorna una referencia a un node amb identificador i tipus passats per parametre (autor, terme, conferencia,
     * paper) del graf implícit. El cost d'executar aquesta funcio es: O(1).
     * @param id l'identificador del node te que ser major o igual que 0.
     * @param tipus el tipus del node te que ser un dels tipus valids: autor, paper, conferencia o terme
     * @return
     * @throws IllegalArgumentException retorna un error si el parametre te valor nul, l'identificador del node no es
     * valid o el tipus del node es incorrecte.
     */
    public Node get_node(int id, Node.TipusNode tipus) throws IllegalArgumentException {
        if (tipus == null) {
            throw new IllegalArgumentException("Error: El tipus de node no pot tenir un valor nul");
        }
        
        Node node = null;
        switch(tipus) {
            case AUTOR:
                if (id < 0 || id >= this.autor.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus autor té que estar  " +
                            "dins l'interval");
                }
                node = autor.get(id);
                break;
            case PAPER:                
                if (id < 0 || id >= this.paper.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus paper té que estar  " +
                            "dins l'interval");
                }
                node = paper.get(id);
                break;
            case TERME:
                if (id < 0 || id >= this.terme.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus terme té que estar  " +
                            "dins l'interval");
                }
                node = terme.get(id);
                break;
            case CONFERENCIA:
                if (id < 0 || id >= this.conferencia.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node de tipus conferencia té que  " +
                            "estar dins l'interval");
                }
                node = conferencia.get(id);
                break;
            default:
                throw new IllegalArgumentException("Error: El tipus del node no es correcte, té que ser: Autor, " +
                        "Paper, Conferencia o Terme.");
        }
        
        if (node == null) {
            throw new IllegalArgumentException("Error: El node que busques amb aquest identificador no existeix");
        }
        return node;
    }

    /**
     * Actualitza el valor de la relació del graf implícit entre el node origen (paper) i el node desti (terme,
     * autor, conferencia) amb valor '1'. Node Origen sempre serà paper, les matrius son Paper x [Autor,Conferencia,
     * Terme] El cost d'executar aquesta funcio es: O(1).
     * @param nodeOrigen conte una referencia al node origen de tipus paper.
     * @param nodeDesti conte una referencia al node desti d'un tipus diferent a paper.
     * @throws IllegalArgumentException Error si el tipus origen no es de tipus paper, o els arguments tenen valor
     * nul, l'identificador no es valid o perque no es poden afegir mes conferencies a un paper.
     */
    public void afegir_aresta(Node nodeOrigen, Node nodeDesti) throws IllegalArgumentException {
        if (nodeOrigen == null || nodeDesti == null) {
            throw new IllegalArgumentException("Error: El node origen i/o el node desti no poden tenir valors nul");
        }
        if (nodeOrigen.get_tipus_node() != Node.TipusNode.PAPER) {
            throw new IllegalArgumentException("Error: El node origen te que ser de tipus paper");            
        }
        if (nodeOrigen.get_id() < 0 || nodeOrigen.get_id() >= this.paper.size()) {
            throw new IllegalArgumentException("Error: L'identificador del node desti de tipus paper té que estar " +
                    "dins  l'interval");
        }
        switch (nodeDesti.get_tipus_node()) {
            case AUTOR:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.autor.size()){
                    throw new IllegalArgumentException("Error: L'identificador del node desti de tipus autor té que  " +
                            "estar dins l'interval");
                }
                this.paperAutor.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 1.0);
                break;
            case CONFERENCIA:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.conferencia.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node desti de tipus conferencia té" +
                            "  que estar dins l'interval");
                }

                HashMap<Integer, HashMap<Integer, Double>> data = this.paperConferencia.get_hashmap();
                if (data.containsKey(nodeOrigen.get_id())) {
                    throw new IllegalArgumentException("Error: No es pot afegir més conferencies en aquest paper " +
                            "perque un paper nomes pot estar en una conferencia.");
                }

                this.paperConferencia.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 1.0);
                break;
            case TERME:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.terme.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node desti de tipus terme té que  " +
                            "estar dins l'interval");
                }
                this.paperTerme.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 1.0);
                break;
            default:
                throw new IllegalArgumentException("Error: El tipus del node no es correcte, té que ser: Autor, " +
                        "Paper,  Conferencia o Terme.");
        }
    }

    /**
     * Actualitza el valor de la relació del graf implícit entre el node origen (paper) i el node desti (terme,
     * autor, conferencia) amb valor '0'. Node Origen sempre serà paper, les matrius son Paper x [Autor,Conferencia,
     * Terme] El cost d'executar aquesta funcio es: O(1).
     * @param nodeOrigen conte una referencia al node origen de tipus paper.
     * @param nodeDesti conte una referencia al node desti d'un tipus diferent a paper.
     * @throws IllegalArgumentException Error si el tipus origen no es de tipus paper, o els arguments tenen valor
     * nul, l'identificador no es valid o perque no es poden eliminar conferencies perque un paper com a minim esta a
     * una conferencia.
     */
    public void eliminar_aresta(Node nodeOrigen, Node nodeDesti) throws IllegalArgumentException {
        if (nodeOrigen == null || nodeDesti == null) {
            throw new IllegalArgumentException("Error: El node origen i/o el node desti no poden tenir valors nul");
        }
        if (nodeOrigen.get_tipus_node() != Node.TipusNode.PAPER) {
            throw new IllegalArgumentException("Error: El node origen te que ser de tipus paper");            
        }
        if (nodeOrigen.get_id() < 0 || nodeOrigen.get_id() >= this.paper.size()) {
            throw new IllegalArgumentException("Error: L'identificador del node desti de tipus paper té que estar " +
                    "dins  l'interval");
        }
        HashMap<Integer, HashMap<Integer, Double>> data;
        switch (nodeDesti.get_tipus_node()) {
            case AUTOR:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.autor.size()){
                    throw new IllegalArgumentException("Error: L'identificador del node desti de tipus autor té que  " +
                            "estar dins l'interval");
                }

                data = this.paperAutor.get_hashmap();
                if (data.containsKey(nodeOrigen.get_id())) {
                    if (data.get(nodeOrigen.get_id()).size() == 1) {
                        throw new IllegalArgumentException("Error: No es pot eliminar aquest autor perquè es l'últim " +
                                "autor del paper " + nodeOrigen.get_nom());
                    }
                }

                this.paperAutor.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 0.0);
                break;
            case CONFERENCIA:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.conferencia.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node desti de tipus conferencia té" +
                            "  que estar dins l'interval");
                }

                data = this.paperConferencia.get_hashmap();
                if (data.containsKey(nodeOrigen.get_id())) {
                    if (data.get(nodeOrigen.get_id()).size() == 1) {
                        throw new IllegalArgumentException("Error: No es pot eliminar aquesta conferencia perquè un " +
                                "paper sempre te que esta relacionat amb alguna");
                    }
                }
                break;
            case TERME:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.terme.size()) {
                    throw new IllegalArgumentException("Error: L'identificador del node desti de tipus terme té que  " +
                            "estar dins l'interval");
                }
                this.paperTerme.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 0.0);
                break;
            default:
                throw new IllegalArgumentException("Error: El tipus del node no es correcte, té que ser: Autor, " +
                        "Paper,  Conferencia o Terme.");
        }
    }

    /**
     * Retorna una referencia a la matriu d'adjcencencia paper x autor. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna una referencia a la matriu d'adjcencencia paper x autor
     */
    public Matriu get_paper_autor() {
        return this.paperAutor;
    }

    /**
     * Retorna una referencia a la matriu d'adjcencencia paper x conferencia. El cost d'executar aquesta funcio es: O
     * (1).
     * @return Retorna una referencia a la matriu d'adjcencencia paper x conferencia
     */
    public Matriu get_paper_conferencia() {
        return this.paperConferencia;
    }

    /**
     * Retorna una referencia a la matriu d'adjcencencia paper x terme. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna una referencia a la matriu d'adjcencencia paper x terme
     */
    public Matriu get_paper_terme() {
        return this.paperTerme;
    }

    /**
     * Retorna una referencia a la llista de nodes de tipus paper. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna una referencia a la llista de nodes de tipus paper.
     */
    public ArrayList<Node> get_paper() {
        return this.paper;
    }

    /**
     * Retorna una referencia a la llista de nodes de tipus conferencia. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna una referencia a la llista de nodes de tipus conferencia.
     */
    public ArrayList<Node> get_conferencia() {
        return this.conferencia;
    }

    /**
     * Retorna una referencia a la llista de nodes de tipus autor. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna una referencia a la llista de nodes de tipus autor.
     */
    public ArrayList<Node> get_autor() {
        return this.autor;
    }

    /**
     * Retorna una referencia a la llista de nodes de tipus terme. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna una referencia a la llista de nodes de tipus terme.
     */
    public ArrayList<Node> get_terme() {
        return this.terme;
    }

}
