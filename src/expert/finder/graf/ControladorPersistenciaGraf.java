package expert.finder.graf;

import expert.finder.node.Node;
import expert.finder.utils.Matriu;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Ruben Bagan Benavides on 14/05/2016.
 */

public class ControladorPersistenciaGraf {
    private ArrayList<Integer> autorId;
    private ArrayList<Integer> paperId;
    private ArrayList<Integer> termeId;
    private ArrayList<Integer> conferenciaId;
    private Graf graf;

    private ArrayList<Node> importar_node(String ruta, String nomFitxer, Node.TipusNode tipusNode) throws IOException {
        File rutaFitxerNodes = new File(ruta+nomFitxer+".txt");
        File rutaTaulaId = new File(ruta+nomFitxer+"taula.txt");
        BufferedReader fitxerNode;
        BufferedWriter fitxerTaula;

        try {
            fitxerNode = new BufferedReader(new InputStreamReader(new FileInputStream(rutaFitxerNodes),"ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No es pot obrir el fitxer: " + nomFitxer + ".txt en la ruta: " + ruta + " perque" +
                    " no existeix o esta bloquejat per un altre programa.");
        }

        try {
            fitxerTaula = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaTaulaId),"ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut crear la taula de ID's en la ruta: " + ruta);
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

            fitxerTaula.write(node.substring(0, posicioTab));
            fitxerTaula.write('\t');
            fitxerTaula.write(String.valueOf(idNode));
            fitxerTaula.newLine();

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
        fitxerTaula.close();

        return nodes;
    }

