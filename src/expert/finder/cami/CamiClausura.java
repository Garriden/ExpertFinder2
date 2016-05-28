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

public class CamiClausura extends Cami{
    protected boolean teClausura;
    protected boolean clausuraDesactualitzada;

    // Pre:  cami != null, descripcio != null
    // Post: S'inicialitza un cami amb els valors passats per parametre
    // Cost: O(n)
    public CamiClausura(String cami, String descripcio, boolean teClausura) throws IllegalArgumentException {
        super(cami, descripcio);
        if (!this.cami_valid(this.cami)) {
            throw new IllegalArgumentException("Error: El cami que intentes afegir no es un camó vàlid.");
        }
        this.teClausura = teClausura;
        this.clausuraDesactualitzada = false;
    }

    // Pre:  Camicodificat != null. El cami codificat té que estar codificat de la següent forma:
    //       [Cami]|[Descripcio]|[Clausura]|[Actualitzada]
    // Post: S'inicialitza un cami amb els valors codificats del parametre.
    // Cost: O(n);
    public CamiClausura(String camiCodificat) {
        super("N/A","N/A");
        int posicioDescripcio = camiCodificat.indexOf('|');
        int posicioClausura = camiCodificat.indexOf('|', posicioDescripcio);
        int posicioActualitzada = camiCodificat.indexOf('|', posicioClausura);

        String cami = camiCodificat.substring(0, posicioDescripcio);
        if (!this.cami_valid(this.cami)) {
            throw new IllegalArgumentException("Error: El cami que intentes afegir no es un camó vàlid.");
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
    }
    
    // Pre:  Cert.
    // Post: Retorna un boolea indicant si aquest camí utilitza una matriu de clausura.
    // Cost: O(1)
    public boolean te_clausura() {
        return this.teClausura;
    }
    
    // Pre:  Cert.
    // Post: Actualitza el boolea indicant si utilitza matrius de clausura o no.
    // Cost: O(1)
    public void set_clausura(boolean teClausura) {
        this.teClausura = teClausura;
    }
    
    // Pre: Cert.
    // Post: Retorna un boolea que indicant si la matriu de clausura esta actualitzada o no
    // Cost: O(1)
    public boolean clausura_desactualitzada() {
        return this.clausuraDesactualitzada;
    }

    // Pre:  El camí implicit té que tindre la opció de matriu de clausura habilitada.
    // Post: Actualitza el boolea incicant si la matriu de clausura esta actualitzada o no.
    // Cost: O(1)
    public void set_actualitzar_clausura(boolean recalcularClausura) {
        if (recalcularClausura) {
            this.clausuraDesactualitzada = true;
        }
    }

    // Pre:  Cert
    // Post: Actualitza el cami del camí implicit per un camí vàlid passat per paràmetre.
    // Cost: Omegea(1) ~ O(n)
    public void set_cami(String cami) {
        if (!this.cami_valid(this.cami)) {
            throw new IllegalArgumentException("Error: El cami que intentes afegir no es un camó vàlid.");
        }
        super.set_cami(cami);
    }

    // Pre:  tipusNode = {'P','T','C','A'}; El cami implícit utilitza matrius de clausura.
    // Post: Actualitza el boolea que indica si la matriu de clausura esta desactualitzada o no depenen si el cami conte
    //       algun node del tipus passat per parametre.
    // Cost: Omega(1) ~ O(n)
    public void actualitzar_clausura(char tipusNode) {
        if (tipusNode == 'P') {
            this.clausuraDesactualitzada = true;
        }
        else {
            int i = 0;
            while (i < cami.length() && this.cami.charAt(i) != tipusNode) {
                ++i;
            }

            if (i < cami.length()) {
                this.clausuraDesactualitzada = true;
            }
        }
    }

    // Pre:  Cert
    // Post: Retorna una string amb la tota la informació codificada. El format de la codificació es el següent:
    //       [Cami]|[Descripcio]|[Clausura]|[Actualitzada]
    // Cost: O(1)
    public String codificar_cami() {
        String camiCodificat = this.cami + "|" + this.descripcio;

        if (this.teClausura) {
            camiCodificat = camiCodificat + "|Si";
        }
        else {
            camiCodificat = camiCodificat + "|No";
        }

        if (this.clausuraDesactualitzada) {
            camiCodificat = camiCodificat + "Si";
        }
        else {
            camiCodificat = camiCodificat + "|No";
        }

        return camiCodificat;
    }
}
