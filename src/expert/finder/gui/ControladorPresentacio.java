/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

import expert.finder.cami.ControladorCami;
import expert.finder.graf.ControladorGraf;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author marc.garrido.casas
 */
public class ControladorPresentacio {
    private final ControladorGraf controladorGraf;
    private final ControladorCami controladorCami;
        
    public ControladorPresentacio() {
        this.controladorGraf = new ControladorGraf();
        this.controladorCami = new ControladorCami();
    }
        
    public void importar_graf(String rutaFitxer, boolean esNou) throws IOException {
        this.controladorGraf.importar(esNou, rutaFitxer);
    }
    
    public void exportar_graf(String rutaFitxer)  throws IOException {
        this.controladorGraf.exportar(rutaFitxer);
    }
    public int get_nombre_camins(){
        return this.controladorCami.get_nombre_camins();
    }
    public ArrayList<String> get_camins(){
        return controladorCami.get_camins();
    }
    
    public String get_cami(int pos){
        return controladorCami.get_cami(pos);
    }
    
    public void importar_cami(String rutaFitxer) throws IllegalArgumentException, IOException, ClassNotFoundException{
        controladorCami.importar_camins(rutaFitxer);
    }
    
    public void exportar_cami(String rutaFitxer) throws IOException{
        controladorCami.exportar_camins(rutaFitxer);
    }
    
    public void afegir_cami(String cami, String descripcio) throws ArrayIndexOutOfBoundsException {
        controladorCami.afegir_cami(cami, descripcio);
    }
    
    public void modificar_cami(int posicio, String cami) throws ArrayIndexOutOfBoundsException, IllegalArgumentException{
        controladorCami.modificar_cami(posicio, cami);
    }
    
    public void modificar_descripcio(int posicio, String Descripcio){
        controladorCami.modificar_descripcio(posicio, Descripcio);
    }
    
    public void eliminar_cami(int posicio){
        controladorCami.eliminar_cami(posicio);
    }
        
    public ArrayList<String> get_nodes(String tipusNode) throws IllegalArgumentException {
        return this.controladorGraf.get_nodes(tipusNode);
    }
    
    public void afegir_node(String nom, String tipusNode) {
        this.controladorGraf.afegir_node(nom, tipusNode);
    }
    
    public void eliminar_node(int posicio, String tipusNode) {
        this.controladorGraf.eliminar_node(posicio, tipusNode);
    }
    
    public void modificar_nom_node(int posicio, String nouNom, String tipusNode) {
        this.controladorGraf.modificar_nom_node(posicio, nouNom, tipusNode);
    }
            
    public void afegir_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) throws IllegalArgumentException {
        this.controladorGraf.afegir_relacio(idNodeOrigen, idNodeDesti, tipusNodeDesti);
    }
    
    public ArrayList<String> consultar_relacio(int idNodeOrigen, String tipusNodeDesti) throws IllegalArgumentException {
        return this.controladorGraf.consultar_relacio(idNodeOrigen, tipusNodeDesti);
    }
    
    public void eliminar_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) {
        this.controladorGraf.eliminar_relacio(idNodeOrigen, idNodeDesti, tipusNodeDesti);
    }

    
    
    public void run() {
        new Menu(this).setVisible(true);
    }
    
    
    
    
}
