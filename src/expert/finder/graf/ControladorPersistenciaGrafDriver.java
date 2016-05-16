package expert.finder.graf;
import expert.finder.node.Node;
import expert.finder.utils.Matriu;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ruben Bagan Benavides on 14/05/2016.
 */
public class ControladorPersistenciaGrafDriver {
    private static ControladorPersistenciaGraf controladorPersistenciaGraf = new ControladorPersistenciaGraf();
    private static Scanner scan = new Scanner(System.in);
    private static Graf graf;

    public static void menu() {
        System.out.println("1\tImportar Graf a partir d'una ruta.");
        System.out.println("2\tImportar Graf com a objecte");
        System.out.println("3\tExportar Graf com a objecte");
        System.out.println("0\tSortir");
    }

    public static  int llegir_enter(int minim, int maxim) {
        int n = scan.nextInt();
        while (n < minim || n > maxim) {
            System.out.println("Error: tens que escriure un nombre entre " + minim + " i " + maxim);
            n = scan.nextInt();
        }
        return n;
    }

    public static void print_graf(String ruta, Graf graf) {
        File nodesGraf = new File(ruta+"nodes_graf.txt");
        File relacionsGraf = new File(ruta+"relacions_graf.txt");
        BufferedWriter fitxerNodes;
        BufferedWriter fitxerRelacions;
        try {
            fitxerNodes = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nodesGraf),"ISO-8859-15"));
            fitxerRelacions = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(relacionsGraf),"ISO-8859-15"));

            ArrayList<Node> autor = graf.get_autor();
            ArrayList<Node> paper = graf.get_paper();
            ArrayList<Node> terme = graf.get_terme();
            ArrayList<Node> conferencia = graf.get_conferencia();
            Matriu paperAutor = graf.get_paper_autor();
            Matriu paperTerme = graf.get_paper_terme();
            Matriu paperConf = graf.get_paper_conferencia();

            fitxerNodes.write("Llista de Nodes Autor: ");
            fitxerNodes.newLine();
            for (int i = 0; i < autor.size(); ++i) {
                Node n = autor.get(i);
                fitxerNodes.write("Id: " + n.get_id() + "\t Nom: " + n.get_nom() + "\t Tipus: Autor");
                fitxerNodes.newLine();
            }

            fitxerNodes.write("Llista de Nodes Terme: ");
            fitxerNodes.newLine();
            for (int i = 0; i < terme.size(); ++i) {
                Node n = terme.get(i);
                fitxerNodes.write("Id: " + n.get_id() + "\t Nom: " + n.get_nom() + "\t Tipus: Terme");
                fitxerNodes.newLine();
            }

            fitxerNodes.write("Llista de Nodes Paper: ");
            fitxerNodes.newLine();
            for (int i = 0; i < paper.size(); ++i) {
                Node n = paper.get(i);
                fitxerNodes.write("Id: " + n.get_id() + "\t Nom: " + n.get_nom() + "\t Tipus: Paper");
                fitxerNodes.newLine();
            }

            fitxerNodes.write("Llista de Nodes Conferencia: ");
            fitxerNodes.newLine();
            for (int i = 0; i < conferencia.size(); ++i) {
                Node n = autor.get(i);
                fitxerNodes.write("Id: " + n.get_id() + "\t Nom: " + n.get_nom() + "\t Tipus: Conferencia");
                fitxerNodes.newLine();
            }
            fitxerNodes.close();


            fitxerRelacions.write("Llista de relacions PAPER x AUTOR");
            fitxerRelacions.newLine();
            for (int i = 0; i < paperAutor.get_nombre_files(); ++i) {
                for (int j = 0; j < paperAutor.get_nombre_columnes(); ++j) {
                    if (paperAutor.get_valor(i,j) == 1.0)
                        fitxerRelacions.write("Nom Origen (Paper): " + paper.get(i).get_nom() + "\tNom desti (Autor): " + autor.get(j).get_nom());
                    fitxerRelacions.newLine();
                }
            }


            fitxerRelacions.write("Llista de relacions PAPER x TERME");
            fitxerRelacions.newLine();
            for (int i = 0; i < paperTerme.get_nombre_files(); ++i) {
                for (int j = 0; j < paperTerme.get_nombre_columnes(); ++j) {
                    if (paperTerme.get_valor(i,j) == 1.0)
                        fitxerRelacions.write("Nom Origen (Paper): " + paper.get(i).get_nom() + "\tNom desti (Terme): " + terme.get(j).get_nom());
                    fitxerRelacions.newLine();
                }
            }
            fitxerRelacions.write("Llista de relacions PAPER x CONFERENCIA");
            fitxerRelacions.newLine();
            for (int i = 0; i < paperConf.get_nombre_files(); ++i) {
                for (int j = 0; j < paperConf.get_nombre_columnes(); ++j) {
                    if (paperConf.get_valor(i,j) == 1.0)
                        fitxerRelacions.write("Nom Origen (Paper): " + paper.get(i).get_nom() + "\tNom desti (Conferencia): " + conferencia.get(j).get_nom());
                    fitxerRelacions.newLine();
                }
            }
            fitxerRelacions.close();
        } catch (IOException e) {
            System.out.println("Error: No es pot crear el fitxer resultat_graf en la ruta: " + ruta);
        }
    }

    public static void importar_graf_nou(String ruta) {
        try {
            graf = controladorPersistenciaGraf.importar_graf_nou(ruta);
            System.out.println("Imporacio completada");
            System.out.println("Contingut del graf en aquest ordre: Nodes i despres relacions.");
            System.out.println("Per no saturar la consola s'escriu el contingut en dos fitxers, un per els nodes i un " +
                    "altre per relacions anomenats: nodes_graf.txt i relacions_graf.txt");
            print_graf(ruta, graf);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void importar_graf_objecte(String ruta) {
        try {
            graf = controladorPersistenciaGraf.importar_graf_salvat(ruta);
            System.out.println("Imporacio completada");
            System.out.println("Contingut del graf en aquest ordre: Nodes i despres relacions.");
            System.out.println("Per no saturar la consola s'escriu el contingut en dos fitxers, un per els nodes i un " +
                    "altre per relacions anomenats: nodes_graf.txt i relacions_graf.txt");
            print_graf(ruta, graf);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void exportar_graf_objecte(String ruta) {
        if (graf != null) {
            try {
                controladorPersistenciaGraf.exportar(ruta, graf);
                System.out.println("Exportacio completada");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else System.out.println("Primer tens que importar un graf per poder exportar-ne un.");
    }

    public static void main(String[] args) {
        int opcio = 1;
        graf = null;
        while (opcio != 0) {
            menu();
            opcio = llegir_enter(0,3);
            if (opcio != 0) {
                scan.nextLine();
                System.out.println("Escriu la ruta ABSOLUTA on estan guardats els fitxers de la base de dades: ");
                String ruta = scan.nextLine();
                ruta = ruta + "\\";
                switch (opcio) {
                    case 1:
                        importar_graf_nou(ruta);
                        break;
                    case 2:
                        importar_graf_objecte(ruta);
                        break;
                    case 3:
                        exportar_graf_objecte(ruta);
                        break;
                }
            }
        }
    }
}
