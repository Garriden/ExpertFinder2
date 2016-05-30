package expert.finder.cami;

import java.io.Serializable;

/**
 *
 * @author adri, Col·laboracio: Ruben Bagan Benavides
 */

/**
 * La funcio d'aquesta clase es la de representar un cami. Aquest cami indica la navegabilitat per un graf entre els
 * seus nodes.
 */
public class Cami implements Serializable{
    /**
     * Conte la navegabilitat per el graf.
     */
    protected String cami;
    /**
     * Conte una breu descripcio del cami.
     */
    protected String descripcio;

    /**
     * Inicialitza una instancia de graf amb els valors passats per paramtre. El cost d'executar aquesta funcio es: O(1).
     * @param cami Conte la navegabilitat per el graf. No pot tenir valor nul.
     * @param descripcio Conte una breu descripcio del cami. No pot tenir valor nul.
     */
    public Cami(String cami, String descripcio) {
        this.cami = cami;
        this.descripcio = descripcio;
    }

    /**
     * Modifica el cami que conte el cami implicit per el nou cami passat per paramtre. El cost d'executar aquesta
     * funcio es: O(1).
     * @param cami Conte la nova navegabilitat per el graf. No pot tenir valor nul.
     */
    public void set_cami(String cami) {
        this.cami = cami;
    }

    /**
     * Modifica la descripcio del cami per la nova descripcio pasada per paramtre. El cost d'executar aquesta funcio es: O(1).
      * @param descripcio Conte la nova descripcio del cami.
     */
    public void set_descripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Retorna el cami que representa la navegabilitat per el graf. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna el cami que representa la navegabilitat per el graf.
     */
    public String get_cami() {
        return this.cami;
    }

    /**
     * Retorna la descripcio del cami. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna la descripcio del cami.
     */
    public String get_descripcio() {
        return this.descripcio;
    }

    /**
     * Retorna la longitud del cami. El cost d'executar aquesta funcio es: O(1).
     * @return Retorna la longitud del cami.
     */
    public Integer get_longitud(){
        return this.cami.length();
    }

    /**
     * Retorna un boolea si el cami es concatenable amb el cami passat per parametre. Per saber si un cami es
     * concatenable o no, el node final del cami implicit te que ser igual que el node inicial del cami pasat per
     * paramtre. El cost d'executar aquesta funcio es: O(1).
     * @param cami Conte una referencia a un altre cami. No pot ser nul.
     * @return Retorna cert si el cami implicit es concatenable amb el cami passat per parametre, altrament fals.
     */
    public Boolean es_concatenable(Cami cami){
        return this.cami.charAt(this.cami.length()-1) == cami.get_cami().charAt(0);
    }

    /**
     * Retorna un nou cami D que conte com a cami la concatenacio del cami implicit amb el cami pasat per paramtre.
     * La descripcio te un valor per defecte. El cost d'executar aquesta funcio es: O(n) on n es la longitud mes gran
     * entre els dos camins.
     * @param cami Conte una referencia a un altre cami. No pot ser nul.
     * @return Retorna un nou cami D que conte com a cami la concatenacio del cami implicit amb el cami pasat per paramtre.
     */
    public Cami concatenar(Cami cami){
        String camiDesti = cami.get_cami();
        String nouCami = this.cami + camiDesti.substring(1, camiDesti.length());
        return new Cami(nouCami, "No te descripcio");
    }

    /**
     * Retorna un nou cami D, que conte les mateixes dades que el cami implicit, inclos la descripcio pero amb el
     * cami invertit. El cost d'executar aquesta funcio es: O(n) on n es la longitud del cami.
     * @return Retorna un nou cami D, que conte les mateixes dades que el cami implicit, inclos la descripcio pero amb el
     * cami invertit.
     */
    public Cami invertir(){
        String camiInvertit = "";
        for (int i = this.cami.length() - 1; i >= 0; --i) camiInvertit += this.cami.charAt(i);
        return new Cami(camiInvertit, this.descripcio);
    }

    /**
     * Retorna un boolea que indica si el cami pasat com a parametre te una navegabilitat correcte per el graf.
     * @param cami Conte la navegabilitat per el graf. El cost d'executar aquesta funcio es: O(n) on n es la longitud
     *             del cami.
     * @returnn Retorna cert si el cami pasat per paramtre te una navegabilitat que no incompleix el graf, altrament
     * fals.
     */
    public static Boolean cami_valid(String cami){
        char[] aux=cami.toCharArray();
        for(int i=0; i<cami.length()-1;i++){
            if(aux[i]!='P' && aux[i]!='C' && aux[i]!='A' &&  aux[i]!='T') return false;
            else if(aux[i]=='P' && aux[i+1]=='P') return false;
            else if((aux[i]=='C' || aux[i]=='A' || aux[i]=='T') && (aux[i+1]!='P')) return false;

        }
        return true;
    }
}