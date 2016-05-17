package expert.finder.graf;
import expert.finder.node.Node;
import expert.finder.utils.Matriu;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Marc Garrido on 17/04/2016. Col·laboracio Ruben Bagan Benavides
 */

public class Graf implements Serializable{
    private Matriu paperAutor;
    private Matriu paperConferencia;
    private Matriu paperTerme;

    private ArrayList<Node> paper;
    private ArrayList<Node> conferencia;
    private ArrayList<Node> autor;
    private ArrayList<Node> terme;


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
    // Post: S'ha afegeix un node al graf del tipus especificat per el tipus node,
    // Cost: O(1)
    // Errors:
    //      -1 = Un dels arguments té valor nul.
    //      -2 = El tipus de node no és vàlid.
    public int afegir_node(Node.TipusNode tipusNode, String nomNode) {
        if (tipusNode == null || nomNode == null) return -1;
        int error = 0;
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
                error = -2;
        }
        return error;
    }

    //Pre:  Cert
    //Post: Elimina el node desitjat al final de l'Array corresponent i treu les columnes i/o files la matriu corresponent.
    // Errors:
    //      1 = no hay ningun nodo con esa id.
    //      2 = tipus node incorrecte
    public int eliminar_node(Node node) {
        int error = 0;
        switch (node.get_tipus_node()) {
            case AUTOR:
                if (node.get_id() < 0 || node.get_id() >= this.autor.size()) error = 1;
                else {
                    this.autor.remove(node.get_id());
                    this.paperAutor.eliminar_columna(node.get_id());
                    for (int i = node.get_id(); i < this.autor.size(); ++i) this.autor.get(i).set_id(i);
                }
                break;
            case PAPER:
                if (node.get_id() < 0 || node.get_id() >= this.paper.size()) error = 1;
                else {
                    this.paper.remove(node.get_id());
                    this.paperAutor.eliminar_fila(node.get_id());
                    this.paperTerme.eliminar_fila(node.get_id());
                    this.paperConferencia.eliminar_fila(node.get_id());
                    for (int i = node.get_id(); i < paper.size(); ++i) paper.get(i).set_id(i);
                }
                break;
            case TERME:
                if (node.get_id() < 0 || node.get_id() >= this.terme.size()) error = 1;
                else {
                    this.terme.remove(node.get_id());
                    this.paperTerme.eliminar_columna(node.get_id());
                    for (int i = node.get_id(); i < terme.size(); ++i) terme.get(i).set_id(i);
                }
                break;
            case CONFERENCIA:
                if (node.get_id() < 0 || node.get_id() >= this.conferencia.size()) error = 1;
                else {
                    this.conferencia.remove(node.get_id());
                    this.paperConferencia.eliminar_columna(node.get_id());
                    for (int i = node.get_id(); i < conferencia.size(); ++i) conferencia.get(i).set_id(i);
                }
                break;
            default:
                error = 2;
        }

        return error;
    }


    // Pre: Cert
    // Post: Canvia el nom del node indicat
    // Errors:
    //      1 = No hi ha cap node amb aquest id.
    //      2 = El tipus de node es incorrecte.
    public int actualizar_node(Node node) {
        int error = 0;
        switch (node.get_tipus_node()) {
            case AUTOR:
                if (0 < node.get_id() || node.get_id() >= this.autor.size()) error = 1;
                else this.autor.get(node.get_id()).set_nom(node.get_nom());
                break;
            case PAPER:
                if (0 < node.get_id() || node.get_id() >= this.paper.size()) error = 1;
                else this.paper.get(node.get_id()).set_nom(node.get_nom());
                break;
            case TERME:
                if (0 < node.get_id() || node.get_id() >= this.terme.size()) error = 1;
                else this.terme.get(node.get_id()).set_nom(node.get_nom());
                break;
            case CONFERENCIA:
                if (0 < node.get_id() || node.get_id() >= this.conferencia.size()) error = 1;
                else this.conferencia.get(node.get_id()).set_nom(node.get_nom());
                break;
            default:
                return 2;
        }
        return error;
    }

    //Pre: Cert
    //Post: Retorna el node indicat
    public Node get_node(int i, Node.TipusNode tipus) {
        switch(tipus) {
            case AUTOR:
                if(i >= 0 && i < autor.size()) return autor.get(i);
                break;
            case PAPER:
                if(i >= 0 && i < paper.size()) return paper.get(i);
                break;
            case TERME:
                if(i >= 0 && i < terme.size()) return terme.get(i);
                break;
            case CONFERENCIA:
                if(i >= 0 && i < conferencia.size()) return conferencia.get(i);
                break;
        }
        return null;
    }

    //Pre:  Node Origen sempre serà paper, les matrius son Paper x [Autor,Conferencia,Terme]
    //Post: Afegeix un '1' en la posició corresponent de la matriu paper_(TipusNodeDestí)
    // Errors:
    //      -1 = Un dels arguments té valor nul.
    //      -2 = El node origen no es de tipus paper.
    //      -3 = No hi ha cap node de tipus paper amb el id del node origen.
    //      -4 = L'identificador del node desti no existeix.
    //      -5 = El node desti no es del tipus Autor, Conferencia o Terme.
    public int afegir_aresta(Node nodeOrigen, Node nodeDesti) {
        if (nodeOrigen == null || nodeDesti == null) return -1;
        if (nodeOrigen.get_tipus_node() != Node.TipusNode.PAPER) return -2;
        if (nodeOrigen.get_id() < 0 || nodeOrigen.get_id() >= this.paper.size()) return -3;
        int error = 0;
        switch (nodeDesti.get_tipus_node()) {
            case AUTOR:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.autor.size()) error = -4;
                else this.paperAutor.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 1.0);
                break;
            case CONFERENCIA:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.conferencia.size()) error = -4;
                else this.paperConferencia.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 1.0);
                break;
            case TERME:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.terme.size()) error = -4;
                else this.paperTerme.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 1.0);
                break;
            default:
                error = -5;
        }
        return error;
    }

    //Pre:  Node Origen sempre serà paper, les matrius son Paper x [Autor,Conferencia,Terme]
    //Post: Afegeix un '0' en la posició corresponent de la matriu paper_(TipusNodeDestí)
    // Errors:
    //      -1 = Un dels arguments té valor nul.
    //      -2 = El node origen no es de tipus paper.
    //      -3 = No hi ha cap node de tipus paper amb el id del node origen.
    //      -4 = L'identificador del node desti no existeix.
    //      -5 = El node desti no es del tipus Autor, Conferencia o Terme.
    public int eliminar_aresta(Node nodeOrigen, Node nodeDesti) {
        if (nodeOrigen == null || nodeDesti == null) return -1;
        if (nodeOrigen.get_tipus_node() != Node.TipusNode.PAPER) return -2;
        if (nodeOrigen.get_id() < 0 || nodeOrigen.get_id() >= this.paper.size()) return -3;
        int error = 0;
        switch (nodeDesti.get_tipus_node()) {
            case AUTOR:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.autor.size()) error = -4;
                else this.paperAutor.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 0.0);
                break;
            case CONFERENCIA:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.conferencia.size()) error = -4;
                else this.paperConferencia.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 0.0);
                break;
            case TERME:
                if (nodeDesti.get_id() < 0 || nodeDesti.get_id() >= this.terme.size()) error = -4;
                else this.paperTerme.set_valor(nodeOrigen.get_id(), nodeDesti.get_id(), 0.0);
                break;
            default:
                error = -5;
        }

        return error;
    }

    //Pre: Cert
    //Post: Retorna la matriu paperAutor
    public Matriu get_paper_autor() {
        return this.paperAutor;
    }

    //Pre: Cert
    //Post: Retorna la matriu paperConferencia
    public Matriu get_paper_conferencia() {
        return this.paperConferencia;
    }

    //Pre: Cert
    //Post: Retorna la matriu paperTerme
    public Matriu get_paper_terme() {
        return this.paperTerme;
    }

    //Pre: Cert
    //Post: Retorna la ArrayList get_paper
    public ArrayList<Node> get_paper() {
        return this.paper;
    }

    //Pre: Cert
    //Post: Retorna la ArrayList get_conferencia
    public ArrayList<Node> get_conferencia() {
        return this.conferencia;
    }

    //Pre: Cert
    //Post: Retorna la ArrayList get_autor
    public ArrayList<Node> get_autor() {
        return this.autor;
    }

    //Pre: Cert
    //Post: Retorna la ArrayList get_terme
    public ArrayList<Node> get_terme() {
        return this.terme;
    }


}
