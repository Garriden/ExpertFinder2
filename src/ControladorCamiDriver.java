import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ruben Bagan Benavides on 24/04/2016.
 */
public class ControladorCamiDriver {
    public static Scanner scan = new Scanner(System.in);
    public static ControladorCami controladorCami = new ControladorCami();

    public static int llegir_enter(int minim, int maxim) {
        int n = scan.nextInt();
        while (n < minim || n > maxim) {
            System.out.println("Error: tens que escriure un nombre entre " + minim + " i " + maxim);
            n = scan.nextInt();
        }
        return n;
    }

    public static void menu() {
        System.out.println("1\t Afegir cami");
        System.out.println("2\t Eliminar cami");
        System.out.println("3\t Obtenir cami per descripcio");
        System.out.println("4\t Obtenir cami per identificador");
        System.out.println("5\t Obtenir llista de camins");
        System.out.println("6\t Modificar descripcio");
        System.out.println("7\t Modificar cami");
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

    public static void eliminar_cami() {
        ArrayList<Cami> camins = controladorCami.get_camins();
        if (camins.size() > 0 ) {
            obtenir_llista();
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

    public static void modificar_descripcio_cami() {
        ArrayList<Cami> camins = controladorCami.get_camins();
        if (camins.size() > 0 ) {
            obtenir_llista();
            System.out.println("=============================");
            System.out.println("Introdueix l'identificador del cami que vols modiciar la descripcio: ");
            int id = llegir_enter(1, camins.size());
            --id;
            scan.nextLine();
            System.out.println("Escriu la nova descripcio: ");
            String novaDescripcio = scan.nextLine();
            if (controladorCami.modificar_descripcio(id, novaDescripcio) == 0) {
                System.out.println("S'ha modificat el cami correctament");
            }
            else System.out.println("No hi ha cap cami en aquesta posicio");
        }
        else {
            System.out.println("No hi ha camins.");
        }
    }


    public static void modificar_cami() {
        ArrayList<Cami> camins = controladorCami.get_camins();
        if (camins.size() > 0 ) {
            obtenir_llista();
            System.out.println("=============================");
            System.out.println("Introdueix l'identificador del cami que vols modiciar el seu cami: ");
            int id = llegir_enter(1, camins.size());
            --id;
            scan.nextLine();
            System.out.println("Escriu el nou cami: ");
            String cami = crear_cami();
            if (!cami.equals("")) {
                Cami c = new Cami();
                if (c.camiValid(cami)) {
                    if (controladorCami.modificar_cami(id, cami) == 0) {
                        System.out.println("S'ha modificat el cami d'un \"cami\" correctament.");
                    }
                    else System.out.println("No hi ha cap cami en aquesta posicio");
                } else System.out.println("El cami introduit no es valid");
            }
        }
        else {
            System.out.println("No hi ha camins.");
        }
    }

    public static void obtenir_llista() {
        ArrayList<Cami> camins = controladorCami.get_camins();
        System.out.println("Camins disponibles: " + camins.size());
        for (int i = 0; i < camins.size(); ++i) {
            System.out.println(i + 1 + " - Descripcio: " + camins.get(i).getDescripcio() + " Cami: " + camins.get(i).getCami());
        }
    }

    public static void obtenir_cami_per_descripcio() {
        scan.nextLine();
        ArrayList<Cami> camins = controladorCami.get_camins();
        if (camins.size() > 0 ) {
            obtenir_llista();
            System.out.println("=============================");
            System.out.println("Introdueix la descripcio del cami que vols obtenir: ");
            String descripcio = scan.nextLine();
            Cami c = controladorCami.get_cami(descripcio);
            if (c != null) {
                System.out.println("Descripcio: " + c.getDescripcio() + " Cami: " + c.getCami());
            }
            else System.out.println("No hi ha cap cami amb aquesta descripcio");
        }
        else {
            System.out.println("No hi ha camins.");
        }
    }

    public static void obtenir_cami_per_identificador() {
        scan.nextLine();
        ArrayList<Cami> camins = controladorCami.get_camins();
        if (camins.size() > 0 ) {
            obtenir_llista();
            System.out.println("=============================");
            System.out.println("Introdueix l'identificador del cami que vols obtenir: ");
            int id = llegir_enter(1, camins.size());
            --id;
            Cami c = controladorCami.get_cami(id);
            if (c != null) {
                System.out.println("Descripcio: " + c.getDescripcio() + " Cami: " + c.getCami());
            }
            else System.out.println("No hi ha cap cami amb aquesta descripcio");
        }
        else {
            System.out.println("No hi ha camins.");
        }
    }

    public static void main(String[] argv) {
        int opcio = 1;
        while (opcio != 0) {
            menu();
            opcio = llegir_enter(0, 7);
            switch (opcio) {
                case 1:
                    afegir_cami();
                    break;
                case 2:
                    eliminar_cami();
                    break;
                case 3:
                    obtenir_cami_per_descripcio();
                    break;
                case 4:
                    obtenir_cami_per_identificador();
                    break;
                case 5:
                    obtenir_llista();
                    break;
                case 6:
                    modificar_descripcio_cami();
                    break;
                case 7:
                    modificar_cami();
                    break;
            }
        }

    }
}
