import java.util.ArrayList;
import java.util.Scanner;

// Created by Marc Garrido Casas on 22/04/2016.

public class GrafDriver {
    public static void print_matriu(Matriu m) {
        for (int i = 0; i < m.get_nombre_files(); i++) {
            for (int j = 0; j < m.get_nombre_columnes(); j++) {
                System.out.printf("%.2f\t", m.get_valor(i,j));
            }
            System.out.println();
        }
    }

    public static int quin_tipus(String s) {
        if (s.equals("PAPER")) {
            return 1;
        }
        else if (s.equals("CONFERENCIA")) {
            return 2;
        }
        else if (s.equals("AUTOR")) {
            return 3;
        }
        else if (s.equals("TERME")) {
            return 4;
        }
        else return -1;
    }

    public static Node.TipusNode string_to_node(String tipusNode) {
        switch(tipusNode) {
            case "AUTOR":
                return Node.TipusNode.AUTOR;
            case "TERME":
                return Node.TipusNode.TERME;
            case "CONFERENCIA":
                return Node.TipusNode.CONFERENCIA;
            case "PAPER":
                return Node.TipusNode.PAPER;
        }
        return null;
    }

    public static void main(String[] argv) {
        {

            Node n, n1, n2;
            String tipusNode;
            Node.TipusNode tipus;
            int id, idO, idD, tipusInt, res;
            int tamany = 0;

            System.out.println("Tria una opció:");
            System.out.println("0) Sortir:");
            System.out.println("1) Afegir node:");
            System.out.println("2) Eliminar node:");
            System.out.println("3) Actualizar node:");
            System.out.println("4) Afegir aresta:");
            System.out.println("5) Eliminar aresta:");
            System.out.println("6) Printar ArrayList paper:");
            System.out.println("7) Printar ArrayList conferencia");
            System.out.println("8) Printar ArrayList autor");
            System.out.println("9) Printar ArrayList terme");
            System.out.println("10) Printar Matriu paper_autor:");
            System.out.println("11) Printar Matriu paper_conferencia:");
            System.out.println("12) Printar Matriu paper_terme:");
            System.out.println("13) Obtenir node:");

            //Inicialitzar matrius i Arrays

            Matriu paperAutor = new Matriu(1, 1);
            Matriu paperConferencia = new Matriu(1, 1);
            Matriu paperTerme = new Matriu(1, 1);

            ArrayList<Node> paper = new ArrayList<Node>();
            paper.add(new Node(0, "PAPER_MARC", Node.TipusNode.PAPER));

            ArrayList<Node> conferencia = new ArrayList<Node>();
            conferencia.add(new Node(0, "CONF_MARC", Node.TipusNode.CONFERENCIA));

            ArrayList<Node> autor = new ArrayList<Node>();
            autor.add(new Node(0, "AUTOR_MARC", Node.TipusNode.AUTOR));

            ArrayList<Node> terme = new ArrayList<Node>();
            terme.add(new Node(0, "TERME_MARC", Node.TipusNode.TERME));

            Graf g;
            g = new Graf(paperAutor, paperConferencia, paperTerme, paper,
                    terme, autor, conferencia);


            Scanner in = new Scanner(System.in);
            int i = in.nextInt();

            while (i != 0) {
                switch (i) {
                    case 1:
                        System.out.println("Escriu el nom del node:   ex.Marc");
                        String nom = in.next();

                        System.out.println("Escriu el tipus del node:  ha de ser AUTOR, CONFERENCIA, TERME o PAPER");
                        tipusNode = in.next();
                        tipusInt = quin_tipus(tipusNode);
                        if (tipusInt < 0) {
                            System.out.println("El tipus de node erroni");
                            break;
                        }
                        tipus = string_to_node(tipusNode);

                        res = g.afegir_node(tipus, nom);
                        if (res == 0) System.out.println("node afegit");
                        else if (res == -1) System.out.println("node null");
                        else if (res == -2) System.out.println("node repetit");

                        break;
                    case 2:
                        System.out.println("Escriu el tipus del node:   ex.AUTOR");
                        tipusNode = in.next();
                        tipusInt = quin_tipus(tipusNode);
                        if (tipusInt < 0) {
                            System.out.println("El tipus de node erroni");
                            break;
                        }
                        tipus = string_to_node(tipusNode);

                        if (tipusInt == 1) tamany = paper.size();
                        else if (tipusInt == 2) tamany = conferencia.size();
                        else if (tipusInt == 3) tamany = autor.size();
                        else if (tipusInt == 4) tamany = terme.size();

                        --tamany;
                        System.out.println("Escriu l'Id del node, ha de ser entre 0 i el tamany de l'array: " + tamany);
                        id = Integer.parseInt(in.next());

                        if (id >= 0 && id <= tamany) {
                            n = new Node(id, "", tipus);

                            if ((g.eliminar_node(n)) == 0) {
                                System.out.println("Node eliminat correctament");
                            } else System.out.println("No s'ha eliminat correctament");
                        } else System.out.println("Id erroni");
                        break;

                    case 3:
                        System.out.println("Escriu el tipus del node:   ex.AUTOR");
                        tipusNode = in.next();
                        tipusInt = quin_tipus(tipusNode);
                        if (tipusInt < 0) {
                            System.out.println("El tipus de node erroni");
                            break;
                        }
                        tipus = string_to_node(tipusNode);

                        if (tipusInt == 1) tamany = paper.size();
                        else if (tipusInt == 2) tamany = conferencia.size();
                        else if (tipusInt == 3) tamany = autor.size();
                        else if (tipusInt == 4) tamany = terme.size();

                        --tamany;
                        System.out.println("Escriu l'Id del node, ha de ser entre 0 i el tamany de l'array");
                        id = Integer.parseInt(in.next());

                        System.out.println("Escriu el nom del node:   ex.Marc");
                        String nomN = in.next();

                        if (id >= 0 && id <= tamany) {
                            n = new Node(id, nomN, tipus);

                            if ((g.actualizar_node(n)) == 0) {
                                System.out.println("Node actualitzat correctament");
                            } else System.out.println("No s'ha actualitzat correctament");
                        } else System.out.println("Id erroni");
                        break;

                    case 4:
                        System.out.println("El node origen sempre serà PAPER;");
                        System.out.println("Escriu el tipus del node destí: CONFERENCIA, AUTOR o TERME:");
                        tipusNode = in.next();
                        tipusInt = quin_tipus(tipusNode);
                        if (tipusInt < 0) {
                            System.out.println("El tipus de node erroni");
                            break;
                        }
                        tipus = string_to_node(tipusNode);

                        if (tipusInt == 1) tamany = paper.size();
                        else if (tipusInt == 2) tamany = conferencia.size();
                        else if (tipusInt == 3) tamany = autor.size();
                        else if (tipusInt == 4) tamany = terme.size();

                        --tamany;
                        System.out.println("Escriu l'Id del node ORIGEN, ha de ser entre 0 i el tamany de la matriu PAPER: " + (paper.size() - 1));
                        idO = Integer.parseInt(in.next());

                        System.out.println("Escriu l'Id del node DESTÍ, ha de ser entre 0 i el tamany de la matriu " + tipus + ": " + tamany);
                        idD = Integer.parseInt(in.next());


                        if (idD >= 0 && idD <= tamany && idO >= 0 && idO <= (paper.size() - 1)) {
                            n1 = new Node(idO, "", Node.TipusNode.PAPER);
                            n2 = new Node(idD, "", tipus);

                            if (g.afegir_aresta(n1, n2) == 0) System.out.println("Aresta afegida correctament");
                            else System.out.println("Error en afegir l'aresta");
                        } else System.out.println("Id erroni");
                        break;

                    case 5:
                        System.out.println("El node origen sempre serà PAPER;");
                        System.out.println("Escriu el tipus del node destí: CONFERENCIA, AUTOR o TERME:");
                        tipusNode = in.next();
                        tipusInt = quin_tipus(tipusNode);
                        if (tipusInt < 0) {
                            System.out.println("El tipus de node erroni");
                            break;
                        }
                        tipus = string_to_node(tipusNode);

                        System.out.println("Escriu l'Id del node ORIGEN, ha de ser entre 0 i el tamany de la matriu PAPER: " + (paper.size() - 1));
                        idO = Integer.parseInt(in.next());

                        System.out.println("Escriu l'Id del node DESTÍ, ha de ser entre 0 i el tamany de la matriu " + tipus + ": " + tamany);
                        idD = Integer.parseInt(in.next());

                        if (idD >= 0 && idD <= tamany && idO >= 0 && idO <= (paper.size() - 1)) {
                            n1 = new Node(idO, "", Node.TipusNode.PAPER);
                            n2 = new Node(idD, "", tipus);

                            if (g.eliminar_aresta(n1, n2) == 0) System.out.println("Aresta eliminada correctament");
                            else System.out.println("Error en eliminar l'aresta");
                        } else System.out.println("Id erroni");
                        break;

                    case 6:
                        ArrayList<Node> pap;
                        pap = new ArrayList<Node>();
                        pap = g.get_paper();

                        for (int k = 0; k < pap.size(); ++k) {
                            System.out.println("Id->" + k + " nom->" + pap.get(k).get_nom());
                        }
                        System.out.println();

                        break;
                    case 7:
                        ArrayList<Node> conf;
                        conf = new ArrayList<Node>();
                        conf = g.get_conferencia();

                        for (int k = 0; k < conf.size(); ++k) {
                            System.out.println("Id->" + k + " nom->" + conf.get(k).get_nom());
                        }
                        System.out.println();

                        break;

                    case 8:
                        ArrayList<Node> aut;
                        aut = new ArrayList<Node>();
                        aut = g.get_autor();

                        for (int k = 0; k < aut.size(); ++k) {
                            System.out.println("Id->" + k + " nom->" + aut.get(k).get_nom());
                        }

                        break;

                    case 9:
                        ArrayList<Node> ter;
                        ter = new ArrayList<Node>();
                        ter = g.get_terme();

                        for (int k = 0; k < ter.size(); ++k) {
                            System.out.println("Id->" + k + " nom->" + ter.get(k).get_nom());
                        }

                        break;

                    case 10:
                        print_matriu(paperAutor);
                        break;

                    case 11:
                        print_matriu(paperConferencia);
                        break;

                    case 12:
                        print_matriu(paperTerme);
                        break;

                    case 13:
                        System.out.println("Escriu el tipus del node:   ex.AUTOR");
                        tipusNode = in.next();
                        tipusInt = quin_tipus(tipusNode);
                        if (tipusInt < 0) {
                            System.out.println("El tipus de node erroni");
                            break;
                        }
                        tipus = string_to_node(tipusNode);


                        if (tipusInt == 1) tamany = paper.size();
                        else if (tipusInt == 2) tamany = conferencia.size();
                        else if (tipusInt == 3) tamany = autor.size();
                        else if (tipusInt == 4) tamany = terme.size();

                        --tamany;
                        System.out.println("Escriu l'Id del node, ha de ser entre 0 i el tamany de l'array: " + tamany);
                        id = Integer.parseInt(in.next());


                        if (id >= 0 && id <= tamany) {
                            n = g.get_node(id, tipus);
                            if (n != null)
                                System.out.println("id node ->" + n.get_id() + "  nom node ->" + n.get_nom());
                        } else System.out.println("Id erroni");
                        break;

                    default:
                        break;

                }

                System.out.println("Tria una opció:");
                System.out.println("0) Sortir:");
                System.out.println("1) Afegir node:");
                System.out.println("2) Eliminar node:");
                System.out.println("3) Actualizar node:");
                System.out.println("4) Afegir aresta:");
                System.out.println("5) Eliminar aresta:");
                System.out.println("6) Printar ArrayList paper:");
                System.out.println("7) Printar ArrayList conferencia");
                System.out.println("8) Printar ArrayList autor");
                System.out.println("9) Printar ArrayList terme");
                System.out.println("10) Printar Matriu paper_autor:");
                System.out.println("11) Printar Matriu paper_conferencia:");
                System.out.println("12) Printar Matriu paper_terme:");
                System.out.println("13) Obtenir node:");

                i = in.nextInt();

            }

        }
    }
}
