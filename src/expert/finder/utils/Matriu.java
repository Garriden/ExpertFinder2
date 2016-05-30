package expert.finder.utils;

import java.io.Serializable;
import java.util.*;

/**
 * @Author Ruben Bagan Benavides
 */

/**
 * La funcio de la matriu es representar una matriu esparsa on nomes enmmagatzema valors diferents a 0.0. Ideal per
 * representar matrius d'adjacencia molt grans on hi ha aproximadament un 80% de 0.0. Cuants menys 0.0 hi ha mes lent
 * es i ineficient.
 */
public class Matriu implements Serializable {
    // S'enmmagatzema la matriu esparsa en aquesta estructura on nomes té valors diferents a 0.0.
    protected HashMap<Integer, HashMap<Integer, Double>> data;

    // Numero de files de la matriu, no te perque representar un valor igual al numero real de files que te data.
    protected int nFiles;

    // Numero de columens de la matriu, no te perque representar un valor igual al numero real de columnes que te data.
    protected int nColumnes;


    /**
     * Inicialitza una matriu esparsa amb les dimensions passades per parametre. El valor per defecte de qualsevol
     * element Mij es 0, in i es la fila, j la columna. El cost d'executar aquesta funcio es: O(1).
     * @param files indica el numero de files que tindra la matriu; el nombre te que ser un valor positiu
     * @param columnes indica el numero de columnes que tindra la matriu; el nombre te que ser un valor positiu
     */
    public Matriu(int files, int columnes) {
        this.nFiles = files;
        this.nColumnes = columnes;
        this.data = new HashMap<>(files);
    }

    /**
     * Modifica el valor que hi hi en la posicio Mij on i es la fila i j la columna per el nou valor passat per el
     * parametre. El cost d'executar aquesta funcio es: O(1).
     * @param fila conte la fila de la matriu. Te que ser mes gran o igual que 0 i no pot ser superior al numero de
     *             files de la matriu.
     * @param columna conte la columna de la matriu. Te que ser mes gran o igual que 0 i no pot ser superior al numero
     *                de columnes de la matriu.
     * @param valor el valor que tindra la posicio Mij.
     */
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

    /**
     * Retorna el valor que hi ha en la posicio Mij on i es la fila i j es la columna. El cost d'executar aquesta
     * funcio es: O(1).
     * @param fila conte la fila de la matriu. Te que ser mes gran o igual que 0 i no pot ser superior al numero de
     *             files de la matriu.
     * @param columna conte la columna de la matriu. Te que ser mes gran o igual que 0 i no pot ser superior al numero
     *                de columnes de la matriu.
     * @return el valor que hi ha en la posicio Mij
     */
    public double get_valor(int fila, int columna) {
        HashMap<Integer, Double> columnaData = this.data.get(fila);
        if (columnaData != null) {
            Double valor = columnaData.get(columna);
            if (valor != null) return valor;
            return 0.0;
        }
        return 0.0;
    }

    /**
     * Retorna el nombre de files que te la matriu. El cost d'executar aquesta funcio es: O(1)
     * @return Retorna el nombre de files que te la matriu.
     */
    public int get_nombre_files() {
        return this.nFiles;
    }

    /**
     * Retorna el nombre de columnes que te la matriu. El cost d'executar aquesta funcio es: O(1)
     * @return Retorna el nombre de columnes que te la matriu.
     */
    public int get_nombre_columnes() {
        return this.nColumnes;
    }

    /**
     * Inicialitza una fila sencera de la matriu Mi0 on i es la fila pasada per parametre. Els valors que pren aquesta
     * fila son els que hi ha en data. El tamany de data te que ser del mateix tamany que el nombre de columnes de la
     * matriu. El cost d'executar aquesta funcio es: O(n) on n es el nombre de columnes.
     * @param fila conte la fila de la matriu. Te que ser mes gran o igual que 0 i no pot ser superior al numero de
     *             files de la matriu.
     * @param data conte els valors amb els que es modificara la fila; no pot apuntar a una referencia nul-la.
     */
    public void set_fila_iessima(int fila , double[] data) {
        for (int i = 0; i < data.length; ++i) this.set_valor(fila, i, data[i]);
    }

