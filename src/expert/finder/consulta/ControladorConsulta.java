package expert.finder.consulta;

import expert.finder.cami.CamiClausura;
import expert.finder.cami.ControladorCami;
import expert.finder.graf.ControladorGraf;
import expert.finder.node.Node;
import expert.finder.utils.Matriu;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author: Ruben Bagan Benavides
 */

/**
 * La funcio d'aquesta clase es la de comunicarse amb la capa de presentacio i la del domini oferint totes les
 * possibles funcions per permetre executar elements del domini i retornar aquesta informacio codificada en un tipus
 * estandard de java. Per parmetre aixi una independencia entre capes.
 */

public class ControladorConsulta {
    private ArrayList<Consulta> consultes;
    private HeteSim hetesim;
    private ControladorGraf controladorGraf;
    private ControladorCami controladorCami;

    private Node.TipusNode camiToTipusNode(char tipusNode) throws IllegalArgumentException {
        if (tipusNode == 'A') return Node.TipusNode.AUTOR;
        if (tipusNode == 'T') return Node.TipusNode.TERME;
        if (tipusNode == 'P') return Node.TipusNode.PAPER;
        return Node.TipusNode.CONFERENCIA;
    }

    private Node.TipusNode stringToTipusNode(String tipusNode) throws IllegalArgumentException {
        if (tipusNode.equalsIgnoreCase("AUTOR")) return Node.TipusNode.AUTOR;
        if (tipusNode.equalsIgnoreCase("TERME")) return Node.TipusNode.TERME;
        if (tipusNode.equalsIgnoreCase("PAPER")) return Node.TipusNode.PAPER;
        if (tipusNode.equalsIgnoreCase("CONFERENCIA")) return Node.TipusNode.CONFERENCIA;
        throw new IllegalArgumentException("Error: El tipus de node no existeix, te que ser de tipus: Autor, Terme, Paper o Conferencia");
    }

    private Resultat generar_resultat(Matriu m, Node.TipusNode tipusDesti) {
        HashMap<Integer, HashMap<Integer, Double>> data = m.get_hashmap();
        Resultat resultat = new Resultat();
        for (Integer fila : data.keySet()) {
            HashMap<Integer, Double> columnes = data.get(fila);
            for (Integer columna : columnes.keySet()) {
                double valor = columnes.get(columna);
                if (valor > 0.0) {
                    Node nodeDesti = this.controladorGraf.get_copia_node(columna, tipusDesti);
                    resultat.afegir_tuple(nodeDesti, valor);
                }
            }
        }

        return resultat;
    }

    private Resultat generar_resultat(ArrayList<String> resultatsCodificat) {
        Resultat resultat = new Resultat();
        for (int i = 0; i < resultatsCodificat.size(); ++i) {
            String resultatCodificat = resultatsCodificat.get(i);
            int posicioNom = resultatCodificat.indexOf('|');
            int posicioTipus = resultatCodificat.indexOf('|',posicioNom+1);
            int grau = resultatCodificat.indexOf('|',posicioTipus+1);

            int id = Integer.valueOf(resultatCodificat.substring(0, posicioNom));
            String nom = resultatCodificat.substring(posicioNom+1, posicioTipus);
            Node.TipusNode tipusNode = stringToTipusNode(resultatCodificat.substring(posicioTipus+1, grau));
            double grauRellevancia = Double.valueOf(resultatCodificat.substring(grau+1, resultatCodificat.length()));

            Node node = new Node(id, nom, tipusNode);
            resultat.afegir_tuple(node, grauRellevancia);
        }

        return resultat;
    }

    /**
     * Inicialitza una instancia del controlador de consultes. Aquesta instancia té que rebre una referencia a una
     * instancia ja inicialitzada del controlador de graf i d'un controlador de camins. El cost d'executar aquesta
     * funcio es: O(1)
     * @param controladorGraf Conte una referencia a una instancia del controlador de graf ja inicialitzada. No pot
     *                        tindre un valor nul.
     * @param controladorCami Conte una referencia a una instancia del controlador de camins ja inicialitzada. No pot
     *                        tindre un valor nul.
     */
    public ControladorConsulta(ControladorGraf controladorGraf, ControladorCami controladorCami) {
        this.hetesim = new HeteSim();
        this.consultes = new ArrayList<>();
        this.controladorGraf = controladorGraf;
        this.controladorCami = controladorCami;
    }

