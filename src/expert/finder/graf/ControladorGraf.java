package expert.finder.graf;

import expert.finder.node.Node;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author: Ruben Bagan Benavides
 */

/**
 * La funcio d'aquesta clase es comunicar-se amb la capa de presentacio i el domini. Proporciona les funcions
 * necessaries per gestionar un graf sencer: poder modificar nodes, eliminar-los o afegir de nous. Tambe s'aplica amb
 * les relaciones entre nodes. Les funcions retornen estructures estandards de Java com poden ser String o
 * ArrayList<String> amb l'informacio del graf codificada.
 */

public class ControladorGraf {
    private Graf graf;

    private Node.TipusNode stringToTipusNode(String tipusNode) throws IllegalArgumentException {
        if (tipusNode.equalsIgnoreCase("AUTOR")) return Node.TipusNode.AUTOR;
        if (tipusNode.equalsIgnoreCase("TERME")) return Node.TipusNode.TERME;
        if (tipusNode.equalsIgnoreCase("PAPER")) return Node.TipusNode.PAPER;
        if (tipusNode.equalsIgnoreCase("CONFERENCIA")) return Node.TipusNode.CONFERENCIA;
        throw new IllegalArgumentException("Error: El tipus de node que has sel·leccionat es incorrecte.");
    }

    /**
     *  S'inicialitza el graf del controlador a partir del contingut del fitxer/s que actuen com a base de dades que
     *  estan indicats en la ruta pasaada com a parametre. Si el graf es nou, la ruta nomes contindra la carpeta on
     *  estan tots els fitxers .txt necessaris per inicialitzar el graf, en cas contrari apuntar a un graf
     *  enmmagatzemat com a objecte. El cost d'executar aquesta funcio es: O(1).
     * @param nouGraf Conte la ruta de la carpeta o conte la ruta absoluta del fitxer on estan enmmagatzemat el graf.
     * @param rutaFitxer Indica si es un nou graf o no.
     * @throws IOException Errors creats per part del controlador de persistencia del graf.
     */
    public void importar(boolean nouGraf, String rutaFitxer) throws IOException {
        ControladorPersistenciaGraf controladorPersistenciaGraf = new ControladorPersistenciaGraf();
        if (nouGraf) this.graf = controladorPersistenciaGraf.importar_graf_nou(rutaFitxer);
        else this.graf = controladorPersistenciaGraf.importar_graf_salvat(rutaFitxer);
    }

    /**
     * Exporta el graf enmmagatzemat en el controlador en un fitxer que es troba inclose en la ruta absoluta pasada
     * com a parametre. El cost d'executar aquesta funcio es: O(1).
     * @param rutaFitxer Conte la ruta absoluta inclos el nom del fitxer amb la seva extencio on enmmagatzemara el
     *                   graf. No pot tindre un valor nul.
     * @throws IOException Errors creats per part del controlador de persistencia del graf.
     */
    public void exportar(String rutaFitxer) throws IOException {
        ControladorPersistenciaGraf controladorPersistenciaGraf = new ControladorPersistenciaGraf();
        controladorPersistenciaGraf.exportar(rutaFitxer, this.graf);
    }

    /**
     *  S'afegeix un nou node al graf del controlador, amb els atributs del node inicialitzats amb els valors passats
     *  per parametre. El cost d'executar aquesta funcio es: O(1).
     * @param nom conte el nom que tindra el nou node.
     * @param tipusNode conte el tipus del nou node. Els valors poden ser autor, terme, conferencia o paper.
     * @throws IllegalArgumentException Errors creats per el graf o el tipus de node no es valid.
     */
    public void afegir_node(String nom, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        this.graf.afegir_node(tipus, nom);
    }

    /**
     * Elimina el node del graf del controlador amb el tipus pasat com a parametre que es troba en la posicio pasda per
     * parametre que actua com a identificador. El cost d'executar aquesta funcio es: O(n).
     * @param posicio conte l'identificador del node que es vol modificar
     * @param tipusNode conte el tipus del node a modificar. Els valors poden ser autor, terme, conferencia o paper.
     * @throws IllegalArgumentException Errors creats per el graf o el tipus de node no es valid.
     */
    public void eliminar_node(int posicio, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        Node n = this.graf.get_node(posicio, tipus);
        this.graf.eliminar_node(n);
    }