    /**
     * Inicialitza totes les columnes de totes les files de la matriu Mij on i es la fila i j la columna pasada per
     * parametre. Els valors que pren cada columna de cada fila son els que hi ha en data. El tamany de data te que
     * ser del mateix tamany que el nombre de files de la matriu. El cost d'executar aquesta funcio es: O(n) on n es
     * el nombre de files.
     * @param columna conte la columna que modificara per cada fila de la matriu. Te que ser mes gran o igual que 0 i
     *                no pot ser superior al numero de columnes de la matriu.
     * @param data conte els valors amb els que es modificara la cada columna; no pot apuntar a una referencia nul-la.
     */
    public void set_columna_iessima(int columna, double[] data) {
        for (int i = 0; i < data.length; ++i) this.set_valor(i, columna, data[i]);
    }

    /**
     * Retorna una matriu d'una unica fila i n columnes, que conte el contingut de la Mij on i es la fila pasada per
     * parametre.  El cost d'executar aquesta funcio es: O(n) on n es el nombre de columnes.
     * @param fila conte la fila de la matriu. Te que ser mes gran o igual que 0 i no pot ser superior al numero de
     *             files de la matriu.
     * @return Retorna una matriu d'una unica fila i n columnes, que conte el contingut de la Mij on i es la fila
     * pasada per parametre.
     */
    public Matriu get_fila_iessima(int fila) {
        Matriu m = new Matriu(1, this.nColumnes);
        for (int i = 0; i < this.nColumnes; ++i) m.set_valor(0, i, this.get_valor(fila, i));
        return m;
    }

    /**
     * Retorna una matriu de n files i una unica columna, que conte el contingut de la Mij on i es la fila i j es la
     * columna passada per parametre. El cost d'executar aquesta funcio es: O(n) on n es el nombre de files.
     * @param columna conte la columna de la matriu. Te que ser mes gran o igual que 0 i no pot ser superior al numero
     *                de columnes de la matriu.
     * @return Retorna una matriu de n files i una unica columna, que conte el contingut de la Mij on i es la fila i
     * j es la columna passada per parametre.
     */
    public Matriu get_columna_iessima(int columna) {
        Matriu m = new Matriu(this.nFiles, 1);
        for (int i = 0; i < this.nFiles; ++i) m.set_valor(i, 0, this.get_valor(i, columna));
        return m;
    }

    /**
     * S'elimina de la matriu implicita la fila passada per parametre juntament amb totes les seves columnes. El cost
     * d'executar aquesta funcio es: O(n) on n es el nombre de columnes de la fila.
     * @param fila indica la fila de la matriu. Te que ser mes gran o igual que 0 i no pot ser superior al numero de
     *             files de la matriu.
     */
    public void eliminar_fila(int fila) {
        this.data.remove(fila);
        HashMap<Integer, HashMap<Integer, Double>> copiaDataFiles = new HashMap<>();
        for (Integer filaData : this.data.keySet()) {
            if (filaData > fila) {
                copiaDataFiles.put(filaData - 1, this.data.get(filaData));
            }
            else if (filaData < fila) {
                copiaDataFiles.put(filaData, this.data.get(filaData));
            }
        }
        this.data = copiaDataFiles;
        --this.nFiles;
    }

    /**
     * S'elimina de la matriu implicita la columna passada per parametre en totes les seves aparicions en cada fila.
     * El cost d'executar aquesta funcio es: O(n^2) on n es el nombre de files.
     * @param columna indica la columna de la matriu. Te que ser mes gran o igual que 0 i no pot ser superior al numero
     *                de columnes de la matriu.
     */
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

    /**
     * S'ha afegit a la matriu implicita una fila on totes les seves columnes tenen valor 0. Aquesta nova fila es la
     * ultima fila de la matriu. El numero de files de la matriu s'incrementa en 1. El cost d'executar aquesta funcio
     * es: O(1)
     */
    public void afegir_fila() {
        ++this.nFiles;
    }

    /**
     * S'ha afegit a la matriu implicita una columna a totes les files on el seu valor es 0. Aquesta nova columa es la
     * ultima columna de la matriu. El numero de columnes de la matriu s'incrementa en 1. El cost d'executar aquesta
     * funcio es: O(1)
     */
    public void afegir_columna() {
        ++this.nColumnes;
    }

