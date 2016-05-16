/**
 * Created by Ruben Bagan Benavides on 24/04/2016.
 */

import expert.finder.node.Node;
import expert.finder.utils.Tuple;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ResultatDriver {
    public static Scanner scan = new Scanner(System.in);
    public static Resultat resultat = new Resultat();
    public static int idNode = 0;

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
        System.out.println("2\t Consultar mida de la consulta");
        System.out.println("3\t Obtenir grau rellevancia tuple iessima");
        System.out.println("4\t Obtenir node tuple iessima");
        System.out.println("5\t Obtenir tuple iessima");
        System.out.println("6\t Eliminar tuple");
        System.out.println("7\t Filtrar resultar");
        System.out.println("8\t Obtenir llista tuples");
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
            resultat.afegir_tuple(node, ThreadLocalRandom.current().nextDouble(0.0, 1.0));
            System.out.println("S'ha creat una nova tuple");
        }
        else System.out.println("El node te que ser un de la llista:  <Autor,Paper,Terme,Conferencia>");
    }

    public static void consultar_mida() {
        System.out.println("Nombre de tuples del resultat: " + resultat.mida());
    }

    public static void obtenir_grau_rellevancia_tuple_iessima() {
        if (resultat.mida() > 0) {
            System.out.println("Escriu l'identificador de la tuple que vulguis veure [1," + resultat.mida() + "]");
            int id = llegir_enter(1, resultat.mida());
            Tuple t = resultat.get_tuple(id-1);
            System.out.println("El grau de rellevancia de la tuple amb indentificador " + id + " es " + t.get_grau_rellevancia());
        }
        else System.out.println("No hi ha tuples en el resultat");
    }

    public static void obtenir_node_tuple_iessima() {
        if (resultat.mida() > 0) {
            System.out.println("Escriu l'identificador de la tuple que vulguis veure [1," + resultat.mida() + "]");
            int id = llegir_enter(1, resultat.mida());
            Tuple t = resultat.get_tuple(id-1);
            System.out.println("Nom node: " + t.get_node().get_nom() + " Tipus node: " + t.get_node().get_tipus_node());
        }
        else System.out.println("No hi ha tuples en el resultat");
    }

    public static void obtenir_tuple_iessima() {
        if (resultat.mida() > 0) {
            System.out.println("Escriu l'identificador de la tuple que vulguis veure [1," + resultat.mida() + "]");
            int id = llegir_enter(1, resultat.mida());
            Tuple t = resultat.get_tuple(id-1);
            System.out.println("Nom node: " + t.get_node().get_nom() + " Tipus node: " + t.get_node().get_tipus_node() +
            " Grau de Rellevancia: " + t.get_grau_rellevancia());
        }
        else System.out.println("No hi ha tuples en el resultat");
    }

    public static void eliminar_tuple() {
        if (resultat.mida() > 0) {
            System.out.println("Escriu l'identificador de la tuple que vulguis eliminar [1," + resultat.mida() + "]");
            int id = llegir_enter(1, resultat.mida());
            resultat.eliminar_tuple(id-1);
            --idNode;
            System.out.println("S'ha eliminat la tuple i-èssima");
        }
        else System.out.println("No hi ha tuples en el resultat");
    }

    public static void obtenir_llista_tuples() {
        ArrayList<Tuple> tuples = resultat.get_tuples();
        System.out.println("Nombre de tuples total: " + resultat.mida());
        for (int i = 0; i < resultat.mida(); ++i) {
            System.out.println((i+1) + "- Nom Node: " + tuples.get(i).get_node().get_nom() + " Tipus Node: " +
                    tuples.get(i).get_node().get_tipus_node() + " Grau rellevancia: " + tuples.get(i).get_grau_rellevancia());
        }
    }

    public static void filtrar_resultat() {
        if (resultat.mida() > 0) {
            System.out.println("Escriu el grau de rellevancia amb el que vols filtrar [NOTA: Els elements que estan" +
                    " fora del interval de grau de rellevancia amb un cert error seran eliminats]: ");
            double grauRellevancia = scan.nextDouble();
            System.out.println("Escriu l'error d'aproximacio sobre el grau de rellevancia: ");
            double error = scan.nextDouble();
            resultat.filtrar_resultat(grauRellevancia, error);
            System.out.println("S'ha aplicat el filtre");
        }
        else System.out.println("No hi ha tuples en el resultat");
    }

    public static void main(String[] argv) {
        int opcio = 1;
        while (opcio != 0) {
            menu();
            opcio = llegir_enter(0, 8);
            switch (opcio) {
                case 1:
                    afegir_tuple();
                    break;
                case 2:
                    consultar_mida();
                    break;
                case 3:
                    obtenir_grau_rellevancia_tuple_iessima();
                    break;
                case 4:
                    obtenir_node_tuple_iessima();
                    break;
                case 5:
                    obtenir_tuple_iessima();
                    break;
                case 6:
                    eliminar_tuple();
                    break;
                case 7:
                    filtrar_resultat();
                    break;
                case 8:
                    obtenir_llista_tuples();
                    break;
            }
        }

    }
}