    /**
     * Executa una consulta de tipus I: Es una consulta que donat un node origen d'un cami i un cami trobar tots els
     * nodes desti relacionats amb aquest node origen amb el seu grau de rellevancia.
     * @param descripcio Cnote una breu descripcio de que fa la consulta.
     * @param idNodeOrigen Conte l'identificador del node origen que pertany al conjunt de nodes origen del primer node
     *                     del cami.
     * @param idCami Conte l'identificador del cami per realitzar la consulta. Te que ser un identificador valid.
     * @return Retorna un conjunt de tuples d'un resultat codificades.
     * @throws IllegalArgumentException Errors provocats per el controlador de cami i/o errors provacts per el
     * controlador de de graf.
     */
    public ArrayList<String> executar_consulta_tipusI(String descripcio, int idNodeOrigen, int idCami) throws IllegalArgumentException {
        CamiClausura cami = this.controladorCami.get_cami_sense_codificar(idCami);
        Node.TipusNode tipusOrigen = camiToTipusNode(cami.get_cami().charAt(0));
        Node.TipusNode tipusDesti = camiToTipusNode(cami.get_cami().charAt(cami.get_longitud()-1));

        Node nodeOrigen = this.controladorGraf.get_graf().get_node(idNodeOrigen, tipusOrigen);
        Matriu m = this.hetesim.relacio_node_matriu(nodeOrigen, cami, this.controladorGraf.get_graf());
        Resultat resultat = generar_resultat(m, tipusDesti);

        Consulta consulta = new Consulta(descripcio, nodeOrigen.get_nom(), cami.get_cami());
        consulta.set_resultat(resultat);
        this.consultes.add(consulta);

        return resultat.codificar_resultat();
    }

    /**
     * Executa una consulta de Tipus II: Es una consulta que donat un node origen d'un cami i un cami trobar tots els
     * nodes desti relacionats amb aquest node origen amb el seu grau de rellevancia. Pero a diferencia del tipus I
     * filtrem el resultat a partir d'un grau de rellevancia proporcionat.
     * @param descripcio Cnote una breu descripcio de que fa la consulta.
     * @param idNodeOrigen Conte l'identificador del node origen que pertany al conjunt de nodes origen del primer node
     *                     del cami.
     * @param idCami Conte l'identificador del cami per realitzar la consulta. Te que ser un identificador valid.
     * @param grauRellevancia Conte un valor entre [0..1]
     * @return Retorna un conjunt de tuples d'un resultat codificades.
     * @throws IllegalArgumentException Errors provocats per el controlador de cami i/o errors provacts per el
     * controlador de de graf.
     */
    public ArrayList<String> executar_consulta_tipusII(String descripcio, int idNodeOrigen, int idCami, double grauRellevancia) throws IllegalArgumentException {
        CamiClausura cami = this.controladorCami.get_cami_sense_codificar(idCami);
        Node.TipusNode tipusOrigen = camiToTipusNode(cami.get_cami().charAt(0));
        Node.TipusNode tipusDesti = camiToTipusNode(cami.get_cami().charAt(cami.get_longitud()-1));

        Node nodeOrigen = this.controladorGraf.get_graf().get_node(idNodeOrigen, tipusOrigen);
        Matriu m = this.hetesim.relacio_node_matriu(nodeOrigen, cami, this.controladorGraf.get_graf());
        Resultat resultat = generar_resultat(m, tipusDesti);
        resultat.filtrar_resultat(grauRellevancia);

        Consulta consulta = new Consulta(descripcio, nodeOrigen.get_nom(), cami.get_cami(), grauRellevancia);
        consulta.set_resultat(resultat);
        this.consultes.add(consulta);

        return resultat.codificar_resultat();
    }

