//  Created Ruben Bagan
package expert.finder.consulta;

import expert.finder.cami.Cami;
import expert.finder.cami.ControladorCami;
import expert.finder.graf.ControladorGraf;
import expert.finder.node.Node;
import expert.finder.utils.Matriu;

import java.util.ArrayList;

public class ControladorConsulta {
    private ArrayList<Consulta> consultes;
    private HeteSim hetesim;
    private ControladorGraf controladorGraf;
    private ControladorCami controladorCami;

    private Node.TipusNode stringToTipusNode(String tipusNode) throws IllegalArgumentException {
        if (tipusNode.equalsIgnoreCase("AUTOR")) return Node.TipusNode.AUTOR;
        if (tipusNode.equalsIgnoreCase("TERME")) return Node.TipusNode.TERME;
        if (tipusNode.equalsIgnoreCase("PAPER")) return Node.TipusNode.PAPER;
        if (tipusNode.equalsIgnoreCase("CONFERENCIA")) return Node.TipusNode.CONFERENCIA;
        throw new IllegalArgumentException("Error: El tipus de node que has sel·leccionat es incorrecte.");
    }

    private Node get_node(int posicio, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        Node node = this.controladorGraf.get_graf().get_node(posicio, tipus);
        if (node == null) throw new IllegalArgumentException("Error: No existeix cap node amb aquest identificador o no es del tipus node correcte.");
        return node;
    }

    private Cami get_cami(int posicioCami) throws IllegalArgumentException {
        String camiCodificat = this.controladorCami.get_cami(posicioCami);
        return new Cami(camiCodificat.substring(0, camiCodificat.indexOf('|')),
                camiCodificat.substring(camiCodificat.indexOf('|')+1, camiCodificat.length()));
    }

    public ControladorConsulta(ControladorGraf controladorGraf, ControladorCami controladorCami) {
        this.hetesim = new HeteSim();
        this.consultes = new ArrayList<>();
        this.controladorGraf = controladorGraf;
        this.controladorCami = controladorCami;
    }

    public void afegir_consulta_tipusI(String descripcio, String tipusNode, int posicioNode, int posicioCami) throws IllegalArgumentException {
        Node nodeOrigen = get_node(posicioNode, tipusNode);
        Cami cami = get_cami(posicioCami);
        Consulta c = new Consulta(descripcio, nodeOrigen, cami);
        this.consultes.add(c);
    }

    public void afegir_consultaTipusII(String descripcio, String tipusNode, int posicioNode, int posicioCami, double grauRellevancia) throws IllegalArgumentException {
        Node nodeOrigen = get_node(posicioNode, tipusNode);
        Cami cami = get_cami(posicioCami);
        Consulta c = new Consulta(descripcio, nodeOrigen, cami, grauRellevancia);
        this.consultes.add(c);
    }

    public void afegir_consultaTipusIII(String descripcio, String tipusNodeOrigen, String tipusNodeDesti, int posicioNodeOrigen, int posicioNodeDesti,
                                       int posicioNodeRelacio, int posicioCami) throws IllegalArgumentException {
        Node nodeOrigen = get_node(posicioNodeOrigen, tipusNodeOrigen);
        Node nodeDesti = get_node(posicioNodeDesti, tipusNodeDesti);
        Node nodeRelacio = get_node(posicioNodeRelacio, tipusNodeOrigen);
        Cami cami = get_cami(posicioCami);
        Consulta c = new Consulta(descripcio, nodeOrigen, nodeDesti, nodeRelacio, cami);
        this.consultes.add(c);
    }