    /**
     * Modifica el nom del node del graf del controlador que es troba en la posicio que actua com a identificador. El
     * cost d'executar aquesta funcio es: O(1).
     * @param posicio conte l'identificador del node que es vol modificar
     * @param nouNom conte el nou nom del node.
     * @param tipusNode conte el tipus del node a modificar. Els valors poden ser autor, terme, conferencia o paper.
     * @throws IllegalArgumentException Errors creats per el graf o el tipus de node no es valid.
     */
    public void modificar_nom_node(int posicio, String nouNom, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        Node n = new Node(posicio, nouNom, tipus);
        this.graf.actualizar_node(n);
    }

    /**
     * Retorna un node codificat en la posicio que actua com a identificador pasada per parametre. El cost d'executar
     * aquesta funcio es: O(1).
     * @param posicio conte l'identificador del node que es vol obtenir.
     * @param tipusNode Conte el tipus del node a buscar. Els valors poden ser autor, terme, conferencia o paper.
     * @return Retorna un node codificat en la posicio pasada per parametre.
     * @throws IllegalArgumentException Errors creats per el graf o el tipus de node no es valid.
     */
    public String consultar_node(int posicio, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        Node n = this.graf.get_node(posicio, tipus);
        String node = n.get_id() + "|" + n.get_nom();
        return node;
    }

    /**
     * Retorna un boolea si existeix un node tipus i nom igual en el graf. El cost d'executar aquesta funcio es: O(n)
     * on n es el nombre de nodes que hi ha per aquest tipus.
     * @param nomNode Conte el nom del node a buscar.
     * @param tipusNode Conte el tipus del node a buscar. Els valors poden ser autor, terme, conferencia o paper.
     * @return Retorna cert si existeix un node amb el mateix nom i tipus, altrament fals.
     * @throws IllegalArgumentException Errors creats per el graf o el tipus de node no es valid.
     */
    public boolean existeix_node(String nomNode, String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        ArrayList<Node> nodes;
        if (tipus == Node.TipusNode.AUTOR) nodes = this.graf.get_autor();
        else if (tipus == Node.TipusNode.PAPER) nodes = this.graf.get_paper();
        else if (tipus == Node.TipusNode.TERME) nodes = this.graf.get_terme();
        else nodes = this.graf.get_conferencia();
        
        for (int i = 0; i < nodes.size(); ++i) {
            String nom = nodes.get(i).get_nom();
            if (nom.equalsIgnoreCase(nomNode)) return true;            
        }
        
        return false;        
    }

