package Cami;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ruben Bagan Benavides on 13/05/2016.
 */
public class ControladorIECamiDriver {
    private static Scanner scan = new Scanner(System.in);
    private static ControladorIECami controladorIECami = new ControladorIECami();
    private static ArrayList<Cami> camins;
    public static void menu() {
        System.out.println("1\tImportar camins");
        System.out.println("2\tExportar camins");
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
            ArrayList<Cami> nous_camins = controladorIECami.importar_camins(ruta);
            System.out.println("Importacio completada");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void exportar() {
        scan.nextLine();
        System.out.println("Introdueix la ruta sencera on exportar el fitxer de camins (Ej: C:\\Prop\\Camins.txt): ");
        String ruta = scan.nextLine();
        try {
            controladorIECami.exportar_camins(ruta, camins);
            System.out.println("Exportacio completada");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        camins = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            Cami c = new Cami("APAAPAPAPAPA", "Esto es un camino de prueba" + i);
            camins.add(c);
        }
        int opcio = 1;
        while (opcio != 0) {
            menu();
            opcio = llegir_enter(0,2);
            switch (opcio) {
                case 1:
                    importar();
                    break;
                case 2:
                    exportar();
                    break;
            }
        }
    }
}
