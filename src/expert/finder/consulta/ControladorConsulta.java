//  Created Ruben Bagan
package expert.finder.consulta;

import expert.finder.cami.Cami;
import expert.finder.cami.CamiClausura;
import expert.finder.cami.ControladorCami;
import expert.finder.graf.ControladorGraf;
import expert.finder.node.Node;
import expert.finder.utils.Matriu;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorConsulta {
    private ArrayList<Consulta> consultes;
    private HeteSim hetesim;
    private ControladorGraf controladorGraf;
    private ControladorCami controladorCami;

    private Node.TipusNode camiToTipusNode(char tipusNode) throws IllegalArgumentException {
        if (tipusNode == 'A') return Node.TipusNode.AUTOR;
        if (tipusNode == 'T') return Node.TipusNode.TERME;
        if (tipusNode == 'P') return Node.TipusNode.PAPER;
        if (tipusNode == 'C') return Node.TipusNode.CONFERENCIA;
        throw new IllegalArgumentException("Error: El tipus de node no existeix, te que ser de tipus: Autor, Terme, Paper o Conferencia");
    }
    
    private Node.TipusNode stringToTipusNode(String tipusNode) throws IllegalArgumentException {
        if (tipusNode.equalsIgnoreCase("AUTOR")) return Node.TipusNode.AUTOR;
        if (tipusNode.equalsIgnoreCase("TERME")) return Node.TipusNode.TERME;
        if (tipusNode.equalsIgnoreCase("PAPER")) return Node.TipusNode.PAPER;
        if (tipusNode.equalsIgnoreCase("CONFERENCIA")) return Node.TipusNode.CONFERENCIA;
        throw new IllegalArgumentException("Error: El tipus de node no existeix, te que ser de tipus: Autor, Terme, Paper o Conferencia");
    }
    
    private Resultat generar_resultat(Matriu m, Node.TipusNode tipusDesti) {
        HashMap<Integer, HashMap<Integer, Double>> data = m.get_hashmap();
        Resultat resultat = new Resultat();
        for (Integer fila : data.keySet()) {
            HashMap<Integer, Double> columnes = data.get(fila);
            for (Integer columna : columnes.keySet()) {
                double valor = columnes.get(columna);
                if (valor > 0.0) {
                    Node nodeDesti = this.controladorGraf.get_copia_node(columna, tipusDesti);
                    resultat.afegir_tuple(nodeDesti, valor);
                }
            }
        }
        
        return resultat;
    }
    
    private Resultat generar_resultat(ArrayList<String> resultatsCodificat) {
        Resultat resultat = new Resultat();
        for (int i = 0; i < resultatsCodificat.size(); ++i) {
            String resultatCodificat = resultatsCodificat.get(i);
            int posicioNom = resultatCodificat.indexOf('|');
            int posicioTipus = resultatCodificat.indexOf('|',posicioNom);
            int grau = resultatCodificat.indexOf('|',posicioTipus);
            
            int id = Integer.valueOf(resultatCodificat.substring(0, posicioNom));
            String nom = resultatCodificat.substring(posicioNom+1, posicioTipus);
            Node.TipusNode tipusNode = stringToTipusNode(resultatCodificat.substring(posicioTipus+1, grau));
            double grauRellevancia = Double.valueOf(resultatCodificat.substring(grau+1, resultatCodificat.length())); 
            
            Node node = new Node(id, nom, tipusNode);
            resultat.afegir_tuple(node, grauRellevancia);
        }
        
        return resultat;
    }
    
    public ControladorConsulta(ControladorGraf controladorGraf, ControladorCami controladorCami) {
        this.hetesim = new HeteSim();
        this.consultes = new ArrayList<>();
        this.controladorGraf = controladorGraf;
        this.controladorCami = controladorCami;
    }
    
    public ArrayList<String> executar_consulta_tipusI(String descripcio, int idNodeOrigen, int idCami) throws IllegalArgumentException {
        CamiClausura cami = this.controladorCami.get_cami_sense_codificar(idCami);
        Node.TipusNode tipusOrigen = camiToTipusNode(cami.get_cami().charAt(0));
        Node.TipusNode tipusDesti = camiToTipusNode(cami.get_cami().charAt(cami.get_longitud()-1));
        
        Node nodeOrigen = this.controladorGraf.get_graf().get_node(idNodeOrigen, tipusOrigen);
        Matriu m = this.hetesim.relacio_node_matriu(nodeOrigen, cami, this.controladorGraf.get_graf());
        Resultat resultat = generar_resultat(m, tipusDesti);
        
        Consulta consulta = new Consulta(descripcio, nodeOrigen.get_nom(), cami.get_cami());
        consulta.set_resultat(resultat);
        this.consultes.add(consulta);
        
        return resultat.codificar_resultat();
    }
    
    public ArrayList<String> executar_consulta_tipusII(String descripcio, int idNodeOrigen, int idCami, double grauRellevancia) throws IllegalArgumentException {
        CamiClausura cami = this.controladorCami.get_cami_sense_codificar(idCami);
        Node.TipusNode tipusOrigen = camiToTipusNode(cami.get_cami().charAt(0));
        Node.TipusNode tipusDesti = camiToTipusNode(cami.get_cami().charAt(cami.get_longitud()-1));
        
        Node nodeOrigen = this.controladorGraf.get_graf().get_node(idNodeOrigen, tipusOrigen);
        Matriu m = this.hetesim.relacio_node_matriu(nodeOrigen, cami, this.controladorGraf.get_graf());
        Resultat resultat = generar_resultat(m, tipusDesti);
        resultat.filtrar_resultat(grauRellevancia);
        
        Consulta consulta = new Consulta(descripcio, nodeOrigen.get_nom(), cami.get_cami(), grauRellevancia);
        consulta.set_resultat(resultat);
        this.consultes.add(consulta);        
        
        return resultat.codificar_resultat();        
    }
    
    public ArrayList<String> executar_consulta_tipusIII(String descripcio, int idNodeOrigen, int idCami, int idNodeDesti1, int idNodeDesti2) {        
        CamiClausura cami = this.controladorCami.get_cami_sense_codificar(idCami);
        Node.TipusNode tipusOrigen = camiToTipusNode(cami.get_cami().charAt(0));
        Node.TipusNode tipusDesti1 = camiToTipusNode(cami.get_cami().charAt(cami.get_longitud()-1));
        
        Node nodeOrigen = this.controladorGraf.get_graf().get_node(idNodeOrigen, tipusOrigen);
        Node nodeDesti1 = this.controladorGraf.get_graf().get_node(idNodeDesti1, tipusDesti1);
        double grauRellevancia = this.hetesim.grau_relacio(nodeOrigen, nodeDesti1, cami, this.controladorGraf.get_graf());
        
        Node nodeDesti2 = this.controladorGraf.get_graf().get_node(idNodeDesti2, tipusOrigen);        
        Matriu m = this.hetesim.relacio_node_matriu(nodeDesti2, cami, this.controladorGraf.get_graf());
        Resultat resultat = generar_resultat(m, tipusDesti1);
        resultat.filtrar_resultat(grauRellevancia);
        
        Consulta consulta = new Consulta(descripcio, nodeOrigen.get_nom(), nodeDesti1.get_nom(), nodeDesti2.get_nom(), cami.get_cami(), grauRellevancia);
        consulta.set_resultat(resultat);
        this.consultes.add(consulta);       
        
        return resultat.codificar_resultat();        
    }
    
    public void eliminar_ultima_consulta_executada() {
        this.consultes.remove(this.consultes.size() - 1);
    }
    
    public void ultima_consulta_modificada(ArrayList<String> resultatCodificat) {
        Consulta consulta = this.consultes.get(this.consultes.size() - 1);
        Resultat resultat = generar_resultat(resultatCodificat);
        consulta.set_resultat(resultat);        
    }
    
    public int get_nombre_consultes() {
        return this.consultes.size();
    }
    
    public ArrayList<String> get_resultat(int posicio) {
        return this.consultes.get(posicio).get_resultat().codificar_resultat();
    }
    
    public ArrayList<String> get_consultes() {
        ArrayList<String> consultesCodificades = new ArrayList<>(this.consultes.size());
        for (int i = 0; i < this.consultes.size(); ++i) {
            Consulta consulta = this.consultes.get(i);
            consultesCodificades.add(consulta.get_tipo_consulta() 
                    + "|" + consulta.get_descripcio() 
                    + "|" + consulta.get_node_origen()
                    + "|" + consulta.get_cami()
                    + "|" + consulta.get_node_desti()
                    + "|" + consulta.get_node_origen()
                    + "|" + consulta.get_grau_rellevancia());
        }
        
        return consultesCodificades;
    }

    public void eliminar_consulta(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= this.consultes.size())
            throw new ArrayIndexOutOfBoundsException("Error: La posicio de la consulta té que estar compresa entre el 1 i la mida de la taula");
        this.consultes.remove(posicio);
    }
    
    /*
    public ArrayList<String> get_consultes() {
        ArrayList<String> consultesCodificades = new ArrayList<>(this.consultes.size());
        for (int i = 0; i < this.consultes.size(); ++i) {
            Consulta c = this.consultes.get(i);
            String consultaCodificada = c.get_tipus_consulta() + "|" + c.get_descripcio();
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
            double valor = matriu.get_valor(0,i);
            if (valor != 0.0) {
                System.out.println();
            }
            node = node + "|" + String.valueOf(valor);
            if (c.get_tipus_consulta().equalsIgnoreCase("Tipus II") || c.get_tipus_consulta().equalsIgnoreCase("Tipus III")) {
                if (valor >= c.get_grau_rellevancia() - 0.05 && valor <= c.get_grau_rellevancia() + 0.05) {
                    resultat.add(node);
                }
            }
            else resultat.add(node);
        }

        return resultat;
    }*/
}
