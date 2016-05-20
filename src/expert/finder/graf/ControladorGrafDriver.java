package expert.finder.graf;

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
        System.out.println("6\tAfegir Relacio");
        System.out.println("7\tEliminar Relacio");
        System.out.println("8\tConsultar Relacio");
        System.out.println("0\tSortir");
    }

    public static void llistar_nodes(ArrayList<String> nodes) {
        for (int i = 0; i < nodes.size(); ++i) {
            String node = nodes.get(i);
            String id = node.substring(0, node.indexOf('|'));
            String nom = node.substring(node.indexOf('|')+1, node.indexOf('|',node.indexOf('|')+1));
            String tipus = node.substring(node.indexOf('|',node.indexOf('|')+1)+1, node.length());
            System.out.printf("%s\t%-100s%-50s", Integer.valueOf(id)+1, nom, tipus);
            System.out.println();
        }
        System.out.println();
    }

    public static void llistar_relacions(ArrayList<String> relacions) {
        for (int i = 0; i < relacions.size(); ++i) {
            String node = relacions.get(i);

            int posicioBarra1 = node.indexOf('|');
            int posicioBarra2 = node.indexOf('|',posicioBarra1+1);
            int posicioBarra3 = node.indexOf('|',posicioBarra2+1);

            String id = node.substring(0, posicioBarra1);
            String nom = node.substring(posicioBarra1+1, posicioBarra2);
            String tipus = node.substring(posicioBarra2+1, posicioBarra3);
            String relacionat = node.substring(posicioBarra3+1, node.length());

            System.out.printf("%s\t%-40s%-40s", Integer.valueOf(id)+1,nom,tipus);
            if (relacionat.equalsIgnoreCase("RELACIONAT")) System.out.print("Relacionat: Si");
            else System.out.print("Relacionat: No");
            System.out.println();
        }
        System.out.println();
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

    public static void afegir_relacio() {
        lector.nextLine();
        try {
            llistar_nodes(controladorGraf.get_nodes("PAPER"));
            System.out.println("Escriu l'identificador del paper per consultar els nodes amb que esta relacionat: ");
            int idOrigen = lector.nextInt();
            System.out.println("Escriu el tipus de node desti: ");
            lector.nextLine();
            String tipusNode = lector.nextLine();
            ArrayList<String> relacions = controladorGraf.consultar_relacio(idOrigen-1, tipusNode);
            llistar_relacions(relacions);
            System.out.println("Escriu l'identificador del node desti per eliminar la relacio: ");
            int idDesti = lector.nextInt();
            controladorGraf.afegir_relacio(idOrigen-1, idDesti-1, tipusNode);
            System.out.println("S'ha afegit correctament");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void eliminar_relacio() {
        lector.nextLine();
        try {
            llistar_nodes(controladorGraf.get_nodes("PAPER"));
            System.out.println("Escriu l'identificador del paper per consultar els nodes amb que esta relacionat: ");
            int idOrigen = lector.nextInt();
            System.out.println("Escriu el tipus de node desti: ");
            lector.nextLine();
            String tipusNode = lector.nextLine();
            ArrayList<String> relacions = controladorGraf.consultar_relacio(idOrigen-1, tipusNode);
            llistar_relacions(relacions);
            System.out.println("Escriu l'identificador del node desti per eliminar la relacio: ");
            int idDesti = lector.nextInt();
            controladorGraf.eliminar_relacio(idOrigen-1, idDesti-1, tipusNode);
            System.out.println("S'ha eliminat correctament");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void consultar_relacio() {
        lector.nextLine();
        try {
            llistar_nodes(controladorGraf.get_nodes("PAPER"));
            System.out.println("Escriu l'identificador del paper per consultar els nodes amb que esta relacionat: ");
            int id = lector.nextInt();
            lector.nextLine();
            System.out.println("Escriu el tipus de node desti: ");
            String tipusNode = lector.nextLine();
            ArrayList<String> relacions = controladorGraf.consultar_relacio(id-1, tipusNode);
            llistar_relacions(relacions);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] argv) {
            int opcio = 1;
            while (opcio != 0) {
                menu();
                opcio = llegir_enter(0,8);
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
                    case 6:
                        afegir_relacio();
                        break;
                    case 7:
                        eliminar_relacio();
                        break;
                    case 8:
                        consultar_relacio();
                        break;
                }
            }
    }
}