    /**
     * Anomanem la matriu implicita com A i la matriu pasada per referencia com B, tant A com B tenen que tenir
     * les mateixes dimensions. El cost d'executar aquesta funcio es: O(n^2).
     * @param b no apunta a una referencia nul-la. B te les mateixes dimensions que la matriu implicita.
     * @return Retorna una matriu anomenada C com a resultat de la suma de la matriu implicita (A) amb la matriu B.
     * C = A + B.
     */
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

    /**
     * Anomanem la matriu implicita com A i la matriu pasada per referencia com B, tant A com B tenen que tenir
     * les mateixes dimensions. El cost d'executar aquesta funcio es: O(n^2).
     * @param b no apunta a una referencia nul-la. B te les mateixes dimensions que la matriu implicita.
     * @return Retorna una matriu anomenada C com a resultat de la resta de la matriu implicita (A) amb la matriu B.
     * C = A - B.
     */
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

    /**
     * Anomanem la matriu implicita com A i la matriu pasada per referencia com B. El nombre de files de la matriu A
     * te que ser igual al nombre de columnes de la matriu B. El cost d'executar aquesta funcio es: O(n^3).
     * @param b no apunta a una referencia nul-la. B te el mateix nombre de columnes que el nombre de files de la
     *          matriu A.
     * @return Retorna una matriu anomenada C com a resultat de la multiplicacio de la matriu implicita (A) amb la
     * matriu B. C = A * B.
     */
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

    /**
     * Retorna una matriu M amb dimensions: nombre columnes x nombre de files amb la transposda de la matriu implicita.
     * El cost d'executar aquesta funcio es: O(n^2).
     * @return Retorna una matriu M amb la transposda de la matriu implicita.
     */
    public Matriu transposar() {
        Matriu m = new Matriu(this.nColumnes, this.nFiles);
        for (int i = 0; i < this.nColumnes; ++i) {
            for (int j = 0; j < this.nFiles; ++j) {
                m.set_valor(i,j, this.get_valor(j,i));
            }
        }

        return m;
    }

    /**
     * Retorna una matriu M amb dimensions: nombre de files x nombre de columnes amb la normalitzacio per files de la
     * matriu implicita. El cost d'executar aquesta funcio es: O(n^2).
     * @return Retorna una matriu M amb la normalitzacio per files de la matriu implicita.
     */
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

    /**
     * Retorna una matriu M amb dimensions: nombre de files x nombre de columnes amb la normalitzacio per columnes de la
     * matriu implicita. El cost d'executar aquesta funcio es: O(n^2).
     * @return Retorna una matriu M amb la normalitzacio per columnes de la matriu implicita.
     */
    public Matriu normalitzar_columna() {
        Matriu m = new Matriu(this.nFiles, this.nColumnes);
        Matriu mColumnes = new Matriu(this.nFiles, this.nColumnes);
        for (Integer fila : this.data.keySet()) {
            HashMap<Integer, Double> columnes = this.data.get(fila);
            for (Integer columna : columnes.keySet()) {
                mColumnes.set_valor(columna, fila, columnes.get(columna));
            }
        }

        for (Integer columna : mColumnes.data.keySet()) {
            double norma = 0.0;
            HashMap<Integer, Double> files = mColumnes.data.get(columna);
            for (Integer fila : files.keySet()) {
                norma += Math.pow(files.get(fila), 2);
            }

            if (norma != 0.0) {
                norma = Math.sqrt(norma);

                for (Integer fila : files.keySet()) {
                    m.set_valor(fila, columna, (files.get(fila)/norma));
                }
            }
        }
        return m;
    }

    /**
     * Retorna una referencia a la estructura de dades que utilitza la matriu esparsa estructurada per files. El
     * cost d'executar aquesta funcio es: O(1)
     * @return Retorna una referencia a la estructura de dades que utilitza la matriu esparsa estructurada per files.
     */
    public HashMap<Integer, HashMap<Integer, Double>> get_hashmap() {
        return this.data;
    }



    /**
     * Retorna una matriu M que es una copia de la matriu implicita. El cost d'executar aquesta funcio es: O(n^2)
     * @return Retorna una matriu M que es una copia de la matriu implicita.
     */
    public Matriu copia_profunditat() {
        Matriu copia = new Matriu(this.nFiles, this.nColumnes);
        copia.data = (HashMap<Integer, HashMap<Integer, Double>>) this.data.clone();
        return copia;
    }
}
