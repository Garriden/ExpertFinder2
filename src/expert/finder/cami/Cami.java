package expert.finder.cami;

import java.io.Serializable;

/**
 *
 * @author adri, Col·laboracio: Ruben Bagan Benavides
 */

public class Cami implements Serializable{
    protected String cami;
    protected String descripcio;

    public Cami(String cami, String descripcio) {
        this.cami = cami;
        this.descripcio = descripcio;
    }

    public void set_cami(String cami) {
        this.cami = cami;
    }

    public void set_descripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String get_cami() {
        return this.cami;
    }

    public String get_descripcio() {
        return this.descripcio;
    }

    public Integer get_longitud(){
        return this.cami.length();
    }

    public Boolean es_concatenable(Cami cami){
        return this.cami.charAt(this.cami.length()-1) == cami.get_cami().charAt(0);
    }

    public Cami concatenar(Cami cami){
        String camiDesti = cami.get_cami();
        String nouCami = this.cami + camiDesti.substring(1, camiDesti.length());
        return new Cami(nouCami, "No te descripcio");
    }

    public Cami invertir(){
        String camiInvertit = "";
        for (int i = this.cami.length() - 1; i >= 0; --i) camiInvertit += this.cami.charAt(i);
        return new Cami(camiInvertit, this.descripcio);
    }

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