    /**
     * Retorna una llista de nodes d'un tipus. Aquest tipus node pot ser autor, paper, terme o conferencia i
     * l'informacio esta codificada de la seguent forma: [idNode]|[NomNode] El cost d'executar aquesta funcio es: O
     * (n). On n es el nombre de nodes del tipus pasat per parametre.
     * @param tipusNode Conte el tipus del node, que pot ser: autor, conferencia, terme o paper.
     * @return  Retorna una llista codificada dels nodes segons el tipus passat per paramtre.
     * @throws IllegalArgumentException Errors creats per el graf o el tipus de node no es valid.
     */
    public ArrayList<String> get_nodes(String tipusNode) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNode);
        ArrayList<Node> nodes;
        if (tipus == Node.TipusNode.AUTOR) nodes = this.graf.get_autor();
        else if (tipus == Node.TipusNode.PAPER) nodes = this.graf.get_paper();
        else if (tipus == Node.TipusNode.TERME) nodes = this.graf.get_terme();
        else nodes = this.graf.get_conferencia();

        ArrayList<String> nodesCodificats = new ArrayList<>(nodes.size());
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            nodesCodificats.add(n.get_id() + "|" + n.get_nom());
        }

        return nodesCodificats;
    }

    /**
     * Elimina una relacio entre un paper i un node diferent a paper. Al eliminar la relacio posa a 1 la interseccio
     * entre la fila i la columna que representen els id's dels nodes. El cost d'executar aquesta funcio es: O(1).
     * @param idNodeOrigen Conte l'identificador del node paper. Te que ser un valor major o igual que 0.
     * @param idNodeDesti Conte l'identificador del node que pot ser autor, terme o conferencia. Te que ser un valor
     *                    major o igual que 0.
     * @param tipusNodeDesti Conte el tipus del node desti, que pot ser: autor, conferencia, terme o paper.
     * @throws IllegalArgumentException Errors creats per el graf o el tipus de node no es valid.
     */
    public void afegir_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) throws IllegalArgumentException{
        Node.TipusNode tipus = stringToTipusNode(tipusNodeDesti);
        Node nodeOrigen = this.graf.get_node(idNodeOrigen, Node.TipusNode.PAPER);
        Node nodeDesti = this.graf.get_node(idNodeDesti, tipus);
        this.graf.afegir_aresta(nodeOrigen, nodeDesti);
    }

    /**
     * Elimina una relacio entre un paper i un node diferent a paper. Al eliminar la relacio posa a 0 la interseccio
     * entre la fila i la columna que representen els id's dels nodes. El cost d'executar aquesta funcio es: O(1).
     * @param idNodeOrigen Conte l'identificador del node paper. Te que ser un valor major o igual que 0.
     * @param idNodeDesti Conte l'identificador del node que pot ser autor, terme o conferencia. Te que ser un valor
     *                    major o igual que 0.
     * @param tipusNodeDesti Conte el tipus del node desti, que pot ser: autor, conferencia, terme o paper.
     * @throws IllegalArgumentException Errors creats per el graf o el tipus de node no es valid.
     */
    public void eliminar_relacio(int idNodeOrigen, int idNodeDesti, String tipusNodeDesti) throws IllegalArgumentException {
        Node.TipusNode tipus = stringToTipusNode(tipusNodeDesti);
        Node nodeOrigen = this.graf.get_node(idNodeOrigen, Node.TipusNode.PAPER);
        Node nodeDesti = this.graf.get_node(idNodeDesti, tipus);
        this.graf.eliminar_aresta(nodeOrigen, nodeDesti);
    }

    /**
     * Retorna una llista de tots els nodes amb que el node origen (paper) esta relacionat o no. L'informacio es
     * transmet codificada i amb el seguent format: [idNode]|[NomNode]|[TipusNode]. El resultat varia en funcio del
     * boolea indicat com a relacionats, si te valor cert retorna els relacionats i si es fals els que no estan
     * relacionats.
     * @param idNodeOrigen Conte l'identificador del node paper. Te que ser un valor major o igual que 0.
     * @param relacionats Conte un boolea indican si volem obtenir els nodes que estan relacionats en aquest cas te
     *                    que tindre valor cert i en cas contrari sera fals.
     * @return Retorna una llista de tots els nodes amb que el node origen (paper) esta relacionat.
     * @throws IllegalArgumentException Errors creats per el graf.
     */
    public ArrayList<String> consultar_relacio(int idNodeOrigen, boolean relacionats) throws IllegalArgumentException {
        ArrayList<String> nodesCodificats = new ArrayList<>();
        ArrayList<Node> nodes = this.graf.get_autor();
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            double valor = this.graf.get_paper_autor().get_valor(idNodeOrigen, n.get_id());
            if (valor == 1.0 && relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Autor");
            else if (valor == 0.0 && !relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Autor");
        }
        
        nodes = this.graf.get_terme();
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            double valor = this.graf.get_paper_terme().get_valor(idNodeOrigen, n.get_id());
            if (valor == 1.0 && relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Terme");
            else if (valor == 0.0 && !relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Terme");
        }
        
        nodes = this.graf.get_conferencia();        
        for (int i = 0; i < nodes.size(); ++i) {
            Node n = nodes.get(i);
            double valor = this.graf.get_paper_conferencia().get_valor(idNodeOrigen, n.get_id());
            if (valor == 1.0 && relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Conferencia");
            else if (valor == 0.0 && !relacionats) nodesCodificats.add(n.get_id() + "|" + n.get_nom() + "|Conferencia");
        }
        
        return nodesCodificats;
    }

    /**
     * Retorna una copia a una referencia d'un node que enmmagatzemat en el graf amb identificador i tipus passats
     * per parametres.  El cost d'executar aquesta funcio es: O(1)
     * @param idNode Conte l'identificador del node. Te que ser un valor major o igual que 0.
     * @param tipusNode Conte el tipus del node, que pot ser: autor, conferencia, terme o paper.
     * @return Retorna una copia d'un node amb identificador i tipus passats per parametres.
     * @throws IllegalArgumentException Errors creats per el graf.
     */
    public Node get_copia_node(int idNode, Node.TipusNode tipusNode) throws IllegalArgumentException {
        Node node = this.graf.get_node(idNode, tipusNode);        
        return new Node(node.get_id(), node.get_nom(), node.get_tipus_node());
    }

    /**
     * Retorna una referencia a l'instancia de graf.
     * @return Retorna una referencia a l'instancia de graf.
     */
    public Graf get_graf() {
        return this.graf;
    }

}
