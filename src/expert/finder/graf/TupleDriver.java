package expert.finder.utils;

import expert.finder.node.Node;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ruben Bagan Benavides on 08/05/2016.
 */
public class TupleDriver {
    public static Scanner scan = new Scanner(System.in);
    public static int idNode = 0;
    public static ArrayList<Tuple> tuples;

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

    public static void menu() {
        System.out.println("1\t Afegir un tuple");
        System.out.println("2\t Obtenir grau rellevancia tuple iessima");
        System.out.println("3\t Obtenir node tuple iessima");
        System.out.println("4\t Obtenir tuple iessima");
        System.out.println("5\t Eliminar tuple");
        System.out.println("6\t Obtenir llista tuples");
        System.out.println("0\t Sortir");
    }


    public static void afegir_tuple() {
        scan.nextLine();
        System.out.println("Introdueix el tipus de node <Autor,Conferencia,Terme,Paper>: ");
        String tipusNode = scan.nextLine();
        Node.TipusNode tipus = string_to_tipus_node(tipusNode);
        if (tipus != null) {
            System.out.println("Introdueix el nom del node de tipus " + tipusNode + ": ");
            String nomNode = scan.nextLine();
            Node node = new Node(idNode, nomNode, tipus);
            ++idNode;
            tuples.add(new Tuple(node, ThreadLocalRandom.current().nextDouble(0.0, 1.0)));
            System.out.println("S'ha creat una nova tuple");
        }
        else System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
    }

    public static void consultar_mida() {
        System.out.println("Nombre de tuples del resultat: " + tuples.size());
    }

    public static void obtenir_grau_rellevancia_tuple_iessima() {
        if (tuples.size() > 0) {
            System.out.println("Escriu l'identificador de la tuple que vulguis veure [1," + tuples.size() + "]");
            int id = llegir_enter(1, tuples.size());
            Tuple t = tuples.get(id-1);
            System.out.println("El grau de rellevancia de la tuple amb indentificador " + id + " es " + t.get_grau_rellevancia());
        }
        else System.out.println("No hi ha tuples en el resultat");
    }

    public static void obtenir_node_tuple_iessima() {
        if (tuples.size() > 0) {
            System.out.println("Escriu l'identificador de la tuple que vulguis veure [1," + tuples.size() + "]");
            int id = llegir_enter(1, tuples.size());
            Tuple t = tuples.get(id-1);
            System.out.println("Nom node: " + t.get_node().get_nom() + " Tipus node: " + t.get_node().get_tipus_node());
        }
        else System.out.println("No hi ha tuples en el resultat");
    }

    public static void obtenir_tuple_iessima() {
        if (tuples.size() > 0) {
            System.out.println("Escriu l'identificador de la tuple que vulguis veure [1," + tuples.size() + "]");
            int id = llegir_enter(1, tuples.size());
            Tuple t = tuples.get(id-1);
            System.out.println("Nom node: " + t.get_node().get_nom() + " Tipus node: " + t.get_node().get_tipus_node() +
                    " Grau de Rellevancia: " + t.get_grau_rellevancia());
        }
        else System.out.println("No hi ha tuples en el resultat");
    }

    public static void eliminar_tuple() {
        if (tuples.size() > 0) {
            System.out.println("Escriu l'identificador de la tuple que vulguis eliminar [1," + tuples.size() + "]");
            int id = llegir_enter(1, tuples.size());
            tuples.remove(id-1);
            --idNode;
            System.out.println("S'ha eliminat la tuple i-èssima");
        }
        else System.out.println("No hi ha tuples en el resultat");
    }

    public static void obtenir_llista_tuples() {
        System.out.println("Nombre de tuples total: " + tuples.size());
        for (int i = 0; i < tuples.size(); ++i) {
            System.out.println((i+1) + "- Nom Node: " + tuples.get(i).get_node().get_nom() + " Tipus Node: " +
                    tuples.get(i).get_node().get_tipus_node() + " Grau rellevancia: " + tuples.get(i).get_grau_rellevancia());
        }
    }



    public static void main(String[] argv) {
        tuples = new ArrayList<>();
        int opcio = 1;
        while (opcio != 0) {
            menu();
            opcio = llegir_enter(0, 8);
            switch (opcio) {
                case 1:
                    afegir_tuple();
                    break;
                case 2:
                    obtenir_grau_rellevancia_tuple_iessima();
                    break;
                case 3:
                    obtenir_node_tuple_iessima();
                    break;
                case 4:
                    obtenir_tuple_iessima();
                    break;
                case 5:
                    eliminar_tuple();
                    break;
                case 6:
                    obtenir_llista_tuples();
                    break;
            }
        }
    }
}
