package expert.finder.consulta;

import expert.finder.cami.Cami;
import expert.finder.node.*;

/**
 *
 * @author adri
 */

public class Consulta {
    private String descripcio;
    private double grauRellevancia;
    private String nodeOrigen;
    private String cami;
    private String nodeDesti;
    private String nodeRelacio;
    private Resultat resultat;
    private String tipoConsulta;

    public Consulta(String descripcio, String nodeOrigen, String cami) {
        this(descripcio, nodeOrigen, cami, 0.0);
        this.tipoConsulta = "Tipo I";
    }

    public Consulta(String descripcio, String nodeOrigen, String cami, double grauRellevancia) {        
        this(descripcio, nodeOrigen, "Cap", "Cap", cami, grauRellevancia);
        this.tipoConsulta = "Tipo II";
    }

    public Consulta(String descripcio, String nodeOrigen, String nodeDesti, String nodeRelacio, String cami, double grauRellevancia) {
        this.descripcio = descripcio;
        this.nodeOrigen = nodeOrigen;
        this.nodeDesti = nodeDesti;
        this.nodeRelacio = nodeRelacio;
        this.cami = cami;
        this.grauRellevancia = grauRellevancia;
        this.tipoConsulta = "Tipo III";
    }

    public String get_descripcio() {
        return descripcio;
    }

    public String get_node_relacio() {
        return nodeRelacio;
    }

    public String get_node_desti() {
        return nodeDesti;
    }

    public String get_cami() {
        return cami;
    }

    public String get_node_origen() {
        return nodeOrigen;
    }

    public double get_grau_rellevancia() {
        return grauRellevancia;
    }
    
    public Resultat get_resultat() {
        return this.resultat;
    }
    
    public String get_tipo_consulta() {
        return this.tipoConsulta;
    }
    
    public void set_resultat(Resultat resultat) {
        this.resultat = resultat;
    }
}