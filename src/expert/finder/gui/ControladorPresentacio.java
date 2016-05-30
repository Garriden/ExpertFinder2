/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

import expert.finder.cami.ControladorCami;
import expert.finder.consulta.ControladorConsulta;
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
    private final ControladorConsulta controladorConsulta;
        
    public ControladorPresentacio() {
        this.controladorGraf = new ControladorGraf();
        this.controladorCami = new ControladorCami();
        this.controladorConsulta = new ControladorConsulta(this.controladorGraf, this.controladorCami);
    }
        
    public void importar_graf(String rutaFitxer, boolean esNou) throws IOException {
        this.controladorGraf.importar(esNou, rutaFitxer);
    }
    
    public void exportar_graf(String rutaFitxer)  throws IOException {
        this.controladorGraf.exportar(rutaFitxer);
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

    public void afegir_cami(String cami, String descripcio, boolean teClausura) throws ArrayIndexOutOfBoundsException {
        controladorCami.afegir_cami(cami, descripcio, teClausura);
    }

    public void modificar_cami(int posicio, String cami, String camiAntic, String descripcio, boolean teClausura, boolean recalcularClausura) throws ArrayIndexOutOfBoundsException, IllegalArgumentException{
        controladorCami.modificar_cami(posicio, cami, descripcio, teClausura, recalcularClausura);
    }

    public void eliminar_cami(int posicio) throws ArrayIndexOutOfBoundsException{
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
        this.controladorCami.graf_node_modificat(tipusNode.charAt(0));
    }

    public void modificar_nom_node(int posicio, String nouNom, String tipusNode) {
        this.controladorGraf.modificar_nom_node(posicio, nouNom, tipusNode);
    }

    public void afegir_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) throws IllegalArgumentException {
        this.controladorGraf.afegir_relacio(idNodeOrigen, idNodeDesti, tipusNodeDesti);
    }

    public boolean existeix_node(String nomNode, String tipusNode) throws IllegalArgumentException {
        return this.controladorGraf.existeix_node(nomNode, tipusNode);
    }

    public ArrayList<String> consultar_relacio(int idNodeOrigen, boolean relacionats) throws IllegalArgumentException {
        return this.controladorGraf.consultar_relacio(idNodeOrigen, relacionats);
    }

    public void eliminar_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) {
        this.controladorGraf.eliminar_relacio(idNodeOrigen, idNodeDesti, tipusNodeDesti);
        this.controladorCami.graf_relacio_modificat();
    }


    public void eliminar_consulta(int posicio) {
        this.controladorConsulta.eliminar_consulta(posicio);
    }


    public int get_nombre_consultes(){
        return controladorConsulta.get_nombre_consultes();
    }

    public ArrayList<String> get_consultes() {
        return this.controladorConsulta.get_consultes();
    }

    public ArrayList<String> executa_consulta_tipo1(int idNodeOrigen, String idCami, String descripcio){

        int idNodeO = idNodeOrigen;
        int idC = Integer.parseInt(idCami);
        return controladorConsulta.executar_consulta_tipusI(descripcio, idNodeO, idC-1);
    }

    public ArrayList<String> executa_consulta_tipo2(int idNodeOrigen, String idCami, double grauRellevancia, String descripcio){

        int idNodeO = idNodeOrigen;
        System.out.println(idCami);
        int idC = Integer.parseInt(idCami);

        return controladorConsulta.executar_consulta_tipusII(descripcio, idNodeO, idC-1, grauRellevancia);
    }

    public ArrayList<String> executa_consulta_tipo3(int idNodeOrigen, String idCami, int idNodeDesti1, int idNodeOrigen2, String descripcio){

        int idC = Integer.parseInt(idCami);


        return controladorConsulta.executar_consulta_tipusIII(descripcio, idNodeOrigen, idC-1, idNodeDesti1, idNodeOrigen2);
    }

    public void eliminar_ultima_consulta_executada(){
        controladorConsulta.eliminar_ultima_consulta_executada();
    }

    public ArrayList<String> get_resultat(int numeroConsulta){
        return controladorConsulta.get_resultat(numeroConsulta);
    }

    public void afegir_resultat_modificat(ArrayList<String> modificacio, int numeroConsulta){

        controladorConsulta.afegir_resultat_modificat(modificacio, numeroConsulta);
    }

    public void run() {
        new Menu(this).setVisible(true);
    }    
}
