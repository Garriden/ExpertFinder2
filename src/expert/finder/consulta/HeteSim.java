package expert.finder.consulta;
import expert.finder.cami.Cami;
import expert.finder.graf.Graf;
import expert.finder.node.Node;
import expert.finder.utils.Matriu;

import java.util.ArrayList;

public class HeteSim {
    //////PÚBLIQUES///////////
    //////PÚBLIQUES///////////
    public HeteSim(){
    }

    //Pre : Node e1 es del tipus primer del camí i e2 es del tipus últim del camí.
    //Post : retorna el coeficient de relació entre els nodes e1, e2
    public double grau_relacio(Node e1, Node e2, Cami cami, Graf g){
        String s = cami.get_cami();
        s = tractament_cami(s);
        Matriu m1; //matriu esquerra
        Matriu m2; //matriu dreta
        char c1 = s.charAt(0); //Node 0
        char c2 = s.charAt(1); //Node 1
        if(c2 == 'E'){
            ArrayList<Matriu> vec = new ArrayList<Matriu>();
            c2 =s.charAt(2);
            vec = adjecencia_E(g,c1,c2);
            m1 = vec.get(0).copia_profunditat().get_fila_iessima(e1.get_id());
            m2 = vec.get(1).copia_profunditat().get_columna_iessima(e2.get_id());
        }
        else{
            m1 = adjecencia(g,c1,c2).copia_profunditat(); //matriu d'adjecencia entre node 1 i 0
            m1 = m1.get_fila_iessima(e1.get_id());
            int mida = s.length();
            c1 = s.charAt(mida-2);
            c2 = s.charAt(mida-1);
            m2 = adjecencia(g,c1,c2).copia_profunditat(); //mat adj de dos ultims nodes
            m2 = m2.get_columna_iessima(e2.get_id());
        }
        probabilitats_fila(m1);
        probabilitats_columna(m2);
        Matriu result;
        result = calculs_hete(g, m1, m2, s);
        double res = result.get_valor(0, 0);
        return res;
    }
    //PRE: El node e es del tipus primer del cami
    //POST: retorna un matriu d'una fila amb els coeficients de relació entre el node e
    //i la resta de nodes del tipus ultim del cami. La posició a la matriu determina el id
    //del node
    public Matriu relacio_node_matriu(Node e, Cami cami, Graf g){
        String s = cami.get_cami();
        s = tractament_cami(s);
        char c1, c2;
        c1 = s.charAt(0);
        c2 = s.charAt(1);
        Matriu m1, m2;
        if(c2 == 'E'){
            ArrayList<Matriu> vec = new ArrayList<Matriu>();
            c2 =s.charAt(2);
            vec = adjecencia_E(g,c1,c2);
            m1 = vec.get(0).copia_profunditat().get_fila_iessima(e.get_id());
            m2 = vec.get(1).copia_profunditat();
        }
        else{
            m1 = adjecencia(g,c1,c2).copia_profunditat();
            int id = e.get_id();
            m1 = m1.get_fila_iessima(id);
            int mida = s.length();
            c1 = s.charAt(mida-2);
            c2 = s.charAt(mida-1);
            m2 = adjecencia(g,c1,c2).copia_profunditat();

        }
        probabilitats_fila(m1);
        probabilitats_columna(m2);
        //prepares la matriu dreta
        return calculs_hete(g,m1,m2,s);
    }



    //Pre : Cami es un camí vàlid de longitud >= 2
    //Post: retorna la matriu de clausura del graf g per al Cami cami.
    public Matriu matriu_clausura(Cami cami, Graf g){
        String s= cami.get_cami();
        s = tractament_cami(s);
        char c1,c2,c3,c4;
        int size = s.length();
        c1 = s.charAt(0);
        c2 = s.charAt(1);
        c3 = s.charAt(size-2);
        c4 = s.charAt(size-1);
        Matriu m1,m2;
        if(c2 == 'E'){
            ArrayList<Matriu> vec = new ArrayList<Matriu>();
            vec = adjecencia_E(g,c1,c4);
            m1 = vec.get(0).copia_profunditat();
            m2 = vec.get(1).copia_profunditat();
        }
        else{
            m1 = adjecencia(g,c1,c2).copia_profunditat();
            m2 = adjecencia(g,c3,c4).copia_profunditat();
        }

        probabilitats_fila(m1);
        probabilitats_columna(m2);

        Matriu res;
        res = calculs_hete(g,m1,m2,s);
        return res;
    }




    ///////////PRIVADES////////////////

