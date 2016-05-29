package expert.finder.graf;
import expert.finder.node.Node;
import expert.finder.utils.Matriu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ruben Bagan Benavides
 */

public class Graf implements Serializable{
    // Matriu esparsa d'adjecencia que conte les relacions paper x autor
    protected Matriu paperAutor;
    // Matriu esparsa d'adjecencia que conte les relacions paper x conferencia
    protected Matriu paperConferencia;
    // Matriu esparsa d'adjecencia que conte les relacions paper x terme
    protected Matriu paperTerme;

    // ArrayList's que contenen tots els nodes [paper,terme,conferencia,autor] ordenats per id.
    protected ArrayList<Node> paper;
    protected ArrayList<Node> conferencia;
    protected ArrayList<Node> autor;
    protected ArrayList<Node> terme;


    // Pre:  Cap dels parametres té valor nul
    // Post: S'inicialitza un graf, amb els nodes terme, autor , paper i conferencia. S'inicialitza les relacions
    //       paper x autor, paper x terme i paper x conferencia.
    // Cost: O(1)
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

    // Pre:  Cert
    // Post: El graf implícit té un nou node en la seva llista de nodes del tipus especificat per el parametre
    //       (autor, paper, conferencia o terme) i no té cap relació amb cap altre node.
    // Cost: O(1)
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

    //Pre:  Cert
    //Post: S'elimina del graf implícit el node del tipus del node passat per parametre. Al eliminar un node si es de
    //      tipus paper s'elimina tambe totes les relacions que té amb els altres autors, igual que si es de tipus autor,
    //      conferencia o terme.
    //Cost: O(n)
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


    // Pre:  Cert
    // Post: Modifica la descripcio del node del graf implicit per la descripcio del node passat per parametre amb
    //       identificador i tipus igual que el node passat per paramtre.
    // Cost: O(1)
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

    // Pre:  Cert
    // Post: Retorna una referencia a un node amb identificador i del tipus passat per parametre (autor, terme,
    //       conferencia, paper) del graf implícit.
    // Cost: O(1)
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

    // Pre:  Cert.
    // Post: Afegeix un '1' en la posició corresponent de la matriu paper_(TipusNodeDestí)
    // Cost: O(1)
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

    // Pre:  Node Origen sempre serà paper, les matrius son Paper x [Autor,Conferencia,Terme]
    // Post: Afegeix un '0' en la posició corresponent de la matriu paper_(TipusNodeDestí)
    // Cost: O(1);
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

    // Pre:  Cert.
    // Post: Retorna una referencia a la matriu paperAutor.
    // Cost: O(1);
    public Matriu get_paper_autor() {
        return this.paperAutor;
    }

    // Pre:  Cert.
    // Post: Retorna una referencia a la matriu paperConferencia.
    // Cost: O(1);
    public Matriu get_paper_conferencia() {
        return this.paperConferencia;
    }

    // Pre:  Cert.
    // Post: Retorna una referencia a la matriu paperTerme.
    // Cost: O(1);
    public Matriu get_paper_terme() {
        return this.paperTerme;
    }

    // Pre:  Cert.
    // Post: Retorna una referencia a una llista de papers.
    // Cost: O(1);
    public ArrayList<Node> get_paper() {
        return this.paper;
    }

    // Pre:  Cert.
    // Post: Retorna una referencia a una llista de conferencies.
    // Cost: O(1);
    public ArrayList<Node> get_conferencia() {
        return this.conferencia;
    }

    // Pre:  Cert.
    // Post: Retorna una referencia a una llista de autors.
    // Cost: O(1);
    public ArrayList<Node> get_autor() {
        return this.autor;
    }

    // Pre:  Cert.
    // Post: Retorna una referencia a una llista de termes.
    // Cost: O(1);
    public ArrayList<Node> get_terme() {
        return this.terme;
    }

}
