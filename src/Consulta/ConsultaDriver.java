package Consulta;

import Node.Node;
import Resultat.Resultat;

import java.util.Scanner;

/**
 *
 * @author adri
 */
public class ConsultaDriver {
    public static void main(String[] argv) {
        System.out.println("TEST CLASE CONSULTA");
        System.out.println("Creem una consulta nova:");
        System.out.println("Insereix el camp de la descripcio:");
        Scanner capt = new Scanner(System.in);
        String s=capt.next();
        System.out.println("Insereix el camp graudeRellevancia del resultat(per simplificar)");
        Resultat r=new Resultat();
        s=capt.next();
        r.afegir_tuple(new Node(12, "example", Node.TipusNode.AUTOR), Double.parseDouble(s));
        Consulta test = new Consulta(s , r);
        int menu=99;
        while(menu!=0){
            System.out.println("Selecciona una opció:");
            System.out.println("1:Cambiar atribut descripcio.");
            System.out.println("2:Cambiar resultat nou, introduim graudeRellevancia.");
            System.out.println("3:Consultar descripcio.");
            System.out.println("4:Consultar grau de rellevancia de resultat.");
            System.out.println("0:Sortir");
            s=capt.next();
            menu=Integer.parseInt(s);
            switch(menu){
                case 1:
                    System.out.println("Insereix el camp de la descripcio:");
                    s=capt.next();
                    test.setDescripcio(s);
                    break;
                case 2:
                    System.out.println("Insereix el camp graudeRellevancia del resultat(per simplificar)");
                    s=capt.next();
                    Resultat rs=new Resultat();
                    rs.afegir_tuple(new Node(12, "example", Node.TipusNode.AUTOR), Double.parseDouble(s));
                    test.setResultado(rs);
                    break;
                case 3:
                    System.out.println("La descripcio es "+test.getDescripcio());
                    break;
                case 4:
                    System.out.println("El grau de rellevancia es "+test.getResultat().get_tuple(0).get_grau_rellevancia());
                    break;

            }
        }
        capt.close();
    }
}