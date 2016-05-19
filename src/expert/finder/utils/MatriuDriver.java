package expert.finder.utils;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ruben Bagan Benavides on 11/04/2016.
 */

public class MatriuDriver {
    private static ArrayList<Matriu> cjtMatrius = new ArrayList<Matriu>();
    private static Scanner scan = new Scanner(System.in);

    public static  int llegir_enter(int minim, int maxim) {
        int n = scan.nextInt();
        while (n < minim || n > maxim) {
            System.out.println("Error: tens que escriure un nombre entre " + minim + " i " + maxim);
            n = scan.nextInt();
        }
        return n;
    }

    public static  void print_matriu(Matriu m) {
        for (int i = 0; i < m.get_nombre_files(); i++) {
            for (int j = 0; j < m.get_nombre_columnes(); j++) {
                System.out.printf("%.2f\t", m.get_valor(i, j));
            }
            System.out.println();
        }
    }

    public static  void llistar_matrius() {
        System.out.println("Llistar matrius: ");
        int id = 0;
        for (Matriu m : cjtMatrius) {
            System.out.println("ID: " + id);
            print_matriu(m);
            System.out.println();
            ++id;
        }
    }

    public static  int llegir_natural() {
        int n = scan.nextInt();
        while (n <= 0) {
            System.out.println("Tens que escriure un nombre natural mes gran que 0, (el zero no el tractem com a natural)");
            n = scan.nextInt();
        }
        return n;
    }

    public static  Matriu demanar_matriu(String msg) {
        llistar_matrius();
        System.out.println(msg);
        int id = llegir_enter(0, cjtMatrius.size() - 1);
        return cjtMatrius.get(id);
    }

    public static  int menu() {
        System.out.println("Per obtenir el llistat de identificadors de les matrius tria l'opcio 16");
        System.out.println("1\t Nova matriu");
        System.out.println("2\t Multiplicar");
        System.out.println("3\t Sumar");
        System.out.println("4\t Restar");
        System.out.println("5\t Transposar");
        System.out.println("6\t Normalitzar per files");
        System.out.println("7\t Normalitzar per columnes");
        System.out.println("8\t Obtenir fila i-èssima");
        System.out.println("9\t Obtenir columna i-èssima");
        System.out.println("10\t Inicialitzar fila i-èssima");
        System.out.println("11\t Inicialitzar columna i-èssima");
        System.out.println("12\t Eliminar columna");
        System.out.println("13\t Eliminar fila");
        System.out.println("14\t Afegir columna");
        System.out.println("15\t Afegir fila");
        System.out.println("16\t Llistar matrius");
        System.out.println("17\t Demo");
        System.out.println("0\t Sortir");
        System.out.println("Escriu una opció: ");
        return llegir_enter(0, 17);
    }

