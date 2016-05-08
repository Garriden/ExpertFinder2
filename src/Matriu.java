import java.io.Serializable;
import java.util.*;

/**
 * Created by Ruben Bagan Benavides on 05/05/2016.
 */

public class Matriu implements Serializable{
    private class Index implements Comparator<Index> {
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

    public Matriu(int files, int columnes) {
        this.nFiles = files;
        this.nColumnes = columnes;
        this.tmpIndex = new Index(0,0);
        this.data = new HashMap<>(files*32);
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
    public Matriu get_fila_iessima(int fila) {
        Matriu m = new Matriu(1, this.nColumnes);
        for (int i = 0; i < this.nColumnes; ++i) m.set_valor(0, i, this.get_valor(fila, i));
        return m;
    }

    // Pre:  0 <= columna < Matriu.columna
    // Post: Retorna una Matriu C coma resultat de obtenir la columna i-èssima de la matriu implícita.
    // Cost: O(n).
    public Matriu get_columna_iessima(int columna) {
        Matriu m = new Matriu(this.nFiles, 1);
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
    public Matriu sumar(Matriu b) {
        Matriu m = new Matriu(this.nFiles, this.nColumnes);
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
    public Matriu restar(Matriu b) {
        Matriu m = new Matriu(this.nFiles, this.nColumnes);
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
    public Matriu multiplicar(Matriu b) {
        Matriu m = new Matriu(this.nFiles, b.nColumnes);
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

    // Pre:  Cert.
    // Post: Retorna una matriu C com a resultat de transposar la matriu implícita.
    // Cost: O(n²).
    public Matriu transposar() {
        Matriu m = new Matriu(this.nColumnes, this.nFiles);
        for (int i = 0; i < this.nColumnes; ++i) {
            for (int j = 0; j < this.nFiles; ++j) {
                m.set_valor(i,j, this.get_valor(j,i));
            }
        }

        return m;
    }

    // Pre:  Cert.
    // Post: Retorna una Matriu C com a resultat de normalitzar per files la matriu implícita.
    // Cost: O(n²).
    public Matriu normalitzar_fila() {
        Matriu m = new Matriu(this.nFiles, this.nColumnes);
        for (int i = 0; i < this.nFiles; i++) {
            double norma = 0.0;
            for (int j = 0; j < this.nColumnes; ++j) {
                norma += Math.pow(this.get_valor(i,j), 2);
            }
            if (norma != 0.0) {
                norma = Math.sqrt(norma);
                for (int j = 0; j < this.nColumnes; ++j) {
                    m.set_valor(i,j, (this.get_valor(i,j) / norma));
                }
            }
        }

        return m;
    }

    // Pre:  Cert.
    // Post: Retorna una Matriu C com a resultat de normalitzar per columnes la matriu implícita.
    // Cost: O(n²).
    public Matriu normalitzar_columna() {
        Matriu m = new Matriu(this.nFiles, this.nColumnes);
        for (int i = 0; i < this.nColumnes; i++) {
            double norma = 0.0;
            for (int j = 0; j < this.nFiles; ++j) {
                norma += Math.pow(this.get_valor(j,i), 2);
            }
            if (norma != 0.0) {
                norma = Math.sqrt(norma);
                for (int j = 0; j < this.nFiles; ++j) {
                    m.set_valor(j,i, (this.get_valor(j,i) / norma));
                }
            }
        }

        return m;
    }

    // Pre:  Cert.
    // Post: Retorna una copia de la matriu implícita, amb les mateixes dades i dimensions.
    // Cost: O(n^2);
    public Matriu copia_profunditat() {
        Matriu copia = new Matriu(this.nFiles, this.nColumnes);
        copia.data = (HashMap<Index, Double>) this.data.clone();
        return copia;
    }
}