    //donats les matrius/vectors dels extrems i donat el graf, els vectors i el cami fagi tots els calculs retorna matriu resultant de fer hete sim
    private Matriu calculs_hete(Graf g, Matriu m1, Matriu m2, String s){

        int longitud = s.length();
        int mitad = (longitud-3) / 2;
        int primer = 1; int ultim = longitud - 2;
        char c1, c2;
        for(int i = 0; i < mitad; ++i){
            ArrayList<Matriu> vec = new ArrayList<Matriu>();
            //Part dreta
            c1 = s.charAt(primer+i);
            c2 = s.charAt(primer+i+1);
            Matriu aux;
            if(c2 == 'E'){
                c2 = s.charAt(primer+i+2);
                vec = adjecencia_E(g,c1,c2);
                aux = vec.get(0).copia_profunditat();
            }
            else aux =  adjecencia(g,c1,c2).copia_profunditat();
            probabilitats_fila(aux);
            m1 = m1.multiplicar(aux);
            //Part esquerra
            c1 = s.charAt(ultim-i-1);
            c2 = s.charAt(ultim-i); //agafem seguents 2 per l'esq
            if(c1 == 'E'){
                aux = vec.get(1).copia_profunditat();
            }
            else aux = adjecencia(g,c1,c2).copia_profunditat();
            probabilitats_columna(aux);
            m2 = aux.multiplicar(m2);
        }
        m1 = m1.normalitzar_fila();
        m2 = m2.normalitzar_columna();

        m1 = m1.multiplicar(m2);
        return m1;
    }


    //Pre : Si esq es true retorna la matriu d'adjecencia on les files corresponen a c1
    //i les columnes a E. En cas contrari retorna la matriu d'adjecencia on les files
    //corresponen a E i les columnes a c2.
    private ArrayList<Matriu> adjecencia_E(Graf g, char c1,char c2){
        Matriu m = adjecencia(g,c1,c2);
        Matriu m1 = new Matriu(m.get_nombre_files(),1);
        Matriu m2 = new Matriu(1,m.get_nombre_columnes());
        boolean primer = true;
        for(int i = 0; i < m.get_nombre_files(); ++i){
            for(int j = 0; j < m.get_nombre_columnes(); ++j){
                double d = 1;
                if(m.get_valor(i, j) == d){
                    if(primer){
                        m1.set_valor(i, 0, 1);
                        m2.set_valor(0, j, 1);
                        primer = false;
                    }
                    else{
                        m1.afegir_columna();
                        m1.set_valor(i, m1.get_nombre_columnes()-1, 1);
                        m2.afegir_fila();
                        m2.set_valor(m2.get_nombre_files()-1, j, 1);
                    }
                }
            }
        }
        ArrayList<Matriu> vec = new ArrayList<Matriu>();

        vec.add(m1);
        vec.add(m2);
        return vec;
    }
    //Given a path(in string format), inserts E and creates both E matrices if needed
    private String tractament_cami(String c){
        if(c.length()%2 == 0){
            int meitat = c.length()/2;
            c = c.substring(0, meitat ) + "E" + c.substring(meitat, c.length());
            return c;
        }
        return c;


    }

    //Pre : c1 i c2 s�n del tipus p,a,c o t
    //retorna matriu d'adj corresponent a la parella de nodes especificada per c1 i c2
    private Matriu adjecencia(Graf g, char c1, char c2){
        Matriu m;
        if(c1 == 'P'){
            if(c2 == 'A') m = g.get_paper_autor();
            else if(c2 == 'T') m = g.get_paper_terme();
            else m = g.get_paper_conferencia();
        }else{
            if(c1 == 'A') m = g.get_paper_autor();
            else if(c1 == 'T') m = g.get_paper_terme();
            else m = g.get_paper_conferencia();
            m = m.transposar();
        }
        return m;
    }


    private void probabilitats_fila(Matriu m){
        for(int i = 0; i < m.get_nombre_files();++i){
            double suma = 0;
            for(int j = 0; j < m.get_nombre_columnes();++j){
                if(m.get_valor(i, j)== 1) ++suma;
            }
            for(int j = 0; j < m.get_nombre_columnes();++j){
                if(m.get_valor(i, j)== 1){
                    double d = 1/suma;
                    m.set_valor(i, j,d);
                }
            }
        }

    }



    private void probabilitats_columna(Matriu m){
        for(int j = 0; j < m.get_nombre_columnes();++j){
            double suma = 0;
            for(int i = 0; i < m.get_nombre_files();++i){
                if(m.get_valor(i, j)==1) ++suma;
            }
            for(int i = 0; i < m.get_nombre_files();++i){
                if(m.get_valor(i, j)==1){
                    double d = 1/suma;
                    m.set_valor(i, j, d);
                }
            }
        }

    }


    private void imprimir_matriu(Matriu m){
        System.out.println("imprimeixo matriu:\n");
        for(int i = 0; i < m.get_nombre_files();++i){
            for(int j = 0; j < m.get_nombre_columnes();++j){
                System.out.print(m.get_valor(i, j));
                System.out.print(" ");

            }
            System.out.println("\n");
        }

    }

}