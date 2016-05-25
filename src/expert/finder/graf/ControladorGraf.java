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
        this.graf.eliminar_node(n);
    }

    public void modificar_nom_node(int posicio, String nouNom, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        Node n = new Node(posicio, nouNom, tipus);
        this.graf.actualizar_node(n);
    }

    public String consultar_node(int posicio, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        Node n = this.graf.get_node(posicio, tipus);
        String node = n.get_nom();
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
            nodesCodificats.add(n.get_nom());
        }

        return nodesCodificats;
    }

    public void afegir_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) throws IllegalArgumentException{
        Node.TipusNode tipus = stringToTipusNode(tipusNodeDesti);
        Node nodeOrigen = this.graf.get_node(idNodeOrigen, Node.TipusNode.PAPER);
        Node nodeDesti = this.graf.get_node(idNodeDesti, tipus);
        this.graf.afegir_aresta(nodeOrigen, nodeDesti);
    }

    public void eliminar_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNodeDesti);
        Node nodeOrigen = this.graf.get_node(idNodeOrigen, Node.TipusNode.PAPER);
        Node nodeDesti = this.graf.get_node(idNodeDesti, tipus);
        this.graf.eliminar_aresta(nodeOrigen, nodeDesti);
    }
    

    public ArrayList<String> consultar_relacio(int idNodeOrigen, String tipusNodeDesti, boolean relacionats) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNodeDesti);

        ArrayList<Node> nodes;
        if (tipus == Node.TipusNode.AUTOR) nodes = this.graf.get_autor();
        else if (tipus == Node.TipusNode.TERME) nodes = this.graf.get_terme();
        else nodes = this.graf.get_conferencia();

        ArrayList<String> nodesCodificats = new ArrayList<>();
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            double valor;
            if (tipus == Node.TipusNode.TERME) valor = this.graf.get_paper_terme().get_valor(idNodeOrigen, n.get_id());
            else if (tipus == Node.TipusNode.AUTOR) valor = this.graf.get_paper_autor().get_valor(idNodeOrigen, n.get_id());
            else valor = this.graf.get_paper_conferencia().get_valor(idNodeOrigen, n.get_id());
            if (valor == 1.0 && relacionats) nodesCodificats.add(n.get_nom());
            else if (valor == 0.0) nodesCodificats.add(n.get_nom());
        }

        return nodesCodificats;
    }

    public Graf get_graf() {
        return this.graf;
    }

}
