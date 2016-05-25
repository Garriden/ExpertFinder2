/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.cami;

/**
 *
 * @author Phenom
 */
public class CamiClausura extends Cami{
    private boolean teClausura;
    private boolean clausuraDesactualitzada;
    public CamiClausura(String cami, String descripcio, boolean teClausura, boolean clausuraDesactualitzada) {
        super(cami, descripcio);
        this.teClausura = teClausura;
        this.clausuraDesactualitzada = clausuraDesactualitzada;
    }
    
    // Pre:  Cert
    // Post: Retorna un boolea que ens indica si aquest camí utilitza una matriu de clausura
    // Cost: O(1)
    public boolean te_clausura() {
        return this.teClausura;
    }
    
    // Pre:  Cert
    // Post: Actualitza el boolea que indica si utilitza matrius de clausura o no.
    // Cost: O(1)
    public void set_clausura(boolean teClausura) {
        this.teClausura = teClausura;
    }
    
    // Pre:  Cert
    // Post: Retorna un boolea que ens indica si aquest camí utilitza una matriu de clausura i que si aquesta esta desactualitzada
    // Cost: O(1)
    public boolean clausura_desactualitzada() {
        return this.clausuraDesactualitzada;
    }
    
    // Pre:  Cert
    // Post: Actualitza el boolea que indica si la matriu de clausura esta desactualitzada o no.
    // Cost: O(1)
    public void set_actualitzar_clausura(boolean clausuraDesactualitzada) {
        this.clausuraDesactualitzada = clausuraDesactualitzada;
    }
    
    // Pre:  Cert
    // Post: Actualitza el boolea que indica si la matriu de clausura esta desactualitzada o no depenen
    //       si el cami conte algun node del tipus passat per parametre.
    // Cost: O(n)
    public void actualitzar_clausura(char tipusNode) {
        if (tipusNode == 'P') this.clausuraDesactualitzada = true;
        else {
            int i = 0;
            while (i < cami.length() && this.cami.charAt(i) != tipusNode) {
                ++i;
            }
            if (i < cami.length()) this.clausuraDesactualitzada = true;
        }
    }
}
