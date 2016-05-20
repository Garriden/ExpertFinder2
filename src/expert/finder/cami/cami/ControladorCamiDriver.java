package expert.finder.cami.cami;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ruben Bagan Benavides on 24/04/2016.
 */
public class ControladorCamiDriver {
    public static Scanner lector = new Scanner(System.in);
    public static ControladorCami controladorCami = new ControladorCami();

    public static int llegir_enter(int minim, int maxim) {
        int n = lector.nextInt();
        while (n < minim || n > maxim) {
            System.out.println("Error: tens que escriure un nombre entre " + minim + " i " + maxim);
            n = lector.nextInt();
        }
        return n;
    }

    public static void menu() {
        System.out.println("1\t Afegir cami");
        System.out.println("2\t Eliminar cami");
        System.out.println("3\t Modificar descripcio d'un cami i-èssim");
        System.out.println("4\t Modificar \"cami\" d'un cami i-èssim");
        System.out.println("5\t Obtenir cami i-èssim");
        System.out.println("6\t Llistar tots els camins");
        System.out.println("7\t Comprovar si un cami es concatenable");
        System.out.println("8\t Concatenar cami");
        System.out.println("9\t Invertir cami");
        System.out.println("10\t Validar cami");
        System.out.println("11\t Importar camins");
        System.out.println("12\t Exportar camins");
        System.out.println("0\t Sortir");
    }

    public static String crear_cami() {
        System.out.println("Introdueix els diferents nodes que formaran el nou cami <Autor,Paper,Terme,Conferencia>, " +
                "cada nom de tipus de node seguit de la tecla enter. Escriu FI per finalitzar: ");
        String node = lector.nextLine();
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
            node = lector.nextLine();
        }
        return cami;
    }

    public static void obtenir_llista() {
        ArrayList<String> camins = controladorCami.get_camins();
        System.out.println("Camins disponibles: " + camins.size());
        for (int i = 0; i < camins.size(); ++i) System.out.println(i+1 + " " + camins.get(i));
    }

    public static int demanar_id() {
        obtenir_llista();
        System.out.println("=============================");
        System.out.println("Introdueix l'identificador del cami: ");
        int id = llegir_enter(1, controladorCami.get_nombre_camins());
        return (--id);
    }

    public static void afegir_cami() {
        lector.nextLine();
        String cami = crear_cami();
        if (!cami.equals("")) {
            if (controladorCami.validar_cami(cami)) {
                System.out.println("Introdueix una descripcio per aquest cami: ");
                String descripcio = lector.nextLine();
                try {
                    controladorCami.afegir_cami(cami, descripcio);
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else System.out.println("El cami introduit no es valid");
        }
    }

    public static void eliminar_cami() {
        if (controladorCami.get_nombre_camins() > 0) {
            int id = demanar_id();
            try {
                controladorCami.eliminar_cami(id);
                System.out.println("S'ha elimiant el cami correctament");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        else System.out.println("No hi ha camins.");
    }

    public static void modificar_descripcio_cami() {
        if (controladorCami.get_nombre_camins() > 0) {
            int id = demanar_id();
            lector.nextLine();
            System.out.println("Escriu la nova descripcio: ");
            String novaDescripcio = lector.nextLine();
            try {
                controladorCami.modificar_descripcio(id, novaDescripcio);
                System.out.println("S'ha modificat el cami correctament");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
        else System.out.println("No hi ha camins.");
    }

    public static void modificar_cami() {
        if (controladorCami.get_nombre_camins() > 0) {
            int id = demanar_id();
            lector.nextLine();
            System.out.println("Escriu el nou cami: ");
            String cami = crear_cami();
            if (!cami.equals("")) {
                if (controladorCami.validar_cami(cami)) {
                    try {
                        controladorCami.modificar_cami(id, cami);
                        System.out.println("S'ha modificat el cami d'un \"cami\" correctament.");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println(e.getMessage());
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                } else System.out.println("El cami introduit no es valid");
            }
        }
        else System.out.println("No hi ha camins.");
    }

    public static void obtenir_cami_per_identificador() {
        if (controladorCami.get_nombre_camins() > 0) {
            System.out.println("Escriu un identificador: ");
            int id = lector.nextInt();
            --id;
            try {
                String cami = controladorCami.get_cami(id);
                System.out.println(cami);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        else System.out.println("No hi ha camins.");
    }

    public static void es_concatenable() {
        if (controladorCami.get_nombre_camins() > 0) {
            int idOrigen = demanar_id();
            int idDesti = demanar_id();
            try {
                if (controladorCami.es_concatenable(idOrigen, idDesti)) System.out.println("Si");
                else System.out.println("No");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        else System.out.println("No hi ha camins.");
    }

    public static void concatenar() {
        if (controladorCami.get_nombre_camins() > 0) {
            int idOrigen = demanar_id();
            int idDesti = demanar_id();
            try {
                controladorCami.concatenar_cami(idOrigen, idDesti);
                System.out.println("S'ha afegit un nou cami com a resultat de concatenar aquests dos camins");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        else System.out.println("No hi ha camins.");
    }

    public static void invertir() {
        if (controladorCami.get_nombre_camins() > 0) {
            int id = demanar_id();
            try {
                controladorCami.invertir_cami(id);
                System.out.println("S'ha  afegit un nou cami amb el seu cami invertit");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        else System.out.println("No hi ha camins.");
    }

    public static void validar_cami() {
        if (controladorCami.get_nombre_camins() > 0) {
            String cami = crear_cami();
            if (controladorCami.validar_cami(cami)) System.out.println("El cami es valid");
            else System.out.println("El cami no es valid");
        }
        else System.out.println("No hi ha camins.");
    }

    public static void importar_camins() {
        System.out.println("Escriu la ruta absoluta on esta ubicat el fitxer amb extensio .txt o .sav de la base de dades de camins: ");
        String ruta = lector.nextLine();
        try {
            controladorCami.importar_camins(ruta);
            System.out.println("Importacio completada");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void exportar_camins() {
        System.out.println("Escriu la ruta absoluta on esta ubicat el fitxer amb extensio .txt o .sav de la base de dades de camins: ");
        String ruta = lector.nextLine();
        try {
            controladorCami.exportar_camins(ruta);
            System.out.println("Exportacio completada");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] argv) {
        int opcio = 1;
        while (opcio != 0) {
            menu();
            opcio = llegir_enter(0, 12);
            switch (opcio) {
                case 1:
                    afegir_cami();
                    break;
                case 2:
                    eliminar_cami();
                    break;
                case 3:
                    modificar_descripcio_cami();
                    break;
                case 4:
                    modificar_cami();
                    break;
                case 5:
                    obtenir_cami_per_identificador();
                    break;
                case 6:
                    obtenir_llista();
                    break;
                case 7:
                    es_concatenable();
                    break;
                case 8:
                    concatenar();
                    break;
                case 9:
                    invertir();
                    break;
                case 10:
                    validar_cami();
                    break;
                case 11:
                    importar_camins();
                    break;
                case 12:
                    exportar_camins();
                    break;
            }
        }

    }
}
