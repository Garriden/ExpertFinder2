package expert.finder.utils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Ruben Bagan Benavides on 05/05/2016.
 */

public class Matriu implements Serializable {
    // S'enmmagatzema la matriu esparsa en aquesta estructura on nomes té valors diferents a 0.0.
    protected HashMap<Integer, HashMap<Integer, Double>> data;

    // Numero de files de la matriu, no te perque representar un valor igual al numero real de files que te data.
    protected int nFiles;

    // Numero de columens de la matriu, no te perque representar un valor igual al numero real de columnes que te data.
    protected int nColumnes;


    // Pre:  files > 0; columnes > 0
    // Post: S'inicialitza una matriu esparsa amb les dimensiones files x columnes, tots els elements estan inicialitzats
    //       a zero.
    // Cost: O(1);
    public Matriu(int files, int columnes) {
        this.nFiles = files;
        this.nColumnes = columnes;
        this.data = new HashMap<>(files);
    }

    // Pre:  0 <= fila < Matriu.fila; 0 <= columna < Matriu.columna
    // Post: La matriu implicita té un nou valor igual a "valor" passat per parametre en la fila i columna pasades per paràmetre.
    // Cost: O(1).
    public void set_valor(int fila, int columna, double valor) {
        HashMap<Integer, Double> columnaData = this.data.get(fila);
        if (columnaData != null) {
            if (valor == 0.0) {
                columnaData.remove(columna);
            }
            else {
                columnaData.put(columna, valor);
            }
        }
        else if (valor != 0.0) {
            this.data.put(fila, new HashMap<>());
            this.data.get(fila).put(columna, valor);
        }
    }

    // Pre:  0 <= fila < Matriu.fila; 0 <= columna < Matriu.columna
    // Post: Retorna el valor en la fila i columna pasades per paràmetre de la matriu implicita.
    // Cost: O(1).
    public double get_valor(int fila, int columna) {
        HashMap<Integer, Double> columnaData = this.data.get(fila);
        if (columnaData != null) {
            Double valor = columnaData.get(columna);
            if (valor != null) return valor;
            return 0.0;
        }
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
        this.data.remove(fila);
        HashMap<Integer, HashMap<Integer, Double>> copiaData = new HashMap<>();
        for (Integer filaData : this.data.keySet()) {
            if (filaData > fila) {
                copiaData.put(filaData - 1, this.data.get(filaData));
            }
            else if (filaData < fila) {
                copiaData.put(filaData, this.data.get(filaData));
            }
        }
        this.data = copiaData;

        --this.nFiles;
    }

    // Pre:  0 <= columna < Matriu.fila; El numero de columnes de la matriu >= 2.
    // Post: S'ha eliminat de la matriu implícita la columna pasada com a paràmetre.
    // Cost: O(n^2).
    public void eliminar_columna(int columna) {
        for (Integer filaData : this.data.keySet()) {
            HashMap<Integer, Double> copiaColumna = new HashMap<>();
            for (Integer columnaData : this.data.get(filaData).keySet()) {
                if (columnaData > columna) {
                    copiaColumna.put(columnaData-1, this.data.get(filaData).get(columnaData));
                }
                else if (columnaData < columna) {
                    copiaColumna.put(columnaData, this.data.get(filaData).get(columnaData));
                }
            }
            this.data.put(filaData, copiaColumna);
        }

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

        for (Integer fila : this.data.keySet()) {
            HashMap<Integer, Double> columnes = this.data.get(fila);
            for (Integer columna : columnes.keySet()) {
                m.set_valor(fila, columna, columnes.get(columna));
            }
        }

        for (Integer fila : b.data.keySet()) {
            HashMap<Integer, Double> columnes = b.data.get(fila);
            for (Integer columna : columnes.keySet()) {
                double valor = m.get_valor(fila, columna);
                valor = valor + columnes.get(columna);
                m.set_valor(fila, columna, valor);
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

        for (Integer fila : this.data.keySet()) {
            HashMap<Integer, Double> columnes = this.data.get(fila);
            for (Integer columna : columnes.keySet()) {
                m.set_valor(fila, columna, columnes.get(columna));
            }
        }

        for (Integer fila : b.data.keySet()) {
            HashMap<Integer, Double> columnes = b.data.get(fila);
            for (Integer columna : columnes.keySet()) {
                double valor = m.get_valor(fila, columna);
                valor = valor - columnes.get(columna);
                m.set_valor(fila, columna, valor);
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

        for (Integer fila : this.data.keySet()) {
            HashMap<Integer, Double> columnes = this.data.get(fila);
            for (Integer columna : columnes.keySet()) {
                HashMap<Integer, Double> filaB = b.data.get(columna);
                for (Integer columnaB : filaB.keySet()) {
                    double valor = m.get_valor(fila, columnaB);
                    valor = valor + (this.data.get(fila).get(columna) * filaB.get(columnaB));
                    m.set_valor(fila,columnaB, valor);
                }
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
    // Cost: O(n^2).
    public Matriu normalitzar_fila() {
        Matriu m = new Matriu(this.nFiles, this.nColumnes);
        for (Integer fila : this.data.keySet()) {
            double norma = 0.0;
            HashMap<Integer, Double> columnes = this.data.get(fila);
            for (Integer columna : columnes.keySet()) {
                norma += Math.pow(columnes.get(columna), 2);
            }

            if (norma != 0.0) {
                norma = Math.sqrt(norma);

                for (Integer columna : columnes.keySet()) {
                    m.set_valor(fila, columna, (columnes.get(columna)/norma));
                }
            }
        }

        return m;
    }

    // Pre:  Cert.
    // Post: Retorna una Matriu C com a resultat de normalitzar per columnes la matriu implicita
    // Cost: O(n^2)
    public Matriu normalitzar_columna() {
        Matriu m = new Matriu(this.nFiles, this.nColumnes);
        
        for (int i = 0; i < this.nColumnes; ++i) {
            double norma = 0.0;
            for (Integer fila : this.data.keySet()) {
                norma += Math.pow(this.get_valor(fila, i),2);
            }
            if (norma != 0.0) {
                norma = Math.sqrt(norma);
                for (Integer fila : this.data.keySet()) {
                    m.set_valor(fila, i, (this.get_valor(fila, i)/norma));
                }
            }
        }                
        
        return m;
    }
    
    // Pre:  Cert.
    // Post: Retorna una referencia al hashmap de la matriu
    // Cost: O(1)
    public HashMap<Integer, HashMap<Integer, Double>> get_hashmap() {
        return this.data;
    }

    // Pre:  Cert.
    // Post: Retorna una copia de la matriu.
    // Cost: O(n^2)
    public Matriu copia_profunditat() {
        Matriu copia = new Matriu(this.nFiles, this.nColumnes);
        copia.data = (HashMap<Integer, HashMap<Integer, Double>>) this.data.clone();
        return copia;
    }
}
