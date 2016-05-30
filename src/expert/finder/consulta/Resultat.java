package expert.finder.consulta;

import expert.finder.node.Node;
import expert.finder.utils.Tuple;
import java.io.Serializable;

import java.util.ArrayList;
/**
 * Created by Ruben Bagan Benavides
 */

/**
 * La funcio d'aquesta clase es la de representar un resultat d'una consulta. Entenem com a resultat un conjunt de
 * tuples que conte una copia del node i el seu grau de rellevancia.
 */
public class Resultat implements Serializable {
    private ArrayList<Tuple> taula;

    /**
     * Inicialitza una instancia de resultat. El cost d'executar aquesta funcio es: O(1)
     */
    public Resultat(){
        taula = new ArrayList<>();
    }

    /**
     * Afegiex una tupla al resultat. Aquesta tuple estara formada per la referencia al node passat per parametre i
     * el seu grau de rellevancia. El cost d'executar aquesta funcio es: O(1)
     * @param node Es una referencia a una copia del node resultant. No pot apuntar a una referencia nul-la.
     * @param grauRellevancia Es un valor que te assignat al node que va en el rang [0..1]
     */
    public void afegir_tuple(Node node, double grauRellevancia){
        taula.add(new Tuple(node, grauRellevancia));
    }

    /**
     * Retorna el nombre de tuples que te la consulta. El cost d'executar aquesta funcio es: O(1)
     * @return Retorna el nombre de tuples que te la consulta.
     */
    public int mida(){
        return taula.size();
    }

    /**
     * Retorna una referencia a una tuple que es troba a la posicio pasada per parametre. El cost d'executar aquesta
     * funcio es: O(1)
     * @param posicio La posicio te que ser major o igual que 0 i menor que el nombre de tuples de la consulta.
     * @return Retorna una referencia a una tuple que es troba a la posicio pasada per parametre.
     */
    public Tuple get_tuple(int posicio) {
        return taula.get(posicio);
    }

    /**
     * Retorna una referencia al conjunt de tuples que formen el resultat. El cost d'executar aquesta funcio es: O(1)
     * @return Retorna una referencia al conjunt de tuples que formen el resultat.
     */
    public ArrayList<Tuple> get_tuples() {
        return this.taula;
    }

    /**
     * Elimina una tuple que estigui en la posicio pasada per parametre. El cost d'executar aquesta funcio es: O(1)
     * @param posicio La posicio te que ser major o igual que 0 i menor que el nombre de tuples de la consulta.
     */
    public void eliminar_tuple(int posicio){
        taula.remove(posicio);
    }

    /**
     * Aquesta funcio serveix que donat un grau de rellevancia filtra totes les seves tuples a partir d'aquest grau
     * de rellevancia pasat com a paramtre. La filtracio va en funcio que si el grau de rellevancia del node es menor
     * que el grau pasat per paramtre s'elimina del resultat. El cost d'executar aquesta funcio es: O(n) on n es el
     * nombre de tuples.
     * @param grauRellevancia Es un valor que te assignat al node que va en el rang [0..1]
     */
    public void filtrar_resultat(double grauRellevancia){
        for(int i = 0; i < taula.size(); ++i){
            if(taula.get(i).get_grau_rellevancia() < grauRellevancia) taula.remove(i);
        }
    }

    /**
     * Retorna una llista de totes les tuples del resultat codificades amb els seguent format:
     * [idNode]|[nomNode]|[TipusNode]|[GrauRellevancia]
     * @return Retorna una llista de totes les tuples del resultat codificades amb els seguent format:
     * [idNode]|[nomNode]|[TipusNode]|[GrauRellevancia]
     */
    public ArrayList<String> codificar_resultat() {
        ArrayList<String> resultatsCodificat = new ArrayList<>(this.taula.size());
        for (int i = 0; i < this.taula.size(); ++i) {
            Tuple tuple = this.taula.get(i);
            String resultat = tuple.get_node().get_id()
                    + "|" + tuple.get_node().get_nom() 
                    + "|" + tuple.get_node().get_tipus_node()
                    + "|" + tuple.get_grau_rellevancia();
            resultatsCodificat.add(resultat);
        } 
        
        return resultatsCodificat;    
    }
}
