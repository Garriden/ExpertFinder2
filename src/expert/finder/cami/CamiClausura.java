/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.cami;

/**
 *
 * @author Ruben Bagan Benavides
 */

/**
 * La funcio d'aquesta clase es ampliar el contingut d'un cami. L'amplacio consta que un cami pot tenir una matriu de
 * clausura associada i saber si esta actualitzada. Poder codificar un cami i construir un cami a partir d'un string
 * codificat.
 */
public class CamiClausura extends Cami{
    private static int idFitxerClausura = 0;
    protected boolean teClausura;
    protected boolean clausuraDesactualitzada;


    /**
     * Inicialitza una instancia d'un cami clausura on inicialitza els seus atributs com son: el cami, una
     * descripcio, i saber si utilitza matriu de clausura. Per defecte la matriu de clausura esta actualitzada ja que
     * no s'han pogut fer modificacions. El cost d'executar aquesta funcio es: O(n) on n es el numero de caracters
     * del cami.
     * @param cami es un string que conte la navegabilitat entre diferents nodes del graf. cami no apunta a una
     *             referencia nul-la.
     * @param descripcio proporciona una descripcio de quina es la navegabilitat del cami. descripcio no apunta a una
     *                   referencia nul-la
     * @param teClausura indica si el cami tindra una matriu de clausura associada.
     * @throws IllegalArgumentException El cami passat per parametre que intentes afegir no es cami valid.
     */
    public CamiClausura(String cami, String descripcio, boolean teClausura) throws IllegalArgumentException {
        super(cami, descripcio);
        if (!this.cami_valid(this.cami)) {
            throw new IllegalArgumentException("Error: El cami que intentes afegir no es un cami vàlid.");
        }
        this.teClausura = teClausura;
        this.clausuraDesactualitzada = false;
        ++this.idFitxerClausura;
    }

    /**
     * Inicialitza una instancia d'un cami clausura on inicialitza els seus atributs com son: el cami, una
     * descripcio, i saber si utilitza matriu de clausura. Per defecte la matriu de clausura esta actualitzada ja que
     * no s'han pogut fer modificacions. A diferencia del constructor amb parametres, aquest rep els parametres
     * codificats amb el següent format: [cami]|[descripcio]|[clausura]|[actualitzada] El cost d'executar aquesta
     * funcio es: O(n) on n es el numero de caracters del cami.
     * @param camiCodificat conte un cami codificat amb el format: [cami]|[descripcio]|[clausura]|[actualitzada]
     * @throws IllegalArgumentException El cami passat per parametre que intentes afegir no es cami valid.
     */
    public CamiClausura(String camiCodificat) throws IllegalArgumentException {
        super("N/A","N/A");
        int posicioDescripcio = camiCodificat.indexOf('|');
        int posicioClausura = camiCodificat.indexOf('|', posicioDescripcio+1);
        int posicioActualitzada = camiCodificat.indexOf('|', posicioClausura+1);

        String cami = camiCodificat.substring(0, posicioDescripcio);
        if (!this.cami_valid(cami)) {
            throw new IllegalArgumentException("Error: El cami que intentes afegir no es un cami valid.");
        }
        String descripcio = camiCodificat.substring(posicioDescripcio+1, posicioClausura);
        boolean clausura = false;
        if(camiCodificat.substring(posicioClausura+1, posicioActualitzada).equalsIgnoreCase("Si")) {
            clausura = true;
        }
        boolean actualitzada = false;
        if (camiCodificat.substring(posicioActualitzada+1, camiCodificat.length()).equalsIgnoreCase("Si")) {
            actualitzada = true;
        }

        this.cami = cami;
        this.descripcio = descripcio;
        this.teClausura = clausura;
        this.clausuraDesactualitzada = actualitzada;
        ++this.idFitxerClausura;
    }

    /**
     * Retorna una boolea indicant si el cami utilitza matriu de clausura. El cost d'executar aquesta funcio es: O(1)
     * @return Retorna una boolea indicant si el cami utilitza matriu de clausura.
     */
    public boolean te_clausura() {
        return this.teClausura;
    }

