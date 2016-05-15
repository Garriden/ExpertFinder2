package Cami;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ruben Bagan Benavides on 13/05/2016.
 */
public class ControladorPersistenciaCamiDriver {
    public static Scanner scan = new Scanner(System.in);
    public static ControladorPersistenciaCami controladorPersistenciaCami = new ControladorPersistenciaCami();
    public static ControladorCami controladorCami;
    public static ArrayList<String> camins;

    public static void menu() {
        System.out.println("1\tImportar camins");
        System.out.println("2\tImportar camins objecte");
        System.out.println("3\tExportar camins objecte");
        System.out.println("0\tSortir");
    }

    public static int llegir_enter(int minim, int maxim) {
        int n = scan.nextInt();
        while (n < minim || n > maxim) {
            System.out.println("Error: tens que escriure un nombre entre " + minim + " i " + maxim);
            n = scan.nextInt();
        }
        return n;
    }

    public static void importar() {
        scan.nextLine();
        System.out.println("Introdueix la ruta sencera on importar el fitxer de camins (Ej: C:\\Prop\\Camins.txt): ");
        String ruta = scan.nextLine();
        try {
            camins = controladorPersistenciaCami.importar_camins(ruta);
            System.out.println("Importacio completada");
            System.out.println("Inicializtem el controlador de camins amb els camins exportats i llistem el contingut; ");
            try {
                controladorCami = new ControladorCami(camins);
                camins = controladorCami.get_camins();
                System.out.println("Camins disponibles: " + camins.size());
                for (int i = 0; i < camins.size(); ++i) System.out.println(camins.get(i));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void importar_objecte() {
        scan.nextLine();
        System.out.println("Introdueix la ruta ABSOLUTA on importar el fitxer de camins (Ej: C:\\Prop\\): ");
        String ruta = scan.nextLine();
        try {
            camins = controladorPersistenciaCami.importar_camins_objecte(ruta);
            System.out.println("Importacio completada");
            System.out.println("Inicializtem el controlador de camins amb els camins exportats i llistem el contingut; ");
            try {
                controladorCami = new ControladorCami(camins);
                camins = controladorCami.get_camins();
                System.out.println("Camins disponibles: " + camins.size());
                for (int i = 0; i < camins.size(); ++i) System.out.println(camins.get(i));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void exportar_objecte() {
        scan.nextLine();
        System.out.println("Introdueix la ruta ABSOLUTA on exportar el fitxer de camins (Ej: C:\\Prop\\): ");
        String ruta = scan.nextLine();
        try {
            camins = controladorCami.get_camins();
            controladorPersistenciaCami.exportar_camins_objecte(ruta, camins);
            System.out.println("Exportacio completada");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        controladorCami = new ControladorCami();
        for (int i = 0; i < 6; ++i) {
            controladorCami.afegir_cami("APAPAPAPAPA", "Esto es un camino de prueba" + i);
        }
        int opcio = 1;
        while (opcio != 0) {
            menu();
            opcio = llegir_enter(0,3);
            switch (opcio) {
                case 1:
                    importar();
                    break;
                case 2:
                    importar_objecte();
                    break;
                case 3:
                    exportar_objecte();
                    break;
            }
        }
    }
}
