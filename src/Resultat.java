import Node.Node;
import Utils.Tuple;

import java.util.ArrayList;
/**
 * Created by Edgar Perez, Col·laboracio : Ruben Bagan Benavides
 */

public class Resultat {
    private ArrayList<Tuple> taula;

    // Pre:  Cert
    // Post: Crea un resultat amb cap tuple.
    public Resultat(){
        taula = new ArrayList<>();
    }

    // Pre:  Node.Node != null; 0 <= grauRellevancia <= 1
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

    // Pre:  0 <= pos < Resultat.Mida
    // Post: Retorna la tuple i-èssima del resultat.
    // Cost: O(1)
    public Tuple get_tuple(int pos) {
        return taula.get(pos);
    }

    // Pre:  Cert
    // Post: Retorna una copia de profunditat de les tuples del resultat.
    // Cost: O(n)
    public ArrayList<Tuple> get_tuples() {
        ArrayList<Tuple> copia_taula = new ArrayList<>();
        for (int i = 0; i < this.taula.size(); ++i) {
            Node node_taula = this.taula.get(i).get_node();
            Node n = new Node(node_taula.get_id(), node_taula.get_nom(), node_taula.get_tipus_node());
            copia_taula.add(new Tuple(n, this.taula.get(i).get_grau_rellevancia()));
        }
        return copia_taula;
    }

    // Pre:  0 <= pos < Resultat.Mida
    // Post: S'ha elimiant la tuple i-èssima del resultat.
    // Cost: O(1)
    public void eliminar_tuple(int pos){
        taula.remove(pos);
    }

    // Pre:  0 <= grauRellevancia <= 1, 0 <= error <= 1
    // Post: S'han eliminat les tuples del resultat que no estan dins de l'interval de grauRellevancia +/- error.
    // Cost: O(n)
    public void filtrar_resultat(double grauRellevancia, double error){
        double min = grauRellevancia - error;
        double max = grauRellevancia + error;
        if (min < 0) min = 0;
        if (max > 1) max = 1;
        for(int i = 0; i < taula.size(); ++i){
            if(taula.get(i).get_grau_rellevancia() < min || taula.get(i).get_grau_rellevancia() > max) taula.remove(i);
        }
    }
}
