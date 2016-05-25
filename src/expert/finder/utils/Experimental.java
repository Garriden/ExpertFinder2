package expert.finder.utils;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ruben Bagan Benavides on 23/05/2016.
*/

public class Experimental {
    public class Index implements Comparator<Index>, Serializable {
        public int fila;
        public int columna;

        public Index(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }

        @Override
        public boolean equals(Object indexB) {
            if (indexB == null || getClass() != indexB.getClass()) return false;
            if (this == indexB) return true;

            Index b = (Index) indexB;

            return b.fila == this.fila && b.columna == this.columna;
        }

        @Override
        public int hashCode() {
            return 31*fila+columna;
        }

        @Override
        public int compare(Index o1, Index o2) {
            return o1.fila - o2.fila;
        }

    }

    private HashMap<Index, Double> data;
    private int nFiles;
    private int nColumnes;
    private Index tmpIndex;

    public Experimental(int files, int columnes) {
        this.nFiles = files;
        this.nColumnes = columnes;
        this.tmpIndex = new Index(0,0);
        this.data = new HashMap<>(files*64);
    }

    // Pre:  0 <= fila < Matriu.fila; 0 <= columna < Matriu.columna
    // Post: La matriu implicita té un nou valor en la fila i columna pasades per paràmetre.
    // Cost: O(1).
    public void set_valor(int fila, int columna, double valor) {
        Index i = new Index(fila, columna);
        if (this.data.containsKey(i)) {
            if (valor == 0.0) this.data.remove(i);
            else this.data.put(i, valor);
        }
        else if (valor != 0.0) this.data.put(i, valor);
    }

    // Pre:  0 <= fila < Matriu.fila; 0 <= columna < Matriu.columna
    // Post: Retorna el valor en la fila i columna pasades per paràmetre de la matriu implicita.
    // Cost: O(1).
    public double get_valor(int fila, int columna) {
        this.tmpIndex.fila = fila;
        this.tmpIndex.columna = columna;
        Double valor = this.data.get(this.tmpIndex);
        if (valor != null) return valor;
        return 0.0;
    }

    // Pre:  Cert.
    // Post: Retorna el nombre de files que té la matriu implícita.
    // Cost: O(1).
    public int get_nombre_files() {
        return this.nFiles;
    }

    // Pre:  Cert.
    // Post: Retorna el nombre de columnes que té la matriu implícita.
    // Cost: O(1).
    public int get_nombre_columnes() {
        return this.nColumnes;
    }

    // Pre:  0 <= fila < Matriu.Fila; data != NULL. La longitud de data té que ser igual al numero de columnes de la
    //       matriu implícita.
    // Post: La fila i-èssima de la matriu implícita té com a valors data.
    // Cost: O(n)
    public void set_fila_iessima(int fila , double[] data) {
        for (int i = 0; i < data.length; ++i) this.set_valor(fila, i, data[i]);
    }

    // Pre:  0 <= columna < Matriu.columna; data != NULL. La longitud de data té que ser igual al numero de files de la
    //       matriu implícita.
    // Post: La columna i-èssima de la matriu implícita té com a valors data.
    // Cost: O(n)
    public void set_columna_iessima(int columna, double[] data) {
        for (int i = 0; i < data.length; ++i) this.set_valor(i, columna, data[i]);
    }

    // Pre:  0 <= fila < Matriu.fila
    // Post: Retorna una Matriu C coma resultat de obtenir la fila i-èssima de la matriu implícita.
    // Cost: O(n).
    public Experimental get_fila_iessima(int fila) {
        Experimental m = new Experimental(1, this.nColumnes);
        for (int i = 0; i < this.nColumnes; ++i) m.set_valor(0, i, this.get_valor(fila, i));
        return m;
    }