    public void eliminar_consulta(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= this.consultes.size())
            throw new ArrayIndexOutOfBoundsException("Error: La posicio de la consulta té que estar compresa entre el 1 i la mida de la taula");
        this.consultes.remove(posicio);
    }

    public ArrayList<String> get_consultes() {
        ArrayList<String> consultesCodificades = new ArrayList<>(this.consultes.size());
        for (int i = 0; i < this.consultes.size(); ++i) {
            Consulta c = this.consultes.get(i);
            String consultaCodificada = i+1 + "|" + c.get_tipus_consulta() + "|" + c.get_descripcio();
            consultesCodificades.add(consultaCodificada);
        }

        return consultesCodificades;
    }

    public void modificar_node_origen(int posicioConsulta, int posicioNode, String tipusNode) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Node node = get_node(posicioNode, tipusNode);
        if (posicioConsulta < 0 || posicioConsulta >= this.consultes.size())
            throw new ArrayIndexOutOfBoundsException("Error: La posicio de la consulta té que estar compresa entre el 1 i la mida de la taula");
        this.consultes.get(posicioConsulta).set_node_origen(node);
    }

    public void modificar_node_desti(int posicioConsulta, int posicioNode, String tipusNode) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Node node = get_node(posicioNode, tipusNode);
        if (posicioConsulta < 0 || posicioConsulta >= this.consultes.size())
            throw new ArrayIndexOutOfBoundsException("Error: La posicio de la consulta té que estar compresa entre el 1 i la mida de la taula");
        this.consultes.get(posicioConsulta).set_node_desti(node);
    }

    public void modificar_node_relacio(int posicioConsulta, int posicioNode, String tipusNode) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Node node = get_node(posicioNode, tipusNode);
        if (posicioConsulta < 0 || posicioConsulta >= this.consultes.size())
            throw new ArrayIndexOutOfBoundsException("Error: La posicio de la consulta té que estar compresa entre el 1 i la mida de la taula");
        this.consultes.get(posicioConsulta).set_node_relacio(node);
    }

    public void modificar_cami(int posicioConsulta, int posicioCami) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Cami cami = get_cami(posicioCami);
        if (posicioConsulta < 0 || posicioConsulta >= this.consultes.size())
            throw new ArrayIndexOutOfBoundsException("Error: La posicio de la consulta té que estar compresa entre el 1 i la mida de la taula");
        this.consultes.get(posicioConsulta).set_cami(cami);
    }

    public void modificar_grau_rellevancia(int posicioConsulta, double grauRellevancia) throws  ArrayIndexOutOfBoundsException{
        if (posicioConsulta < 0 || posicioConsulta >= this.consultes.size())
            throw new ArrayIndexOutOfBoundsException("Error: La posicio de la consulta té que estar compresa entre el 1 i la mida de la taula");
        this.consultes.get(posicioConsulta).set_grau_rellevancia(grauRellevancia);
    }

    public void actualitzar_nodes_consultes(int posicioNode, String tipusNode) {
        Node node = get_node(posicioNode, tipusNode);
        for (int i = 0; i < this.consultes.size(); ++i) {
            Consulta c = this.consultes.get(i);
            if (c.get_node_origen().get_id() == node.get_id()) this.consultes.remove(i);
            else if (c.get_tipus_consulta().equalsIgnoreCase("Tipus III")) {
                if (c.get_node_desti().get_id() == node.get_id() || c.get_node_relacio().get_id() == node.get_id()) {
                    this.consultes.remove(i);
                }
            }
        }
    }

    public void actualitzar_camins_consultes(int posicioCami) throws IllegalArgumentException {
        Cami cami = get_cami(posicioCami);
        for (int i = 0; i < this.consultes.size(); ++i) {
            Consulta c = this.consultes.get(i);
            if (c.get_cami().get_cami().equalsIgnoreCase(cami.get_cami()) &&
                    c.get_descripcio().equalsIgnoreCase(cami.get_descripcio())) {
                this.consultes.remove(i);
            }
        }
    }

    public ArrayList<String> executar_consulta(int posicioConsulta) {
        if (posicioConsulta < 0 || posicioConsulta >= this.consultes.size())
            throw new ArrayIndexOutOfBoundsException("Error: La posicio de la consulta té que estar compresa entre el 1 i la mida de la taula");
        ArrayList<String> resultat = new ArrayList<>();
        Consulta c = this.consultes.get(posicioConsulta);
        if (c.get_tipus_consulta().equalsIgnoreCase("Tipus III")) {
            double grauRellevancia = this.hetesim.grau_relacio(c.get_node_origen(), c.get_node_desti(), c.get_cami(), this.controladorGraf.get_graf());
            c.set_grau_rellevancia(grauRellevancia);
        }

        String cami = c.get_cami().get_cami();
        Matriu matriu = hetesim.relacio_node_matriu(c.get_node_origen(), c.get_cami(), controladorGraf.get_graf());
        String tipusNode = "Autor";
        if (cami.charAt(cami.length()-1) == 'T') tipusNode = "Terme";
        else if (cami.charAt(cami.length()-1) == 'C') tipusNode = "Conferencia";
        for (int i = 0 ; i < matriu.get_nombre_columnes(); ++i) {
            String node = controladorGraf.consultar_node(i, tipusNode);
            node = node + "|" + matriu.get_valor(c.get_node_origen().get_id(),i);
            if (c.get_tipus_consulta().equalsIgnoreCase("Tipus II") || c.get_tipus_consulta().equalsIgnoreCase("Tipus III")) {
                if (matriu.get_valor(c.get_node_origen().get_id(), i) >= c.get_grau_rellevancia() - 0.05 &&
                        matriu.get_valor(c.get_node_origen().get_id(), i) <= c.get_grau_rellevancia() + 0.05) {
                    resultat.add(node);
                }
            }
            else resultat.add(node);
        }

        return resultat;
    }
}