    /**
     * Actualitza si el cami utilitza o no matriu de clausura. El cost d'executar aquesta funcio es: O(1)
     * @param teClausura Indica si el cami utilitza o no matriu de clausura.
     */
    public void set_clausura(boolean teClausura) {
        this.teClausura = teClausura;
    }

    /**
     * Retorna una boolea indicant si el cami que utilitza matriu de clausura esta actualitzada o no. El cost
     * d'executar aquesta funcio es: O(1)
     * @return Retorna una boolea indicant si el cami utilitza matriu de clausura esta actualitzada o no.
     */
    public boolean clausura_desactualitzada() {
        return this.clausuraDesactualitzada;
    }

    /**
     * Actualitza el boolea incicant si la matriu de clausura esta actualitzada o no. El camí implicit te que tindre
     * la opcio de matriu de clausura habilitada. El cost d'executar aquesta funcio es: O(1)
     * @param recalcularClausura Indica si el cami te actualitzada o no la matriu de clausura.
     */

    public void set_actualitzar_clausura(boolean recalcularClausura) {
        if (this.teClausura && recalcularClausura) {
            this.clausuraDesactualitzada = false;
        }
    }

    /**
     * Retorna l'identificado del fitxer on esta enmmagatzemada la matriu de clausura del cami. El cost d'executar
     * aquesta funcio es: O(1)
     * @return Retorna l'identificado del fitxer on esta enmmagatzemada la matriu de clausura del cami.
     */
    public int get_id_fitxer_clausura() {
        return idFitxerClausura;
    }

    /**
     * Actualitza el cami del cami implicit per el nou cami passat per parametre. El cost d'executar aquesta funcio
     * es: Omega(1) ~ O(n) on n es el numero de caracters del cami.
     * @param cami Conte el nou cami que sera substituit. No pot tindre un valor nul
     * @throws IllegalArgumentException El cami que intentes afegir no es un camó vàlid.
     */
    public void set_cami(String cami) throws IllegalArgumentException {
        if (!this.cami_valid(this.cami)) {
            throw new IllegalArgumentException("Error: El cami que intentes afegir no es un camó vàlid.");
        }
        super.set_cami(cami);
    }

    /**
     * Actualitza el boolea que indica si la matriu de clausura esta desactualitzada o no depenen si el cami conte
     * algun node del tipus passat per parametre. El cost d'executar aquesta funcio es: Omega(1) ~ O(n) on n es el
     * numero de caracters del cami implicit.
     * @param tipusNode conte una lletra capital de cada tipus de node = {'P','T','C','A'}; El cami implicit utilitza
     *                  matrius de clausura.
     */
    public void actualitzar_clausura(char tipusNode) {
        if (this.teClausura) {
            if (tipusNode == 'P') {
                this.clausuraDesactualitzada = true;
            } else {
                int i = 0;
                while (i < cami.length() && this.cami.charAt(i) != tipusNode) {
                    ++i;
                }

                if (i < cami.length()) {
                    this.clausuraDesactualitzada = true;
                }
            }
        }
    }

    /**
     * Retorna una string amb la tota la informacio codificada. El format de la codificacio es el seguent:
     *         [Cami]|[Descripcio]|[Clausura]|[Actualitzada]. El cost d'executar aquesta funcio es: O(1)
     * @return Retorna una string amb la tota la informacio codificada. El format de la codificacio es el seguent:
     *         [Cami]|[Descripcio]|[Clausura]|[Actualitzada]
     */
    public String codificar_cami() {
        String camiCodificat = this.cami + "|" + this.descripcio;

        if (this.teClausura) {
            camiCodificat = camiCodificat + "|Si";
        }
        else {
            camiCodificat = camiCodificat + "|No";
        }

        if (this.clausuraDesactualitzada) {
            camiCodificat = camiCodificat + "|Si";
        }
        else {
            camiCodificat = camiCodificat + "|No";
        }

        return camiCodificat;
    }
}
