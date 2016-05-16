package expert.finder.graf;

import expert.finder.cami.ControladorCami;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ruben Bagan Benavides on 16/05/2016.
 */
public class ControladorGrafDriver {
    public static Scanner lector = new Scanner(System.in);
    public static ControladorGraf controladorGraf;

    public static int llegir_enter(int minim, int maxim) {
        int n = lector.nextInt();
        while (n < minim || n > maxim) {
            System.out.println("Error: tens que escriure un nombre entre " + minim + " i " + maxim);
            n = lector.nextInt();
        }
        return n;
    }

    public static void menu() {
        System.out.println("1\tAfegir Node");
        System.out.println("2\tEliminar Node");
        System.out.println("3\tModificar nom node");
        System.out.println("4\tConsultar node");
        System.out.println("5\tGet Nodes");
        System.out.println("0\tSortir");
    }

    public static void llistar_nodes(ArrayList<String> nodes) {
        for (int i = 0; i < nodes.size(); ++i) {
            String node = nodes.get(i);
            String id = node.substring(0, node.indexOf('|'));
            String nom = node.substring(node.indexOf('|')+1, node.indexOf('|',node.indexOf('|')+1));
            String tipus = node.substring(node.indexOf('|',node.indexOf('|')+1)+1, node.length());
            System.out.println(Integer.valueOf(id)+1 + "\t\t" + nom + "\t\t\t\t\t" + tipus);
        }
    }

    public static void afegir_node() {
        lector.nextLine();
        System.out.println("Escriu el nom del node que vols afegir: ");
        String nom = lector.nextLine();
        System.out.println("Escriu el tipus del node: ");
        String tipusNode = lector.nextLine();
        try {
            controladorGraf.afegir_node(nom, tipusNode);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void eliminar_node() {
        lector.nextLine();
        System.out.println("Escriu el tipus del node: ");
        String tipusNode = lector.nextLine();
        try {
            ArrayList<String> nodes = controladorGraf.get_nodes(tipusNode);
            llistar_nodes(nodes);
            System.out.println("Escriu l'identificador del node a eliminar: ");
            int id = lector.nextInt();
            lector.nextLine();
            controladorGraf.eliminar_node(id-1, tipusNode);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void modificar_nom() {
        lector.nextLine();
        System.out.println("Escriu el tipus del node: ");
        String tipusNode = lector.nextLine();
        try {
            ArrayList<String> nodes = controladorGraf.get_nodes(tipusNode);
            llistar_nodes(nodes);
            System.out.println("Escriu l'identificador del node a consultar: ");
            int id = lector.nextInt();
            lector.nextLine();
            System.out.println("Escriu el nou nom per el node: ");
            String nouNom = lector.nextLine();
            controladorGraf.modificar_nom_node(id-1, nouNom, tipusNode);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void consultar_node() {
        lector.nextLine();
        System.out.println("Escriu el tipus del node: ");
        String tipusNode = lector.nextLine();
        try {
            ArrayList<String> nodes = controladorGraf.get_nodes(tipusNode);
            llistar_nodes(nodes);
            System.out.println("Escriu l'identificador del node a consultar: ");
            int id = lector.nextInt();
            String node = controladorGraf.consultar_node(id-1, tipusNode);
            System.out.println(node);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void get_nodes() {lector.nextLine();
        System.out.println("Escriu el tipus del node: ");
        String tipusNode = lector.nextLine();
        try {
            ArrayList<String> nodes = controladorGraf.get_nodes(tipusNode);
            llistar_nodes(nodes);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] argv) {
        try {
            controladorGraf = new ControladorGraf(true, "C:\\Users\\Phenom\\Downloads\\DBLP_four_area\\");
            int opcio = 1;
            while (opcio != 0) {
                menu();
                opcio = llegir_enter(0,5);
                switch (opcio) {
                    case 1:
                        afegir_node();
                        break;
                    case 2:
                        eliminar_node();
                        break;
                    case 3:
                        modificar_nom();
                        break;
                    case 4:
                        consultar_node();
                        break;
                    case 5:
                        get_nodes();
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
