package expert.finder.consulta;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ruben Bagan Benavides on 24/04/2016.
 */

public class ControladorConsultaDriver {
    /*
    public static ControladorConsulta controladorConsulta;
    public static ControladorCami controladorCami;
    public static Scanner scan = new Scanner(System.in);
    public static Graf graf;


    public static int llegir_enter(int minim, int maxim) {
        int n = scan.nextInt();
        while (n < minim || n > maxim) {
            System.out.println("Error: tens que escriure un nombre entre " + minim + " i " + maxim);
            n = scan.nextInt();
        }
        return n;
    }

    public static Node.TipusNode string_to_tipus_node(String tipusNode) {
        switch (tipusNode.toUpperCase()) {
            case "AUTOR": return Node.TipusNode.AUTOR;
            case "TERME": return Node.TipusNode.TERME;
            case "PAPER": return Node.TipusNode.PAPER;
            case "CONFERENCIA": return Node.TipusNode.CONFERENCIA;
            default: return null;
        }
    }

    public static boolean validar_node_cami(Node.TipusNode tipus, char c) {
        char charTipus = '0';
        if (tipus == Node.TipusNode.CONFERENCIA) charTipus = 'C';
        else if(tipus == Node.TipusNode.PAPER) charTipus = 'P';
        else if(tipus == Node.TipusNode.TERME) charTipus = 'T';
        else charTipus = 'A';
        if (c == charTipus) return true;
        return false;
    }

    public static int llistar_nodes(Node.TipusNode tipus) {
        ArrayList<Node> nodes = new ArrayList();
        switch (tipus) {
            case AUTOR:
                nodes = graf.get_autor();
                break;
            case CONFERENCIA:
                nodes = graf.get_conferencia();
                break;
            case TERME:
                nodes = graf.get_terme();
                break;
            case PAPER:
                nodes = graf.get_paper();
                break;
        }

        for (int i = 0; i < nodes.size(); ++i) {
            System.out.println("Node amb id: " + (i+1) + " Nom: " + nodes.get(i).get_nom());
        }

        return nodes.size();
    }

    public static void menu() {
        System.out.println("1\t Consulta I: Calcular el grau de rellevancia donat un node en un cami.");
        System.out.println("2\t Consulta II: Calcular el grau de rellevancia donat un node en un cami a partir d'un grau " +
                "de rellevancia amb un cert error.");
        System.out.println("3\t Consulta III: Cercar experts que tinguin un mateix grau de rellevancia amb un cert error " +
                "a partir de dos nodes");
        System.out.println("4\t Afegir cami");
        System.out.println("5\t Eliminar cami");
        System.out.println("6\t Obtenir llista de camins");
        System.out.println("7\t Afegir node");
        System.out.println("8\t Eliminar node");
        System.out.println("9\t Afegir aresta");
        System.out.println("10\t Eliminar aresta");
        System.out.println("11\t Obtenir llista erestes");
        System.out.println("0\t Sortir");
    }

    public static String crear_cami() {
        System.out.println("Introdueix els diferents nodes que formaran el nou cami <Autor,Paper,Terme,Conferencia>, " +
                "cada nom de tipus de node seguit de la tecla enter. Escriu FI per finalitzar: ");
        String node = scan.nextLine();
        String cami = "";
        while (!node.equalsIgnoreCase("FI")) {
            switch (node.toUpperCase()) {
                case "AUTOR":
                    cami = cami + "A";
                    break;
                case "TERME":
                    cami = cami + "T";
                    break;
                case "PAPER":
                    cami = cami + "P";
                    break;
                case "CONFERENCIA":
                    cami = cami + "C";
                    break;
                default: System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
            }
            node = scan.nextLine();
        }
        return cami;
    }

    public static void print_matriu(Matriu m) {
        for (int i = 0; i < m.get_nombre_files(); i++) {
            for (int j = 0; j < m.get_nombre_columnes(); j++) {
                System.out.printf("%.2f\t", m.get_valor(i, j));
            }
            System.out.println();
        }
    }

    public static void afegir_cami() {
        scan.nextLine();
        String cami = crear_cami();
        if (!cami.equals("")) {
            Cami c = new Cami();
            if (c.camiValid(cami)) {
                System.out.println("Introdueix una descripcio per aquest cami: ");
                String descripcio = scan.nextLine();
                c = new Cami(cami, descripcio);
                if (controladorCami.afegir_cami(c) == 0) {
                    System.out.println("S'ha afegit un cami correctament!");
                }
                else System.out.println("No s'ha pogut afegir el cami perquè ja existia");

            } else System.out.println("El cami introduit no es valid");
        }
    }

    public static int obtenir_llista_camins() {
        ArrayList<Cami> camins = controladorCami.get_camins();
        System.out.println("Camins disponibles: " + camins.size());
        for (int i = 0; i < camins.size(); ++i) {
            System.out.println(i + 1 + " - Descripcio: " + camins.get(i).getDescripcio() + " Cami: " + camins.get(i).getCami());
        }
        return camins.size();
    }

    public static void eliminar_cami() {
        ArrayList<Cami> camins = controladorCami.get_camins();
        if (camins.size() > 0 ) {
            obtenir_llista_camins();
            System.out.println("=============================");
            System.out.println("Introdueix l'identificador del cami que vols eliminar: ");
            int id = llegir_enter(1, camins.size());
            --id;
            if (controladorCami.eliminar_cami(id) == 0) {
                System.out.println("S'ha elimiant el cami correctament");
            }
            else System.out.println("No hi ha cap cami en aquesta posicio");
        }
        else {
            System.out.println("No hi ha camins.");
        }
    }

    public static void afegir_node() {
        scan.nextLine();
        System.out.println("Introdueix el tipus de node <Autor,Conferencia,Terme,Paper>: ");
        String tipusNode = scan.nextLine();
        Node.TipusNode tipus = string_to_tipus_node(tipusNode);
        if (tipus != null) {
            System.out.println("Introdueix el nom del node de tipus " + tipusNode + ": ");
            String nomNode = scan.nextLine();
            int error = graf.afegir_node(tipus, nomNode);
            if (error != 0) {
                switch (error) {
                    case -1:
                        System.out.println("L'argument es nul");
                        break;
                    case -3:
                        System.out.println("El tipus de node introduit no existex");
                        break;
                }
            }
            else System.out.println("S'ha afegit un nou node al graf");
        }
        else System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
    }

    public static void eliminar_node() {
        scan.nextLine();
        System.out.println("Escriu el tipus de node <Autor,Conferencia,Terme,Paper>: ");
        String tipusNode = scan.nextLine();
        Node.TipusNode tipus = string_to_tipus_node(tipusNode);
        if (tipus != null) {
            int tamany = llistar_nodes(tipus);
            System.out.println("Escriu l'identificador del node que vols eliminar: ");
            int id = llegir_enter(1, tamany);
            --id;
            Node n = graf.get_node(id, tipus);
            int error = graf.eliminar_node(n);
            if (error != 0) {
                switch (error) {
                    case -1:
                        System.out.println("Error, el argument es nul");
                        break;
                    case -2:
                        System.out.println("No hi ha cap node amb aquest id ni tipus");
                        break;
                    case -3:
                        System.out.println("Tipus de node incorrecte");
                        break;
                    case -4:
                        System.out.println("No es pot eliminar un node si la matriu es com a minim 1 en alguna de los parts.");
                        break;
                }
            }
            else System.out.println("S'ha eliminat el node correctament");
        }
        else System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
    }

    public static void afegir_aresta() {
        scan.nextLine();
        System.out.println("Escriu el tipus de node <Autor,Conferencia,Terme,Paper>: ");
        String tipusNode = scan.nextLine();
        Node.TipusNode tipus = string_to_tipus_node(tipusNode);
        if (tipus != null) {
            int tamany_paper = llistar_nodes(Node.TipusNode.PAPER);
            System.out.println("Seleciona un node paper com a origen: ");
            int idNodeOrigen = llegir_enter(1, tamany_paper);
            int tamany_node = llistar_nodes(tipus);
            System.out.println("Seleciona un node " + tipus + " com a desti: ");
            int idNodeDesti = llegir_enter(1, tamany_node);

            Node origen = graf.get_node(idNodeOrigen, Node.TipusNode.PAPER);
            Node desti = graf.get_node(idNodeDesti, tipus);

            int error = graf.afegir_aresta(origen, desti);
            if (error != 0) {
                System.out.println("ERROR!");
            }
            else System.out.println("S'ha afegit una nova aresta entre els nodes amb id " + idNodeOrigen + " a " + idNodeDesti);
        }
        else System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
    }

    public static void eliminar_aresta() {
        scan.nextLine();
        System.out.println("Escriu el tipus de node <Autor,Conferencia,Terme,Paper>: ");
        String tipusNode = scan.nextLine();
        Node.TipusNode tipus = string_to_tipus_node(tipusNode);
        if (tipus != null) {
            int tamany_paper = llistar_nodes(Node.TipusNode.PAPER);
            System.out.println("Seleciona un node paper com a origen: ");
            int idNodeOrigen = llegir_enter(1, tamany_paper);
            int tamany_node = llistar_nodes(tipus);
            System.out.println("Seleciona un node " + tipus + " com a desti: ");
            int idNodeDesti = llegir_enter(1, tamany_node);

            Node origen = graf.get_node(idNodeOrigen, Node.TipusNode.PAPER);
            Node desti = graf.get_node(idNodeDesti, tipus);

            int error = graf.eliminar_aresta(origen, desti);
            if (error != 0) {
                System.out.println("Error: no s'ha pogut eliminar l'aresta");
            }
            else System.out.println("S'ha eliminat l'aresta entre els nodes amb id " + idNodeOrigen + " a " + idNodeDesti);
        }
        else System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
    }

    public static void obtenir_llista_arestes() {
        System.out.println("Matriu de adjcacencia Paper x Terme: ");
        print_matriu(graf.get_paper_terme());
        System.out.println("Matriu de adjcacencia Paper x Autor: ");
        print_matriu(graf.get_paper_autor());
        System.out.println("Matriu de adjcacencia Paper x Conferencia: ");
        print_matriu(graf.get_paper_conferencia());
    }

    public static void consulta_node_cami(){
        scan.nextLine();
        System.out.println("Introdueix el tipus de node <Autor,Conferencia,Terme,Paper>: ");
        String tipusNode = scan.nextLine();
        Node.TipusNode tipus = string_to_tipus_node(tipusNode);
        if (tipus != null) {
            int tamany = llistar_nodes(tipus);
            System.out.println("Introdueix l'identificador del node que vols utilitzar [1," + tamany + "]");
            int idNode = llegir_enter(1, tamany);
            --idNode;

            Node node = graf.get_node(idNode, tipus);

            tamany = obtenir_llista_camins();
            System.out.println("Introdueix l'identificador del cami que vols utilitzar [1," +  tamany + "]");
            int idCami = llegir_enter(1, tamany);
            --idCami;

            Cami cami = controladorCami.get_cami(idCami);
            if (validar_node_cami(tipus, cami.getCami().charAt(0))) {
                Consulta consulta = controladorConsulta.consulta_node_cami("Consulta1", node, cami, graf);
                Resultat resultat = consulta.getResultat();
                for (int i = 0; i < resultat.mida(); ++i) {
                    Resultat.Tuple t = resultat.get_tuple(i);
                    System.out.println("Nom node: " + t.get_node().get_nom() + " Grau rellevancia: " + t.get_grau_rellevancia());
                }
            } else System.out.println("El node te que pertanyer al conjunt de nodes de l'inici del cami");
        }
        else System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
    }

    public static void consulta_node_cami_grau() {
        scan.nextLine();
        System.out.println("Introdueix el tipus de node <Autor,Conferencia,Terme,Paper>: ");
        String tipusNode = scan.nextLine();
        Node.TipusNode tipus = string_to_tipus_node(tipusNode);
        if (tipus != null) {
            int tamany = llistar_nodes(tipus);
            System.out.println("Introdueix l'identificador del node que vols utilitzar [1," + tamany + "]");
            int idNode = llegir_enter(1, tamany);
            --idNode;

            Node node = graf.get_node(idNode, tipus);

            tamany = obtenir_llista_camins();
            System.out.println("Introdueix l'identificador del cami que vols utilitzar [1," +  tamany + "]");
            int idCami = llegir_enter(1, tamany);
            --idCami;
            Cami cami = controladorCami.get_cami(idCami);

            System.out.println("Escriu el grau de rellevancia amb el que vols filtrar [NOTA: Els elements que estan" +
                    " fora del interval de grau de rellevancia amb un cert error seran eliminats]: ");
            double grauRellevancia = scan.nextDouble();
            System.out.println("Escriu l'error d'aproximacio sobre el grau de rellevancia: ");
            double error = scan.nextDouble();

            if (grauRellevancia >= 0.0 && grauRellevancia <= 1.0) {
                if (error >= 0.0 && error <= 1.0) {
                    if (validar_node_cami(tipus, cami.getCami().charAt(0))) {
                        Consulta consulta = controladorConsulta.consulta_node_cami_grau_rellevancia("Consulta1", node, cami, grauRellevancia, error, graf);
                        Resultat resultat = consulta.getResultat();
                        resultat.filtrar_resultat(grauRellevancia, error);
                        for (int i = 0; i < resultat.mida(); ++i) {
                            Resultat.Tuple t = resultat.get_tuple(i);
                            System.out.println("Nom node: " + t.get_node().get_nom() + " Grau rellevancia: " + t.get_grau_rellevancia());
                        }
                    } else System.out.println("El node te que pertanyer al conjunt de nodes de l'inici del cami");
                } else System.out.println("Error, el error te que estar entre 0 y 1");
            }
            else System.out.println("Error, el grau de rellevancia te que estar entre 0 y 1");
        }
        else System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
    }

    public static void consulta_node_cami_node() {
        scan.nextLine();
        System.out.println("Introdueix el tipus de node per el node Origen <Autor,Conferencia,Terme,Paper>: ");
        String tipusNode = scan.nextLine();
        Node.TipusNode tipusOrigen = string_to_tipus_node(tipusNode);
        System.out.println("Introdueix el tipus de node per el node Desti <Autor,Conferencia,Terme,Paper>: ");
        tipusNode = scan.nextLine();
        Node.TipusNode tipusDesti = string_to_tipus_node(tipusNode);
        if (tipusOrigen != null && tipusDesti != null) {
            int tamany = llistar_nodes(tipusOrigen);
            System.out.println("Introdueix l'identificador del node origen que vols utilitzar [1," + tamany + "]");
            int idNodeOrigen = llegir_enter(1, tamany);
            --idNodeOrigen;

            tamany = llistar_nodes(tipusDesti);
            System.out.println("Introdueix l'identificador del node desti que vols utilitzar [1," + tamany + "]");
            int idNodeDesti = llegir_enter(1, tamany);
            --idNodeDesti;

            Node nodeOrigen = graf.get_node(idNodeOrigen, tipusOrigen);
            Node nodeDesti = graf.get_node(idNodeDesti, tipusDesti);

            tamany = obtenir_llista_camins();
            System.out.println("Introdueix l'identificador del cami que vols utilitzar [1," +  tamany + "]");
            int idCami = llegir_enter(1, tamany);
            --idCami;
            Cami cami = controladorCami.get_cami(idCami);

            double grauRellevancia = controladorConsulta.consulta_nodo_cami_nodo(nodeOrigen, cami, nodeDesti,graf);

            System.out.println("Escriu l'error d'aproximacio sobre el grau de rellevancia entre aquests dos nodes: ");
            double error = scan.nextDouble();

            if (error >= 0.0 && error <= 1.0) {
                if (validar_node_cami(tipusOrigen, cami.getCami().charAt(0)) && validar_node_cami(tipusDesti, cami.getCami().charAt(cami.getCami().length()-1))) {
                    scan.nextLine();
                    System.out.println("A partir del grau de rellevancia dels dos nodes anteriors per aquell cami.");
                    System.out.println("Introdueix de nou un " + tipusOrigen + " per el cami " + cami.getCami());
                    tamany = llistar_nodes(tipusOrigen);
                    System.out.println("Introdueix l'identificador del node que vols utilitzar [1," + tamany + "]");
                    int idNode = llegir_enter(1, tamany);
                    --idNode;

                    Node node = graf.get_node(idNode, tipusOrigen);

                    if (validar_node_cami(tipusOrigen, cami.getCami().charAt(0))) {
                        Consulta consulta = controladorConsulta.consulta_node_cami_grau_rellevancia("Consulta1", node, cami, grauRellevancia, error, graf);
                        Resultat resultat = consulta.getResultat();
                        resultat.filtrar_resultat(grauRellevancia, error);
                        for (int i = 0; i < resultat.mida(); ++i) {
                            Resultat.Tuple t = resultat.get_tuple(i);
                            System.out.println("Nom node: " + t.get_node().get_nom() + " Grau rellevancia: " + t.get_grau_rellevancia());
                        }
                    } else System.out.println("El node te que pertanyer al conjunt de nodes de l'inici del cami");
                } else System.out.println("El node te que pertanyer al conjunt de nodes de l'inici del cami o el node final té que pertanyer al conjunt de nodes del final");
            } else System.out.println("Error, el error te que estar entre 0 y 1");
        } else System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
    }

    public static void main(String[] argv) {
        System.out.println("Inicialitzem el graf amb unes dades d'exemple, pero es pot ampliar o reduir a traves del menu");
        controladorConsulta = new ControladorConsulta();
        controladorCami = new ControladorCami();

        ArrayList<Node> autors= new ArrayList<Node>();
        ArrayList<Node> papers= new ArrayList<Node>();
        ArrayList<Node> terms= new ArrayList<Node>();
        ArrayList<Node> conferencies= new ArrayList<Node>();
        for(int i = 0; i < 4; ++i){
            Node.TipusNode a = Node.TipusNode.AUTOR;
            Node n = new Node(i, ("TestAutor" + (i+1)),a);
            autors.add(n);
        }
        for(int i = 0; i < 3; ++i){
            Node.TipusNode a = Node.TipusNode.PAPER;
            Node n = new Node(i, ("TestPaper" + (i+1)),a);
            papers.add(n);
        }
        for(int i = 0; i < 4; ++i){
            Node.TipusNode a = Node.TipusNode.TERME;
            Node n = new Node(i, ("TestTerme"  + (i+1)),a);
            terms.add(n);
        }
        for(int i = 0; i < 4; ++i){
            Node.TipusNode a = Node.TipusNode.CONFERENCIA;
            Node n = new Node(i, ("TestConferencia" + (i+1)),a);
            conferencies.add(n);
        }

        Matriu PA = new Matriu(3,4);
        PA.set_valor(0,0,1);
        PA.set_valor(0,1,1);
        PA.set_valor(1,1,1);
        PA.set_valor(1,2,1);
        PA.set_valor(1,3,1);
        PA.set_valor(2,3,1);
        Matriu PT = new Matriu(3,4);
        Matriu PC = new Matriu(3,4);
        graf = new Graf(PA,PC,PT,papers,terms, autors, conferencies);

        controladorCami.afegir_cami(new Cami("PA","Cami de proba"));

        int opcio = 1;
        while (opcio != 0) {
            menu();
            opcio = llegir_enter(0,11);
            switch (opcio) {
                case 1:
                    consulta_node_cami();
                    break;
                case 2:
                    consulta_node_cami_grau();
                    break;
                case 3:
                    consulta_node_cami_node();
                    break;
                case 4:
                    afegir_cami();
                    break;
                case 5:
                    eliminar_cami();
                    break;
                case 6:
                    obtenir_llista_camins();
                    break;
                case 7:
                    afegir_node();
                    break;
                case 8:
                    eliminar_node();
                    break;
                case 9:
                    afegir_aresta();
                    break;
                case 10:
                    eliminar_aresta();
                    break;
                case 11:
                    obtenir_llista_arestes();
                    break;
            }
        }

    }*/
}
