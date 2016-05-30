package expert.finder.graf;

import expert.finder.node.Node;
import expert.finder.utils.Matriu;

import java.io.*;
import java.util.ArrayList;

/**
 * @Author: Ruben Bagan Benavides
 */

/**
 * La funcio d'aquesta clase es la de permetre importar i exportar grafs ja sigui en format objecte o a partir de
 * fitxers. Si la importacio es d'un nou graf, la carpeta te que contenir tots els fitxers necesaris de la base de
 * dades per inicialitzar un graf. Els fitxers dels nodes i els fitxers de les relacions, tots amb extencio .txt. En
 * canvi si es per recuperar un graf guardat com a objecte nomes cal aquell objecte unicament. Per exportar el graf
 * el guardara en la ruta absoluta indicada amb la extencio i nom definides en la propia ruta.
 */

public class ControladorPersistenciaGraf {
    private ArrayList<Integer> autorId;
    private ArrayList<Integer> paperId;
    private ArrayList<Integer> termeId;
    private ArrayList<Integer> conferenciaId;

    // Pre:  ruta != null, nomFitxer != null, Tipus node té que ser un tipus valid
    // Post: Retorna una llista de nodes del tipus passat per paramtre importats del fitxer resultant de la ruta +
    //       nomFitxer.
    // Cost: O(n) on N es el nombre d'entrades del fitxer
    private ArrayList<Node> importar_node(String ruta, String nomFitxer, Node.TipusNode tipusNode) throws IOException {
        if (ruta.charAt(ruta.length()-1) != '\\' && ruta.charAt(ruta.length()-1) != '/') ruta = ruta + "/";
        File rutaFitxerNodes = new File(ruta+nomFitxer+".txt");
        BufferedReader fitxerNode;

        try {
            fitxerNode = new BufferedReader(new InputStreamReader(new FileInputStream(rutaFitxerNodes),"ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No es pot obrir el fitxer: " + nomFitxer + ".txt en la ruta: "  +
                    rutaFitxerNodes.getAbsolutePath() + " perque no existeix o esta bloquejat per un altre programa.");
        }

        String node = fitxerNode.readLine();
        if (node == null) {
            throw new IOException("Error: La ruta del fitxer on estan enmmagatzemats els nodes esta buit: " + rutaFitxerNodes.toString());
        }

        int idNode = 0;
        ArrayList<Node> nodes = new ArrayList<>();
        while (node != null) {
            int posicioTab = node.indexOf("\t");
            if (posicioTab == -1) {
                throw new IOException("Error: El format d'entrada del fitxer on estan enmmagatzemats els nodes no es " +
                        "correcte [idNode][TABULACIO][Nom].");
            }

            Node n = new Node(idNode, (node.substring(posicioTab+1, node.length())), tipusNode);
            nodes.add(n);

            switch (tipusNode) {
                case AUTOR:
                    this.autorId.add(Integer.valueOf(node.substring(0, posicioTab)));
                    break;
                case CONFERENCIA:
                    this.conferenciaId.add(Integer.valueOf(node.substring(0, posicioTab)));
                    break;
                case PAPER:
                    this.paperId.add(Integer.valueOf(node.substring(0, posicioTab)));
                    break;
                case TERME:
                    this.termeId.add(Integer.valueOf(node.substring(0, posicioTab)));
                    break;
            }
            node = fitxerNode.readLine();
            ++idNode;
        }
        
        fitxerNode.close();
        return nodes;
    }

    // Pre:  ruta != null, nomFitxer != null, Tipus node té que ser un tipus valid
    // Post: Retorna una matriu esparsa amb les relacions entre paper x node que es del tipus (autor,terme,
    //       conferencia) amb les relacions indicades en el fitxer resultant de ruta + nomFitxer
    // Cost: O(n) on N es el nombre d'entrades del fitxer
    private Matriu importar_relacions(String ruta, String nomFitxer, Node.TipusNode tipusNode) throws IOException {
        if (ruta.charAt(ruta.length()-1) != '\\' && ruta.charAt(ruta.length()-1) != '/') ruta = ruta + "/";
        File rutaFitxerRelacions = new File(ruta+nomFitxer+".txt");
        BufferedReader fitxerRelacions;

        try {
            fitxerRelacions = new BufferedReader(new InputStreamReader(new FileInputStream(rutaFitxerRelacions),"ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No es pot obrir el fitxer: " + nomFitxer + ".txt en la ruta:  " +
                    rutaFitxerRelacions.getAbsolutePath() + " perque no existeix o esta bloquejat per un altre programa.");
        }

        String relacio = fitxerRelacions.readLine();
        if (relacio == null) {
            throw new IOException("Error: La ruta del fitxer on estan enmmagatzemats les relacions esta buit: " + rutaFitxerRelacions.toString());
        }

        int tamany = 0;
        switch (tipusNode) {
            case AUTOR:
                tamany = this.autorId.size();
                break;
            case CONFERENCIA:
                tamany = this.conferenciaId.size();
                break;
            case TERME:
                tamany = this.termeId.size();
                break;
        }

        Matriu m = new Matriu(this.paperId.size(), tamany);
        while (relacio != null) {
            int posicioTab = relacio.indexOf("\t");
            if (posicioTab == -1) {
                throw new IOException("Error: El format d'entrada del fitxer on estan enmmagatzemats les relacions no es " +
                        "correcte [idNodeOrigen][TABULACIO][idNodeDesti].");
            }
            int idOrigen = this.paperId.indexOf(Integer.parseInt(relacio.substring(0, posicioTab)));
            int idDesti = 0;
            switch (tipusNode) {
                case AUTOR:
                    idDesti = this.autorId.indexOf(Integer.parseInt(relacio.substring(posicioTab+1, relacio.length())));
                    break;
                case CONFERENCIA:
                    idDesti = this.conferenciaId.indexOf(Integer.parseInt(relacio.substring(posicioTab+1, relacio.length())));
                    break;
                case TERME:
                    idDesti = this.termeId.indexOf(Integer.parseInt(relacio.substring(posicioTab+1, relacio.length())));
                    break;
            }
            m.set_valor(idOrigen, idDesti, 1.0);
            relacio = fitxerRelacions.readLine();
        }
        
        fitxerRelacions.close();
        return m;
    }

    /**
     * Inicialitza el controlador de persistencia del graf. El cost d'executar aquesta funcio es: O(1).
     */
    public ControladorPersistenciaGraf() {
        this.autorId = new ArrayList<>();
        this.paperId = new ArrayList<>();
        this.termeId = new ArrayList<>();
        this.conferenciaId = new ArrayList<>();
    }

    /**
     *  Retorna una referencia a un graf inicialitzat amb els nodes existents en els diferents fitxers de nodes i
     *  conté les relacions especificades en els fitxers paper x [autor,terme,conferencia] El cost d'executar aquesta
     *  funcio es: O(n). on es el nombre d'entrades del fitxer mes gran.
     * @param ruta La ruta absoluta on estan tots els fitxers necessaris per importar: fitxers de nodes i fitxers de
     *             relacions. Tots amb extencio .txt
     * @return Retorna una referencia al graf que s'ha importat.
     * @throws IOException Errors format dels fitxers o els fitxers necessaris no existeixen.
     * @throws IllegalArgumentException La ruta te valor nul.
     */
    public Graf importar_graf_nou(String ruta) throws IOException, IllegalArgumentException {
        if (ruta == null) {
            throw new IllegalArgumentException("Error: La ruta no pot tenir un valor nul");
        }

        ArrayList<Node> autors = importar_node(ruta, "author", Node.TipusNode.AUTOR);
        ArrayList<Node> papers = importar_node(ruta, "paper", Node.TipusNode.PAPER);
        ArrayList<Node> conferencies = importar_node(ruta, "conf", Node.TipusNode.CONFERENCIA);
        ArrayList<Node> termes = importar_node(ruta, "term", Node.TipusNode.TERME);

        Matriu paper_autor = importar_relacions(ruta, "paper_author", Node.TipusNode.AUTOR);
        Matriu paper_conf = importar_relacions(ruta, "paper_conf", Node.TipusNode.CONFERENCIA);
        Matriu paper_term = importar_relacions(ruta, "paper_term", Node.TipusNode.TERME);

        return new Graf(paper_autor, paper_conf, paper_term, papers, termes, autors, conferencies);
    }

    /**
     * Retorna una referencia a un graf que s'habia enmmagatzemat com a objecte. Aquest graf conte una imatge de
     * l'estat en que es va exportar. El cost d'executar aquesta funcio es: O(1).
     * @param ruta Conte la ruta i el nom del fitxer amb la seva extencio on esta enmmagatzemat el graf com a objecte.
     * @return Retorna una referencia a un graf enmmagatzemat com a objecte en l'estat en que es va exportar.
     * @throws IOException El contingut del fitxer no es un graf o la ruta noe s valida o no s'ha pogut obrir el fitxer.
     */
    public Graf importar_graf_salvat(String ruta) throws IOException {
        FileInputStream fitxer = new FileInputStream(ruta);
        ObjectInputStream ois;
        Graf graf;
        try {
            ois = new ObjectInputStream(fitxer);
            graf = (Graf) ois.readObject();
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut importar el graf selvat de la ruta (" + ruta + "( perque o no" +
                    " existeix, o es utilitzat per un altre programa.");
        } catch (ClassNotFoundException e) {
            throw new IOException("Error: El fitxer binari on esta enmmagatzemat el graf en la ruta (" + ruta + ") " +
                    "no s'ha pogut obtenir el graf perquè el contingut del fitxer no es un graf.");
        }

        ois.close();
        fitxer.close();
        return graf;
    }

    /**
     * Exporta una un graf a la ruta absoluta pasada com a paramtre, la cual també conte el nom del fitxer i la seva
     * extencio. El graf s'enmmagatzema com a un objecte i es guarda en l'estat actual en que es fa l'exportacio.
     * El cost d'executar aquesta funcio es: O(1).
     * @param ruta Conte la ruta i el nom del fitxer amb la seva extencio on s'enmmagatzemara el graf com a objecte.
     * @param graf Conte una referencia a un graf instanciat
     * @throws IOException Algun paramtre te valor nul, o la ruta no es valida.
     */
    public void exportar(String ruta , Graf graf) throws IOException {
        if (ruta == null) {
            throw new IllegalArgumentException("Error: La ruta no pot tenir un valor nul");
        }
        if (graf == null) {
            throw new IllegalArgumentException("Error: La referencia al graf no pot ser nul·la");
        }
        FileOutputStream fout = new FileOutputStream(ruta);
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(fout);
            oos.writeObject(graf);
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut importar el graf selvat de la ruta: " + ruta +
                    "perque o no existeix, o es utilitzat per un altre programa.");
        }
        fout.close();
        oos.close();
    }
}
