package expert.finder.utils;

import expert.finder.node.Node;

/**
 * Created by Ruben Bagan Benavides on 08/05/2016.
 */
public class Tuple {
    private Node node;
    private double grauRellevancia;

    public Tuple(Node node, double grauRellevancia){
        this.node = node;
        this.grauRellevancia = grauRellevancia;
    }
    public Node get_node() {
        return this.node;
    }
    public double get_grau_rellevancia() {
        return this.grauRellevancia;
    }

    public void set_node(Node node) {
        this.node = node;
    }

    public void set_grau_rellevancia(double grauRellevancia) {
        this.grauRellevancia = grauRellevancia;
    }
}