    // Pre:  0 <= columna < Matriu.columna
    // Post: Retorna una Matriu C coma resultat de obtenir la columna i-èssima de la matriu implícita.
    // Cost: O(n).
    public Experimental get_columna_iessima(int columna) {
        Experimental m = new Experimental(this.nFiles, 1);
        for (int i = 0; i < this.nFiles; ++i) m.set_valor(i, 0, this.get_valor(i, columna));
        return m;
    }

    // Pre:  0 <= fila < Matriu.fila; El nombre de files de la matriu >= 2.
    // Post: S'ha eliminat de la matriu implícita la fila pasada com a paràmetre.
    // Cost: O(n).
    public void eliminar_fila(int fila) {
        HashMap<Index, Double>  dataCopy = new HashMap<>((this.nFiles - 1) * this.nColumnes);

        for (Map.Entry<Index,Double> e : this.data.entrySet()) {
            if (e.getKey().fila != fila) {
                Index idx = e.getKey();
                if (idx.fila > fila) idx.fila -= 1;
                dataCopy.put(idx, e.getValue());
            }
        }

        this.data = dataCopy;
        --this.nFiles;
    }

    // Pre:  0 <= columna < Matriu.fila; El numero de columnes de la matriu >= 2.
    // Post: S'ha eliminat de la matriu implícita la columna pasada com a paràmetre.
    // Cost: O(n).
    public void eliminar_columna(int columna) {
        HashMap<Index, Double>  dataCopy = new HashMap<>(this.nFiles * (this.nColumnes - 1));

        for (Map.Entry<Index,Double> e : this.data.entrySet()) {
            if (e.getKey().columna != columna) {
                Index idx = e.getKey();
                if (idx.columna > columna) idx.columna -= 1;
                dataCopy.put(idx, e.getValue());
            }
        }

        this.data = dataCopy;
        --this.nColumnes;
    }

    // Pre:  Cert.
    // Post: S'ha afegit a la matriu implícita una fila on tots els seus valors són 0. Aquesta nova fila és la
    //       última fila de la matriu. El numero de files de la matriu és Matriu.files + 1.
    // Cost: O(1).
    public void afegir_fila() {
        ++this.nFiles;
    }

    // Pre:  Cert.
    // Post: S'ha afegit a la matriu implícita una columna on tots els seus valors són 0. Aquesta nova columna és la
    //       última columna de la matriu. El numero de columnes de la matriu és Matriu.columnes + 1.
    // Cost: O(1).
    public void afegir_columna() {
        ++this.nColumnes;
    }

    // Pre:  Anomanem la matriu implícita com A i la matriu pasada per referència com B, tant A com B tenen que tenir
    //       mateixes dimensions. B != NULL.
    // Post: Retorna una matriu C com a resultat de la suma de la matriu implícita (A) amb la matriu B. C = A + B.
    // Cost: O(n^2).
    public Experimental sumar(Matriu b) {
        Experimental m = new Experimental(this.nFiles, this.nColumnes);
        for (int i = 0; i < this.nFiles; ++i) {
            for (int j = 0; j < this.nColumnes; ++j) {
                double valor = this.get_valor(i, j) + b.get_valor(i,j);
                m.set_valor(i,j,valor);
            }
        }
        return m;
    }

    // Pre:  Anomanem la matriu implícita com A i la matriu pasada per referència com B, tant A com B tenen que tenir
    //       mateixes dimensions. B != NULL.
    // Post: Retorna una matriu C com a resultat de la resta de la matriu implícita (A) amb la matriu B. C = A - B.
    // Cost: O(n^2).
    public Experimental restar(Matriu b) {
        Experimental m = new Experimental(this.nFiles, this.nColumnes);
        for (int i = 0; i < this.nFiles; ++i) {
            for (int j = 0; j < this.nColumnes; ++j) {
                double valor = this.get_valor(i, j) - b.get_valor(i,j);
                m.set_valor(i,j,valor);
            }
        }
        return m;
    }

