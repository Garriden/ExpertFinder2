package expert.finder.consulta;

import expert.finder.node.Node;
import expert.finder.utils.Tuple;
import java.io.Serializable;

import java.util.ArrayList;
/**
 * Created by Ruben Bagan Benavides
 */

public class Resultat implements Serializable {
    private ArrayList<Tuple> taula;

    // Pre:  Cert
    // Post: Crea un resultat amb cap tuple.
    public Resultat(){
        taula = new ArrayList<>();
    }

    // Pre:  Node != null; 0 <= grauRellevancia <= 1
    // Post: Afegix una tuple al resultat amb el node i un grau de rellevancia passats per paràmetre.
    // Cost: O(1)
    public void afegir_tuple(Node node, double grauRellevancia){
        taula.add(new Tuple(node, grauRellevancia));
    }

    // Pre:  Cert
    // Post: Retorna el nombre de tuples que té un resultat.
    // Cost: O(1)
    public int mida(){
        return taula.size();
    }

    // Pre:  0 <= posicio < Resultat.Mida
    // Post: Retorna la tuple i-èssima del resultat.
    // Cost: O(1)
    public Tuple get_tuple(int posicio) {
        return taula.get(posicio);
    }

    // Pre:  Cert
    // Post: Retorna una referencia a l'ArrayList de tuples d'un resultat.
    // Cost: O(n)
    public ArrayList<Tuple> get_tuples() {
        return this.taula;
    }

    // Pre:  0 <= posicio < Resultat.Mida
    // Post: S'ha elimiant la tuple i-èssima del resultat.
    // Cost: O(1)
    public void eliminar_tuple(int posicio){
        taula.remove(posicio);
    }

    // Pre:  0 <= grauRellevancia
    // Post: S'han eliminat les tuples del resultat que no estan dins de l'interval de grauRellevancia +/- error.
    // Cost: O(n)
    public void filtrar_resultat(double grauRellevancia){
        for(int i = 0; i < taula.size(); ++i){
            if(taula.get(i).get_grau_rellevancia() < grauRellevancia - 0.05 ||
                    taula.get(i).get_grau_rellevancia() > grauRellevancia + 0.05) taula.remove(i);
        }
    }
    
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
