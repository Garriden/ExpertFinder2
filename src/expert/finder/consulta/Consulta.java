package expert.finder.consulta;

import expert.finder.cami.Cami;
import expert.finder.node.*;

/**
 *
 * @author adri
 */

public class Consulta {
    private String tipusConsulta;
    private String descripcio;
    private double grauRellevancia;
    private Node nodeOrigen;
    private Cami cami;
    private Node nodeDesti;
    private Node nodeRelacio;
    private boolean actualitzada;

    public Consulta(String descripcio, Node nodeOrigen, Cami cami) {
        this.tipusConsulta = "Tipus I";
        this.descripcio = descripcio;
        this.nodeOrigen = nodeOrigen;
        this.cami = cami;
        this.actualitzada = true;
    }

    public Consulta(String descripcio, Node nodeOrigen, Cami cami, double grauRellevancia) {
        this.tipusConsulta = "Tipus II";
        this.descripcio = descripcio;
        this.nodeOrigen = nodeOrigen;
        this.cami = cami;
        this.grauRellevancia = grauRellevancia;
        this.actualitzada = true;
    }

    public Consulta(String descripcio, Node nodeOrigen, Node nodeDesti, Node nodeRelacio, Cami cami) {
        this.tipusConsulta = "Tipus III";
        this.descripcio = descripcio;
        this.nodeOrigen = nodeOrigen;
        this.nodeDesti = nodeDesti;
        this.nodeRelacio = nodeRelacio;
        this.cami = cami;
        this.actualitzada = true;
    }

    public String get_descripcio() {
        return descripcio;
    }

    public void set_descripcio(String descripcio) {
        this.descripcio = descripcio;
    }


    public Node get_node_relacio() {
        return nodeRelacio;
    }

    public void set_node_relacio(Node nodeRelacio) {
        this.nodeRelacio = nodeRelacio;
    }

    public Node get_node_desti() {
        return nodeDesti;
    }

    public void set_node_desti(Node nodeDesti) {
        this.nodeDesti = nodeDesti;
    }

    public Cami get_cami() {
        return cami;
    }

    public void set_cami(Cami cami) {
        this.cami = cami;
    }

    public Node get_node_origen() {
        return nodeOrigen;
    }

    public void set_node_origen(Node nodeOrigen) {
        this.nodeOrigen = nodeOrigen;
    }

    public double get_grau_rellevancia() {
        return grauRellevancia;
    }

    public void set_grau_rellevancia(double grauRellevancia) {
        this.grauRellevancia = grauRellevancia;
    }

    public String get_tipus_consulta() {
        return tipusConsulta;
    }

    public boolean es_actualitzada() {
        return this.actualitzada;
    }

    public void actualitzar(boolean nouEstat) {
        this.actualitzada = nouEstat;
    }
}