    // Pre:  Anomanem la matriu implícita com A i la matriu pasada per referència com B, B != NULL. El nombre files de
    //		 la Matriu A té que ser igual al nombre de columnes de la Matriu B.
    // Post: Retorna una matriu C com a resultat de multiplicar la matriu implícita (A) amb la matriu B. C = A * B.
    // Cost: O(n³).
    public Experimental multiplicar(Experimental b) {
        Experimental m = new Experimental(this.nFiles, b.nColumnes);
        for (int i = 0; i < this.nFiles; ++i) {
            for (int j = 0; j < b.nColumnes; ++j) {
                double valor = 0.0;
                for (int k = 0; k < b.nFiles; ++k) {
                    valor += this.get_valor(i, k) * b.get_valor(k, j);
                }
                m.set_valor(i, j, valor);
            }
        }
        return m;
    }
/*
        for (Index indexA : this.data.keySet()) {
            for (Index indexB : b.data.keySet()) {
                if (indexA.columna == indexB.fila) {
                    double valor = m.get_valor(indexA.fila, indexB.columna);
                    valor = valor + (this.get_valor(indexA.fila, indexA.columna) * b.get_valor(indexB.fila, indexB.columna));
                    m.set_valor(indexA.fila, indexB.columna, valor);
                }
            }
        }*/


    public static void print_matriu(Experimental a, Matriu b) {
        File rutaFitxerA = new File("resultadoA.txt");
        File rutaFitxerB = new File("resultadoB.txt");

        BufferedWriter fitxerA;
        BufferedWriter fitxerB;

        try {
            fitxerA = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaFitxerA),"ISO-8859-15"));
            fitxerB = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaFitxerB),"ISO-8859-15"));
            for (int i = 0; i < a.get_nombre_files(); ++i) {
                for (int j = 0; j < a.get_nombre_columnes(); ++j) {
                    fitxerA.write("\t\t" + a.get_valor(i,j));
                }
                fitxerA.newLine();
            }


            for (int i = 0; i < b.get_nombre_files(); ++i) {
                for (int j = 0; j < b.get_nombre_columnes(); ++j) {
                    fitxerB.write("\t\t" + a.get_valor(i,j));
                }
                fitxerB.newLine();
            }
            fitxerA.close();
            fitxerB.close();
        } catch (IOException e) {
        }


    }

    public static void main(String[] args) {
        Experimental a = new Experimental(1000,1000);
        Experimental b = new Experimental(1000,1000);
        Matriu a1 = new Matriu(1000, 1000);
        Matriu b1 = new Matriu(1000, 1000);

        for (int i = 0; i < 1000; ++i) {
            for (int j = 0; j < 1000; ++j) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            a.set_valor(i, j, ((i - j + 4 * 2 + (i + j) - 5) + ThreadLocalRandom.current().nextDouble(2.0, 10.0)));
                            b.set_valor(i, j, ((i - j + 4 * 2 + (i + j) - 5) + ThreadLocalRandom.current().nextDouble(2.0, 10.0)));
                            a1.set_valor(i, j, ((i - j + 4 * 2 + (i + j) - 5) + ThreadLocalRandom.current().nextDouble(2.0, 10.0)));
                            b1.set_valor(i, j, ((i - j + 4 * 2 + (i + j) - 5) + ThreadLocalRandom.current().nextDouble(2.0, 10.0)));
                        }
                    }
                }
            }
        }


        System.out.println("De las viejas! ");
        long t1 = System.currentTimeMillis();
        Experimental c = a.multiplicar(b);
        System.out.println("Tiempo total: " + (System.currentTimeMillis() - t1) + " ms");

        System.out.println("Tiempo de las nuevas!");
        long t2 = System.currentTimeMillis();
        Matriu m = a1.multiplicar(b1);
        System.out.println("Tiempo total: " + (System.currentTimeMillis() - t2) + " ms");

        print_matriu(c, m);


    }
}