    public static  void nova_matriu() {
        System.out.println("Introdueix el nombre de files i columnes (ej: 4 2): ");
        int files = llegir_natural();
        int columnes = llegir_natural();
        System.out.println("Introdueix les dades: ");

        Matriu m = new Matriu(files, columnes);
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                m.set_valor(i, j, scan.nextDouble());
            }
        }
        cjtMatrius.add(m);
    }

    public static  void multiplicar() {
        Matriu a = demanar_matriu("Introdueix l'identificador de la matriu A: ");
        Matriu b = demanar_matriu("Introdueix l'identificador de la matriu B: ");
        if (a.get_nombre_columnes() == b.get_nombre_files()) {
            System.out.println("expert.finder.consulta.Resultat de la multiplicacio: ");
            print_matriu(a.multiplicar(b));
        }
        else {
            System.out.println("Error: Les dimensions de les matrius son incompatibles");
        }
    }

    public static  void sumar() {
        Matriu a = demanar_matriu("Introdueix l'identificador de la matriu A: ");
        Matriu b = demanar_matriu("Introdueix l'identificador de la matriu B: ");
        if (a.get_nombre_columnes() == b.get_nombre_columnes() && a.get_nombre_files() == b.get_nombre_files()){
            System.out.println("expert.finder.consulta.Resultat de la suma: ");
            print_matriu(a.sumar(b));
        }
        else {
            System.out.println("Error: Les dimensions de les matrius son incompatibles");
        }
    }

    public static  void restar() {
        Matriu a = demanar_matriu("Introdueix l'identificador de la matriu A: ");
        Matriu b = demanar_matriu("Introdueix l'identificador de la matriu B: ");
        if (a.get_nombre_columnes() == b.get_nombre_columnes() && a.get_nombre_files() == b.get_nombre_files()) {
            System.out.println("expert.finder.consulta.Resultat de la resta: ");
            print_matriu(a.restar(b));
        }
        else {
            System.out.println("Error: Les dimensions de les matrius son incompatibles");
        }
    }

    public static  void transposar() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        System.out.println("expert.finder.consulta.Resultat de la transposada: ");
        print_matriu(m.transposar());
    }

    public static  void normalitzar_files() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        System.out.println("expert.finder.consulta.Resultat de la normalitzada per files: ");
        print_matriu(m.normalitzar_fila());
    }

    public static  void normalitzar_columnes() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        System.out.println("expert.finder.consulta.Resultat de la normalitzada per columnes: ");
        print_matriu(m.normalitzar_columna());
    }

    public static  void obtenir_fila_iessima() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        System.out.println("IIntrodueix la fila i-èssima que vols obtenir [1,"+ m.get_nombre_files() + "]: ");
        int fila = llegir_enter(1, m.get_nombre_files());
        --fila;
        System.out.println("Fila i-èssima: ");
        print_matriu(m.get_fila_iessima(fila));
    }

    public static  void obtenir_columna_iessima() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        System.out.println("Introdueix la columna i-èssima que vols obtenir [1,"+ m.get_nombre_columnes() + "]: ");
        int columna = llegir_enter(1, m.get_nombre_columnes());
        --columna;
        System.out.println("Columna i-èssima: ");
        print_matriu(m.get_columna_iessima(columna));

    }

    public static  void inicialitzar_fila_iessima() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        System.out.println("Introdueix la fila i-èssima que vols inicialitzar [1,"+ m.get_nombre_files()+"]: ");
        int fila = llegir_enter(1, m.get_nombre_files());
        --fila;
        System.out.println("Escriu " + m.get_nombre_columnes() + " reals: ");
        for (int i = 0; i < m.get_nombre_columnes(); i++) {
            double valor = scan.nextDouble();
            m.set_valor(fila, i, valor);
        }
        print_matriu(m);
    }

    public static  void inicialitzar_columna_iessima() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        System.out.println("Introdueix la columna i-èssima que vols inicialitzar [1,"+ m.get_nombre_columnes()+"]: ");
        int columna = llegir_enter(1, m.get_nombre_columnes());
        --columna;
        System.out.println("Escriu " + m.get_nombre_files() + " reals: ");
        for (int i = 0; i < m.get_nombre_files(); i++) {
            double valor = scan.nextDouble();
            m.set_valor(i, columna, valor);
        }
        print_matriu(m);
    }

    public static  void eliminar_columna_iessima() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        System.out.println("Introdueix la columna i-èssima que vols eliminar [1,"+ m.get_nombre_columnes()+"]: ");
        int columna = llegir_enter(1, m.get_nombre_columnes());
        --columna;
       // m.eliminar_columna(columna);
        print_matriu(m);
    }

    public static  void eliminar_fila_iessima() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        System.out.println("Introdueix la fila i-èssima que vols eliminar [1,"+ m.get_nombre_files()+"]: ");
        int fila = llegir_enter(1, m.get_nombre_files());
        --fila;
        //m.eliminar_fila(fila);
        print_matriu(m);
    }

    public static  void afegir_columna() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
        //m.afegir_columna();
        print_matriu(m);
    }

    public static  void afegir_fila() {
        Matriu m = demanar_matriu("Introdueix l'identificador de la matriu: ");
       // m.afegir_fila();
        print_matriu(m);
    }

    public static void demo() {
        System.out.println("Creo una matriu anomenada A de 4 x 7: ");
        Matriu a = new Matriu(4,7);
        print_matriu(a);
        System.out.println("Inicialitzare la fila 3");
        double[] dades_fila_iessima = {1,2,6,1,0,3,4};
        a.set_fila_iessima(2, dades_fila_iessima);
        print_matriu(a);
        System.out.println("Inicialitzare la columna 3");
        double[] dades_columna_iessima = {5,8.1,4,3.1};
        a.set_columna_iessima(2, dades_columna_iessima);
        print_matriu(a);
        System.out.println("Inicialitzare tota una matriu: ");
        for (int i = 0; i < a.get_nombre_files(); ++i) {
            for (int j = 0; j < a.get_nombre_columnes(); ++j) {
                a.set_valor(i,j, ((i-j+4*2+(i+j)-5)+ ThreadLocalRandom.current().nextDouble(2.0,10.0)));
            }
        }
        print_matriu(a);
        System.out.println("Inicialitzare la posicio 0,0 amb valor 500");
        a.set_valor(0,0, 500.0);
        print_matriu(a);
        System.out.println("Escriu la fila 2");
        print_matriu(a.get_fila_iessima(1));
        System.out.println("Escriu la columna 1");
        print_matriu(a.get_columna_iessima(0));
        System.out.println("Elimina la fila 3");
        a.eliminar_fila(2);
        print_matriu(a);
        System.out.println("Elimina la columna 3");
        a.eliminar_columna(2);
        print_matriu(a);
        System.out.println("Afeigr una nova fila");
        a.afegir_fila();
        print_matriu(a);
        System.out.println("Afegir una nova columna");
        a.afegir_columna();
        print_matriu(a);
        System.out.println("Elimina la ultima columna i fila afegida i la primera columna i primera fila ");
        a.eliminar_columna(0);
        a.eliminar_columna(a.get_nombre_columnes() - 1);
        a.eliminar_fila(0);
        a.eliminar_fila(a.get_nombre_files() - 1);
        print_matriu(a);
        System.out.println("Duplicare la matriu A en una nova matrui B");
        Matriu b = a;
        print_matriu(b);
        System.out.println("Suma A + B");
        print_matriu(a.sumar(b));
        System.out.println("Resta A - B");
        print_matriu(a.restar(b));
        System.out.println("Declarem una nova matriu B");
        b = new Matriu(5,2);
        System.out.println("Inicialitzare tota una matriu: ");
        for (int i = 0; i < b.get_nombre_files(); ++i) {
            for (int j = 0; j < b.get_nombre_columnes(); ++j) {
                b.set_valor(i,j, ((i-j+4*2+(i+j)-5)+ ThreadLocalRandom.current().nextDouble(2.0,10.0)));
            }
        }
        print_matriu(b);
        System.out.println("Multiplica A * B");
        print_matriu(a.multiplicar(b));
        System.out.println("Elimina la ultima columna");
        a.eliminar_columna(a.get_nombre_columnes() - 1);
        print_matriu(a);
        System.out.println("Transposa la matriu A");
        print_matriu(a.transposar());
        System.out.println("Normalitza la matriu A per files");
        print_matriu(a.normalitzar_fila());
        System.out.println("Normalitza la matyrui A per Columnes");
        print_matriu(a.normalitzar_columna());

        Set<Matriu.Index> index = a.get_index_matriu();
        System.out.println();
    }

    public static void main(String[] args) {
        int opcio = menu();
        while (opcio != 0) {
            if (opcio != 1 && opcio != 17 && cjtMatrius.isEmpty()) {
                System.out.println("Necesites crear una matriu primer");
            }
            else {
                switch (opcio) {
                    case 1:
                        nova_matriu();
                        break;
                    case 2:
                        multiplicar();
                        break;
                    case 3:
                        sumar();
                        break;
                    case 4:
                        restar();
                        break;
                    case 5:
                        transposar();
                        break;
                    case 6:
                        normalitzar_files();
                        break;
                    case 7:
                        normalitzar_columnes();
                        break;
                    case 8:
                        obtenir_fila_iessima();
                        break;
                    case 9:
                        obtenir_columna_iessima();
                        break;
                    case 10:
                        inicialitzar_fila_iessima();
                        break;
                    case 11:
                        inicialitzar_columna_iessima();
                        break;
                    case 12:
                        eliminar_columna_iessima();
                        break;
                    case 13:
                        eliminar_fila_iessima();
                        break;
                    case 14:
                        afegir_columna();
                        break;
                    case 15:
                        afegir_fila();
                        break;
                    case 16:
                        llistar_matrius();
                        break;
                    case 17:
                        demo();
                        break;
                }
            }
            opcio = menu();
        }
    }
}