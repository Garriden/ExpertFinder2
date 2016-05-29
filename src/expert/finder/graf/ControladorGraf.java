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

    // Pre:  Cert.
    // Post: S'inicialitza el graf del controlador amb el contingut de la base de dades o d'un altre graf guardat com
    //       a objecte.
    // Cost: O(n)
    public void importar(boolean nouGraf, String rutaFitxer) throws IOException {
        ControladorPersistenciaGraf controladorPersistenciaGraf = new ControladorPersistenciaGraf();
        if (nouGraf) this.graf = controladorPersistenciaGraf.importar_graf_nou(rutaFitxer);
        else this.graf = controladorPersistenciaGraf.importar_graf_salvat(rutaFitxer);
    }

    // Pre:  Cert
    // Post: S'enmmagatzema el graf del controlador en el fitxer indicat en la ruta passada del parametre.
    // Cost: O(1)
    public void exportar(String rutaFitxer) throws IOException {
        ControladorPersistenciaGraf controladorPersistenciaGraf = new ControladorPersistenciaGraf();
        controladorPersistenciaGraf.exportar(rutaFitxer, this.graf);
    }

    // Pre:  Cert
    // Post: S'afegeix un nou node al graf del controlador, amb els atributs del node inicialitzats amb els valors
    //       passats per parametre.
    // Cost: O(n)
    public void afegir_node(String nom, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        this.graf.afegir_node(tipus, nom);
    }

    // Pre:  Cert
    // Post: S'elimina el node del graf del controlador.
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
        String node = n.get_id() + "|" + n.get_nom();
        return node;
    }
    
    public boolean existeix_node(String nomNode, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        ArrayList<Node> nodes;
        if (tipus == Node.TipusNode.AUTOR) nodes = this.graf.get_autor();
        else if (tipus == Node.TipusNode.PAPER) nodes = this.graf.get_paper();
        else if (tipus == Node.TipusNode.TERME) nodes = this.graf.get_terme();
        else nodes = this.graf.get_conferencia();
        
        for (int i = 0; i < nodes.size(); ++i) {
            String nom = nodes.get(i).get_nom();
            if (nom.equalsIgnoreCase(nomNode)) return true;            
        }
        
        return false;        
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
            nodesCodificats.add(n.get_id() + "|" + n.get_nom());
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
    

    public ArrayList<String> consultar_relacio(int idNodeOrigen, boolean relacionats) throws IllegalArgumentException {
        ArrayList<String> nodesCodificats = new ArrayList<>();
        ArrayList<Node> nodes = this.graf.get_autor();
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            double valor = this.graf.get_paper_autor().get_valor(idNodeOrigen, n.get_id());
            if (valor == 1.0 && relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Autor");
            else if (valor == 0.0 && !relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Autor");
        }
        
        nodes = this.graf.get_terme();
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            double valor = this.graf.get_paper_terme().get_valor(idNodeOrigen, n.get_id());
            if (valor == 1.0 && relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Terme");
            else if (valor == 0.0 && !relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Terme");
        }
        
        nodes = this.graf.get_conferencia();        
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            double valor = this.graf.get_paper_conferencia().get_valor(idNodeOrigen, n.get_id());
            if (valor == 1.0 && relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Conferencia");
            else if (valor == 0.0 && !relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Conferencia");
        }
        
        return nodesCodificats;
    }

    public Node get_copia_node(int idNode, Node.TipusNode tipusNode) throws IllegalArgumentException {
        Node node = this.graf.get_node(idNode, tipusNode);        
        return new Node(node.get_id(), node.get_nom(), node.get_tipus_node());
    }
    
    public Graf get_graf() {
        return this.graf;
    }

}
