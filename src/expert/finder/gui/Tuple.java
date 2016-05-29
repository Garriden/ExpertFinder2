/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.consulta;

import expert.finder.node.Node;

/**
 * Created by Ruben Bagan Benavides on 08/05/2016.
 */
public class Tuple {
    private String node;
    private double grauRellevancia;

    public Tuple(String node, double grauRellevancia){
        this.node = node;
        this.grauRellevancia = grauRellevancia;
    }
    public String get_node() {
        return this.node;
    }
    public double get_grau_rellevancia() {
        return this.grauRellevancia;
    }

    public void set_node(String node) {
        this.node = node;
    }

    public void set_grau_rellevancia(double grauRellevancia) {
        this.grauRellevancia = grauRellevancia;
    }
}