    /**
     * Executa una consulta de tipus III: Es uan consulta que donat un node origen i un node desti en un cami, trobar
     * el seu grau de rellevancia que era desconegut. Proporcionar un tercer node, que sera tambe del conjunt de
     * nodes origen executar la consulta de Tipus II amb el mateix grau de rellevancia.
     * @param descripcio Cnote una breu descripcio de que fa la consulta.
     * @param idNodeOrigen Conte l'identificador del node origen que pertany al conjunt de nodes origen del primer node
     *                     del cami.
     * @param idCami Conte l'identificador del cami per realitzar la consulta. Te que ser un identificador valid.
     * @param idNodeDesti1 Conte l'identificador del node desti1 que pertany al conjunt de nodes desti de l'ultim node
     *                     del cami.
     * @param idNodeOrigen2 Conte l'identificador del node desti2 que pertany al conjunt de nodes origen del primer
     *                      node del cami.
     * @return Retorna un conjunt de tuples d'un resultat codificades.
     * @throws IllegalArgumentException Errors provocats per el controlador de cami i/o errors provacts per el
     * controlador de de graf.
     */
    public ArrayList<String> executar_consulta_tipusIII(String descripcio, int idNodeOrigen, int idCami, int
            idNodeDesti1, int idNodeOrigen2) throws IllegalArgumentException {
        CamiClausura cami = this.controladorCami.get_cami_sense_codificar(idCami);
        Node.TipusNode tipusOrigen = camiToTipusNode(cami.get_cami().charAt(0));
        Node.TipusNode tipusDesti1 = camiToTipusNode(cami.get_cami().charAt(cami.get_longitud()-1));

        Node nodeOrigen = this.controladorGraf.get_graf().get_node(idNodeOrigen, tipusOrigen);
        Node nodeDesti1 = this.controladorGraf.get_graf().get_node(idNodeDesti1, tipusDesti1);
        double grauRellevancia = this.hetesim.grau_relacio(nodeOrigen, nodeDesti1, cami, this.controladorGraf.get_graf());

        Node nodeDesti2 = this.controladorGraf.get_graf().get_node(idNodeOrigen2, tipusOrigen);
        Matriu m = this.hetesim.relacio_node_matriu(nodeDesti2, cami, this.controladorGraf.get_graf());
        Resultat resultat = generar_resultat(m, tipusDesti1);
        resultat.filtrar_resultat(grauRellevancia);

        Consulta consulta = new Consulta(descripcio, nodeOrigen.get_nom(), nodeDesti1.get_nom(), nodeDesti2.get_nom(), cami.get_cami(), grauRellevancia);
        consulta.set_resultat(resultat);
        this.consultes.add(consulta);

        return resultat.codificar_resultat();
    }

    /**
     *
     * @param resultat
     * @param posicioConsulta
     */
    public void afegir_resultat_modificat(ArrayList<String> resultat, int posicioConsulta){
        this.consultes.get(posicioConsulta).set_resultat(generar_resultat(resultat));
    }

    public void eliminar_ultima_consulta_executada() {
        this.consultes.remove(this.consultes.size() - 1);
    }

    public void ultima_consulta_modificada(ArrayList<String> resultatCodificat) {
        Consulta consulta = this.consultes.get(this.consultes.size() - 1);
        Resultat resultat = generar_resultat(resultatCodificat);
        consulta.set_resultat(resultat);
    }

    /**
     * Retorna el nombre total de consultes.
     * @return Retorna el nombre total de consultes.
     */
    public int get_nombre_consultes() {
        return this.consultes.size();
    }

    /**
     *
     * @param posicio
     * @return
     */
    public ArrayList<String> get_resultat(int posicio) {
        return this.consultes.get(posicio).get_resultat().codificar_resultat();
    }

    /**
     * Retorna un conjunt de consultes codificades de la seguent forma:
     * [Tipo Consulta]|[Descripcio]|[Norde Origen]|[Cami]|[Node Desti]|[Node Origen2]|[Grau rellevancia] El cost
     * d'executar aquesta funcio es: O(n) on n es el nombre de consultes.
     * @return Retorna un conjunt de consultes codificades.
     */
    public ArrayList<String> get_consultes() {
        ArrayList<String> consultesCodificades = new ArrayList<>(this.consultes.size());
        for (int i = 0; i < this.consultes.size(); ++i) {
            Consulta consulta = this.consultes.get(i);
            consultesCodificades.add(consulta.get_tipo_consulta()
                    + "|" + consulta.get_descripcio()
                    + "|" + consulta.get_node_origen()
                    + "|" + consulta.get_cami()
                    + "|" + consulta.get_node_desti()
                    + "|" + consulta.get_node_relacio()
                    + "|" + consulta.get_grau_rellevancia());
        }

        return consultesCodificades;
    }

    /**
     * Elimina del controlador la consulta que estroba en la posicio pasada per parametre.
     * @param posicio Conte la posicio de la consulta que s'ha d'eliminar de la llista. Te que tenir un valor major o
     *                igual que 0 i menor al nombre total de consultes del controlador.
     * @throws ArrayIndexOutOfBoundsException La posicio no esta dins d'un interval correcte
     */
    public void eliminar_consulta(int posicio) throws ArrayIndexOutOfBoundsException {
        if (posicio < 0 || posicio >= this.consultes.size()) {
            throw new ArrayIndexOutOfBoundsException("Error: La posicio de la consulta té que estar compresa entre el" +
                    " 1 i la mida de la taula");
        }
        this.consultes.remove(posicio);
    }

    public void calcular_matriu_clausura(int idCami) {

    }
}