    private Matriu importarRelacions(String ruta, String nomFitxer, Node.TipusNode tipusNode) throws IOException {
        File rutaFitxerRelacions = new File(ruta+nomFitxer+".txt");
        BufferedReader fitxerRelacions;

        try {
            fitxerRelacions = new BufferedReader(new InputStreamReader(new FileInputStream(rutaFitxerRelacions),"ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No es pot obrir el fitxer: " + nomFitxer + ".txt en la ruta: " + ruta + " perque" +
                    " no existeix o esta bloquejat per un altre programa.");
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

    public ControladorPersistenciaGraf() {
        this.autorId = new ArrayList<>();
        this.paperId = new ArrayList<>();
        this.termeId = new ArrayList<>();
        this.conferenciaId = new ArrayList<>();
    }

    public Graf importar_graf_nou(String ruta) throws IOException {
        ArrayList<Node> autors = importar_node(ruta, "author", Node.TipusNode.AUTOR);
        ArrayList<Node> papers = importar_node(ruta, "paper", Node.TipusNode.PAPER);
        ArrayList<Node> conferencies = importar_node(ruta, "conf", Node.TipusNode.CONFERENCIA);
        ArrayList<Node> termes = importar_node(ruta, "term", Node.TipusNode.TERME);

        Matriu paper_autor = importarRelacions(ruta, "paper_author", Node.TipusNode.AUTOR);
        Matriu paper_conf = importarRelacions(ruta, "paper_conf", Node.TipusNode.CONFERENCIA);
        Matriu paper_term = importarRelacions(ruta, "paper_term", Node.TipusNode.TERME);

        this.graf = new Graf(paper_autor, paper_conf, paper_term, papers, termes, autors, conferencies);
        return this.graf;
    }

    public Graf importar_graf_salvat(String ruta) throws IOException {
        FileInputStream fitxer = new FileInputStream(ruta + "graf.sav");
        ObjectInputStream ois;
        try {

            ois = new ObjectInputStream(fitxer);
            this.graf = (Graf) ois.readObject();
        }catch (IOException e) {
            throw new IOException("Error: No s'ha pogut importar el graf selvat de la ruta: " + ruta + "graf.sav " +
                    "perque o no existeix, o es utilitzat per un altre programa.");
        } catch (ClassNotFoundException e) {
            throw new IOException("Error: El fitxer binari on esta enmmagatzemat el graf en la ruta: " + ruta + "graf.sav " +
                    "no s'ha pogut obtenir el graf perquè el contingut del fitxer no es un graf.");
        }

        ois.close();
        fitxer.close();
        return this.graf;
    }

    public void exportar(String ruta , Graf graf) throws IOException {
        FileOutputStream fout = new FileOutputStream(ruta+"graf.sav");
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(fout);
            oos.writeObject(graf);
        }catch (IOException e) {
            throw new IOException("Error: No s'ha pogut importar el graf selvat de la ruta: " + ruta + "\\graf.sav " +
                    "perque o no existeix, o es utilitzat per un altre programa.");
        }
        fout.close();
        oos.close();
    }


    /*
    ///////////////////////////////////////////////////////// ESTO ES PARA COMPROVAR SI IMPORTA O EXPORTA BIEN

            //////////////////////////////////////////////// VOLCADO TAMPORAL //////////////////////////////////////////////


        /////////////////////////// AUTOR ////////////////////////////
        //////////////////////////////////////////////////////////////
        File rutaTemporal = new File(ruta+"autor_graf.txt");
        BufferedWriter fitxerGraf;

        try {
            fitxerGraf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaTemporal), "ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut crear la taula de ID's en la ruta: " + ruta);
        }

        for (int i = 0; i < autorId.size(); ++i) {
            fitxerGraf.write(String.valueOf(autorId.get(i)));
            fitxerGraf.write('\t');
            fitxerGraf.write(autors.get(i).get_nom());
            fitxerGraf.newLine();
        }
        fitxerGraf.close();


        /////////////////////////// TERME ////////////////////////////
        //////////////////////////////////////////////////////////////
        rutaTemporal = new File(ruta+"terme_graf.txt");
        try {
            fitxerGraf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaTemporal), "ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut crear la taula de ID's en la ruta: " + ruta);
        }

        for (int i = 0; i < termeId.size(); ++i) {
            fitxerGraf.write(String.valueOf(termeId.get(i)));
            fitxerGraf.write('\t');
            fitxerGraf.write(termes.get(i).get_nom());
            fitxerGraf.newLine();
        }
        fitxerGraf.close();


        /////////////////////////// CONFERENCIA ////////////////////////////
        //////////////////////////////////////////////////////////////
        rutaTemporal = new File(ruta+"conf_graf.txt");
        try {
            fitxerGraf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaTemporal), "ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut crear la taula de ID's en la ruta: " + ruta);
        }

        for (int i = 0; i < conferenciaId.size(); ++i) {
            fitxerGraf.write(String.valueOf(conferenciaId.get(i)));
            fitxerGraf.write('\t');
            fitxerGraf.write(conferencies.get(i).get_nom());
            fitxerGraf.newLine();
        }
        fitxerGraf.close();

        /////////////////////////// PAPER ////////////////////////////
        //////////////////////////////////////////////////////////////
        rutaTemporal = new File(ruta+"paper_graf.txt");
        try {
            fitxerGraf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaTemporal), "ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut crear la taula de ID's en la ruta: " + ruta);
        }

        for (int i = 0; i < paperId.size(); ++i) {
            fitxerGraf.write(String.valueOf(paperId.get(i)));
            fitxerGraf.write('\t');
            fitxerGraf.write(papers.get(i).get_nom());
            fitxerGraf.newLine();
        }
        fitxerGraf.close();

        ////////////////////////////// RELACIONES

        rutaTemporal = new File(ruta+"paper_autor_graf.txt");
        try {
            fitxerGraf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaTemporal), "ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut crear la taula de ID's en la ruta: " + ruta);
        }

        for (int i = 0; i < paper_autor.get_nombre_files(); ++i) {
            for (int j = 0; j < paper_autor.get_nombre_columnes(); ++j) {
                if (paper_autor.get_valor(i,j) == 1.0) {
                    fitxerGraf.write(String.valueOf(paperId.get(i)));
                    fitxerGraf.write('\t');
                    fitxerGraf.write(String.valueOf(autorId.get(j)));
                    fitxerGraf.newLine();
                }
            }
        }
        fitxerGraf.close();

        rutaTemporal = new File(ruta+"paper_conf_graf.txt");
        try {
            fitxerGraf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaTemporal), "ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut crear la taula de ID's en la ruta: " + ruta);
        }

        for (int i = 0; i < paper_conf.get_nombre_files(); ++i) {
            for (int j = 0; j < paper_conf.get_nombre_columnes(); ++j) {
                if (paper_conf.get_valor(i,j) == 1.0) {
                    fitxerGraf.write(String.valueOf(paperId.get(i)));
                    fitxerGraf.write('\t');
                    fitxerGraf.write(String.valueOf(conferenciaId.get(j)));
                    fitxerGraf.newLine();
                }
            }
        }
        fitxerGraf.close();

        rutaTemporal = new File(ruta+"paper_term_graf.txt");
        try {
            fitxerGraf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaTemporal), "ISO-8859-15"));
        } catch (IOException e) {
            throw new IOException("Error: No s'ha pogut crear la taula de ID's en la ruta: " + ruta);
        }

        for (int i = 0; i < paper_term.get_nombre_files(); ++i) {
            for (int j = 0; j < paper_term.get_nombre_columnes(); ++j) {
                if (paper_term.get_valor(i,j) == 1.0) {
                    fitxerGraf.write(String.valueOf(paperId.get(i)));
                    fitxerGraf.write('\t');
                    fitxerGraf.write(String.valueOf(termeId.get(j)));
                    fitxerGraf.newLine();
                }
            }
        }
        fitxerGraf.close();

        FileOutputStream fout = new FileOutputStream(ruta+"graf.sav");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(graf);

        FileInputStream fin = new FileInputStream(ruta+"graf.sav");
        ObjectInputStream ois = new ObjectInputStream(fin);
        try {
            graf = (Graf) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

     */

}
