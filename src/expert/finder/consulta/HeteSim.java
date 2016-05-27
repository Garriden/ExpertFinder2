package expert.finder.consulta;
import expert.finder.cami.Cami;
import expert.finder.graf.Graf;
import expert.finder.node.Node;
import expert.finder.utils.Matriu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HeteSim {
	//////PÚBLIQUES///////////
	public HeteSim(){
	}
	
	//Pre : Node e1 es del tipus primer del camí i e2 es del tipus últim del camí.
	//Post : retorna el coeficient de relació entre els nodes e1, e2
	public double grau_relacio(Node e1, Node e2, Cami cami,  Graf g){
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
			m1 = vec.get(0).get_fila_iessima(e1.get_id());
			m2 = vec.get(1).get_columna_iessima(e2.get_id());
		}
		else{
			m1 = adjecencia(g,c1,c2); //matriu d'adjecencia entre node 1 i 0
			m1 = m1.get_fila_iessima(e1.get_id());
			int mida = s.length();
			c1 = s.charAt(mida-2);
			c2 = s.charAt(mida-1);
			m2 = adjecencia(g,c1,c2); //mat adj de dos ultims nodes
			m2 = m2.get_columna_iessima(e2.get_id());
		}
		m1 = probabilitats_fila(m1);
		m2 = probabilitats_columna(m2);
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
			m1 = vec.get(0).get_fila_iessima(e.get_id());
			m2 = vec.get(1);
		}
		else{
			m1 = adjecencia(g,c1,c2);
			int id = e.get_id();
			m1 = m1.get_fila_iessima(id);
			int mida = s.length();
			c1 = s.charAt(mida-2);
			c2 = s.charAt(mida-1);
			m2 = adjecencia(g,c1,c2);
			
		}
		m1 = probabilitats_fila(m1);
		m2 = probabilitats_columna(m2);
		//prepares la matriu dreta
		return calculs_hete(g,m1,m2,s);
	}
	
	
	
	//Pre : Cami es un camí vàlid de longitud >= 2
	//Post: retorna la matriu de clausura del graf g per al Cami cami.
	public Matriu matriu_clausura(Cami cami, Graf g){
		System.out.println("preparo les matrius");
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
			System.out.println("calculo adjecencia amb E");
			vec = adjecencia_E(g,c1,c4);
			m1 = vec.get(0);
			m2 = vec.get(1);
		}
		else{
			m1 = adjecencia(g,c1,c2);
			m2 = adjecencia(g,c3,c4);
		}
		System.out.println("probabiltats esquerra");
		m1 = probabilitats_fila(m1);
		System.out.println("probabilitats dreta");
		m2 = probabilitats_columna(m2);
		Matriu res;
		System.out.println("calculs");
		res = calculs_hete(g,m1,m2,s);
		return res;
	}
	///////////PRIVADES////////////////
	
	//donats les matrius/vectors dels extrems i donat el graf, els vectors i el cami fagi tots els calculs retorna matriu resultant de fer hete sim
	private Matriu calculs_hete(Graf g, Matriu m1, Matriu m2, String s){
		int longitud = s.length(); 
		int mitad = (longitud-3) / 2;
		int primer = 1; 
		int ultim = longitud - 2;
		char c1, c2; 
		for(int i = 0; i < mitad; ++i){
			ArrayList<Matriu> vec = new ArrayList<Matriu>();
			//Part dreta
			c1 = s.charAt(primer+i);
			c2 = s.charAt(primer+i+1); 
			Matriu aux;
			if(c2 == 'E'){
				 c2 = s.charAt(primer+i+2);
				 System.out.println("adjecencia_E");
				 vec = adjecencia_E(g,c1,c2);
				 aux = vec.get(0);
			}
			else aux =  adjecencia(g,c1,c2);
			System.out.println("probabilitats_fila");
		    aux = probabilitats_fila(aux);
		    System.out.println("multiplicar");
			m1 = m1.multiplicar(aux);
			//Part esquerra
			c1 = s.charAt(ultim-i-1);
			c2 = s.charAt(ultim-i); 
			if(c1 == 'E'){
				aux = vec.get(1);
			}
			else aux = adjecencia(g,c1,c2);
			System.out.println("probabilitats_columna");
			aux = probabilitats_columna(aux);
			System.out.println("multiplicar");
			m2 = aux.multiplicar(m2);
			System.out.println("normalitzar");
			m1 = m1.normalitzar_fila();
			m2 = m2.normalitzar_columna();
		}
		System.out.println("normalitzant");
		m1 = m1.normalitzar_fila();
		m2 = m2.normalitzar_columna();
		System.out.println("multiplicant");
		m1 = m1.multiplicar(m2);
		return m1;
}
	
	
	//Pre : Si esq es true retorna la matriu d'adjecencia on les files corresponen a c1
	//i les columnes a E. En cas contrari retorna la matriu d'adjecencia on les files 
	//corresponen a E i les columnes a c2.
	
	private ArrayList<Matriu> adjecencia_E(Graf g, char c1,char c2){
		Matriu m = adjecencia(g,c1,c2);
		HashMap <Integer, HashMap<Integer,Double>> map_matriu = m.get_hashmap();
		Matriu res_esq = new Matriu(m.get_nombre_files(),1);
		Matriu res_dre = new Matriu(1,m.get_nombre_columnes());
		Iterator<Integer> iter = map_matriu.keySet().iterator();
		int cont = 0;
		while(iter.hasNext()){
			int fila = iter.next();
			HashMap<Integer,Double> map_fila = map_matriu.get(fila);
			Iterator <Integer> iter2 = map_fila.keySet().iterator();
			while(iter2.hasNext()){
				int columna = iter2.next();
				if(cont == 0){
					res_esq.set_valor(fila, cont, 1);
					res_dre.set_valor(cont, columna, 1);
				}
				else{
					res_esq.afegir_columna();
					res_esq.set_valor(fila, cont, 1);
					res_dre.afegir_fila();
					res_dre.set_valor(cont, columna, 1);
					
				}
				++cont;
			}
			
		}
		ArrayList <Matriu> res = new ArrayList<Matriu>();
		res.add(res_esq);
		res.add(res_dre);
		return res;
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
	
	//Pre : c1 i c2 son del tipus p,a,c o t
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
	
	private Matriu probabilitats_fila(Matriu m){
		Matriu res = new Matriu(m.get_nombre_files(),m.get_nombre_columnes());
		HashMap <Integer, HashMap<Integer,Double>> map_matriu = m.get_hashmap();
		Iterator <Integer> iter =map_matriu.keySet().iterator();
		while(iter.hasNext()){	
			int fila = iter.next();
			HashMap<Integer,Double> map_fila = map_matriu.get(fila);
			double x = map_fila.size();
			x = 1/x;
			Iterator<Integer> iter2 = map_fila.keySet().iterator();
			while(iter2.hasNext()){
				int columna = iter2.next();
				res.set_valor(fila, columna, x);
			}
		}
		return res;
	}
		
	private Matriu probabilitats_columna(Matriu m){
		Matriu res = new Matriu(m.get_nombre_files(),m.get_nombre_columnes());
		Matriu cont_columna = new Matriu(m.get_nombre_files(),1);
		HashMap <Integer, HashMap<Integer,Double>> map_matriu = m.get_hashmap();
		Iterator <Integer> iter =map_matriu.keySet().iterator();
		while (iter.hasNext()){
			int fila = iter.next();
			HashMap<Integer,Double> map_fila = map_matriu.get(fila);
			Iterator<Integer> iter2 = map_fila.keySet().iterator();
			while(iter2.hasNext()){
				int columna = iter2.next();
				double cont = cont_columna.get_valor(fila, columna);
				cont_columna.set_valor(fila,columna,cont+1);
			}
		}
		iter =map_matriu.keySet().iterator();
		while(iter.hasNext()){
			int fila = iter.next();
			HashMap<Integer,Double> map_fila = map_matriu.get(fila);
			Iterator<Integer> iter2 = map_fila.keySet().iterator();
			while(iter2.hasNext()){
				int columna = iter2.next();
				double cont = cont_columna.get_valor(fila, columna);
				double valor = m.get_valor(fila, columna);
				valor = valor/cont;
				res.set_valor(fila, columna, valor);
			}
		}
		return res;
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