/**
 *
 * @author adri
 */

public class Consulta {
    private String descripcio;
    private Resultat resultat;

    public Consulta(String descripcio, Resultat resultat) {
        this.descripcio = descripcio;
        this.resultat = resultat;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public Resultat getResultat() {
        return resultat;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setResultado(Resultat resultat) {
        this.resultat = resultat;
    }

}