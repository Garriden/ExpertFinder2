package expert.finder.graf;

import expert.finder.node.Node;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides on 16/05/2016.
 */
public class ControladorGraf {
    private Graf graf;

    private Node.TipusNode stringToTipusNode(String tipusNode) throws IllegalArgumentException {
        if (tipusNode.equalsIgnoreCase("AUTOR")) return Node.TipusNode.AUTOR;
        if (tipusNode.equalsIgnoreCase("TERME")) return Node.TipusNode.TERME;
        if (tipusNode.equalsIgnoreCase("PAPER")) return Node.TipusNode.PAPER;
        if (tipusNode.equalsIgnoreCase("CONFERENCIA")) return Node.TipusNode.CONFERENCIA;
        throw new IllegalArgumentException("Error: El tipus de node que has sel·leccionat es incorrecte.");
    }

    public void importar(boolean nouGraf, String rutaFitxer) throws IOException {
        ControladorPersistenciaGraf controladorPersistenciaGraf = new ControladorPersistenciaGraf();
        if (nouGraf) this.graf = controladorPersistenciaGraf.importar_graf_nou(rutaFitxer);
        else this.graf = controladorPersistenciaGraf.importar_graf_salvat(rutaFitxer);
    }

    public void exportar(String rutaFitxer) throws IOException {
        ControladorPersistenciaGraf controladorPersistenciaGraf = new ControladorPersistenciaGraf();
        controladorPersistenciaGraf.exportar(rutaFitxer, this.graf);
    }

    public void afegir_node(String nom, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        this.graf.afegir_node(tipus, nom);
    }

    public void eliminar_node(int posicio, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        Node n = this.graf.get_node(posicio, tipus);
        if (n == null) throw new IllegalArgumentException("Error: No existeix cap node amb aquest identificador o no es del tipus node correcte.");
        else this.graf.eliminar_node(n);
    }

    public void modificar_nom_node(int posicio, String nouNom, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        Node n = new Node(posicio, nouNom, tipus);
        int error = this.graf.actualizar_node(n);
        if (error == 1) throw new IllegalArgumentException("Error: No existeix cap node amb aquest identificador.");
    }

    public String consultar_node(int posicio, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        Node n = this.graf.get_node(posicio, tipus);
        if (n == null) throw new IllegalArgumentException("Error: No existeix cap node amb aquest identificador o no es del tipus node correcte.");
        String node = n.get_id() + "|" + n.get_nom() + "|" + n.get_tipus_node();
        return node;
    }

    public ArrayList<String> get_nodes(String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        ArrayList<Node> nodes;
        if (tipus == Node.TipusNode.AUTOR) nodes = this.graf.get_autor();
        else if (tipus == Node.TipusNode.PAPER) nodes = this.graf.get_paper();
        else if (tipus == Node.TipusNode.TERME) nodes = this.graf.get_terme();
        else nodes = this.graf.get_conferencia();

        ArrayList<String> nodesCodificats = new ArrayList<>(nodes.size());
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            nodesCodificats.add(n.get_id()+1 + "|" + n.get_nom());
        }

        return nodesCodificats;
    }

    public void afegir_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) throws IllegalArgumentException{
        Node.TipusNode tipus = stringToTipusNode(tipusNodeDesti);
        Node nodeOrigen = this.graf.get_node(idNodeOrigen, Node.TipusNode.PAPER);
        if (nodeOrigen == null) throw new IllegalArgumentException("Error: No existeix cap node origen amb aquest identificador o no es del tipus node correcte.");
        Node nodeDesti = this.graf.get_node(idNodeDesti, tipus);
        if (nodeDesti == null) throw new IllegalArgumentException("Error: No existeix cap node desti amb aquest identificador o no es del tipus node correcte.");
        int error = this.graf.afegir_aresta(nodeOrigen, nodeDesti);
        switch (error) {
            case -3:
                throw new IllegalArgumentException("Error: No hi ha cap node origen de tipus paper amb aquest identificador.");
            case -4:
                throw new IllegalArgumentException("Error: No hi ha cap node desti amb aquest identificador.");
            case -5:
                throw new IllegalArgumentException("Error: El node desti no es del tipus Autor, Conferencia o Terme.");
        }
    }

    public void eliminar_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNodeDesti);
        Node nodeOrigen = this.graf.get_node(idNodeOrigen, Node.TipusNode.PAPER);
        if (nodeOrigen == null) throw new IllegalArgumentException("Error: No existeix cap node origen amb aquest identificador o no es del tipus node correcte.");
        Node nodeDesti = this.graf.get_node(idNodeDesti, tipus);
        if (nodeDesti == null) throw new IllegalArgumentException("Error: No existeix cap node desti amb aquest identificador o no es del tipus node correcte.");
        int error = this.graf.eliminar_aresta(nodeOrigen, nodeDesti);
        switch (error) {
            case -3:
                throw new IllegalArgumentException("Error: No hi ha cap node origen de tipus paper amb aquest identificador.");
            case -4:
                throw new IllegalArgumentException("Error: No hi ha cap node desti amb aquest identificador.");
            case -5:
                throw new IllegalArgumentException("Error: El node desti no es del tipus Autor, Conferencia o Terme.");
        }
    }

    public ArrayList<String> consultar_relacio(int idNodeOrigen, String tipusNodeDesti) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNodeDesti);
        Node nodeOrigen = this.graf.get_node(idNodeOrigen, Node.TipusNode.PAPER);
        if (nodeOrigen == null) throw new IllegalArgumentException("Error: No existeix cap node origen amb aquest identificador o no es del tipus node correcte.");

        ArrayList<Node> nodes;
        if (tipus == Node.TipusNode.AUTOR) nodes = this.graf.get_autor();
        else if (tipus == Node.TipusNode.TERME) nodes = this.graf.get_terme();
        else nodes = this.graf.get_conferencia();

        ArrayList<String> nodesCodificats = new ArrayList<>(nodes.size());
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            double valor;
            if (tipus == Node.TipusNode.TERME) valor = this.graf.get_paper_terme().get_valor(idNodeOrigen, n.get_id());
            else if (tipus == Node.TipusNode.AUTOR) valor = this.graf.get_paper_autor().get_valor(idNodeOrigen, n.get_id());
            else valor = this.graf.get_paper_conferencia().get_valor(idNodeOrigen, n.get_id());
            if (valor == 1.0) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Si");
            else nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|No");
        }

        return nodesCodificats;
    }

    public Graf get_graf() {
        return this.graf;
    }

}
