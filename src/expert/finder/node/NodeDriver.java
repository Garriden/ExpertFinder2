package expert.finder.node;

import java.util.Scanner;

// Created by Marc Garrido  17/04/2016

public class NodeDriver {

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

    public static void main(String[] args)
    {
        String nom;
        int id;
        Node n = new Node(0,null,null);

        System.out.println("Es crea un node de prova (id=0, String=null, TipusNode=null)");
        System.out.println();
        System.out.println("Tria una opció:");
        System.out.println("0) Sortir:");
        System.out.println("1) Crear node:");
        System.out.println("2) Get_id:");
        System.out.println("3) Get_nom:");
        System.out.println("4) Get_tipus_node:");
        System.out.println("5) Set_nom:");
        System.out.println("6) Set_id:");
        System.out.println("Els 'sets' i 'gets' sempre es fan a partir de l'últim node creat");

        Scanner in = new Scanner(System.in);
        int i = in.nextInt();



        while (i != 0) {
            switch(i){
                case 1:
                    System.out.println("Escriu l'Id del node:   ex.8");
                    id = Integer.parseInt(in.next());

                    System.out.println("Escriu el nom del node:   ex.Marc");
                    nom = in.next();

                    System.out.println("Escriu el tipus del node:  ha de ser AUTOR, CONFERENCIA, TERME o PAPER");
                    String tipusNode = in.next();

                    Node.TipusNode tipus = string_to_node(tipusNode);
                    if (tipus != null) {
                        n = new Node(id,nom,tipus);
                        System.out.println("El node ha estat creat correctament");
                        break;
                    }
                    else System.out.println("El tipus del node no es correcte");
                    break;
                case 2:
                    System.out.println("Id -> "+n.get_id());
                    break;

                case 3:
                    System.out.println("Nom -> "+n.get_nom());
                    break;

                case 4:
                    System.out.println("TipusNode -> "+n.get_tipus_node());
                    break;
                case 5:
                    System.out.println("Escriu el nom del node:   ex.Edgar");
                    nom = in.next();
                    n.set_nom(nom);
                    break;
                case 6:
                    System.out.println("Escriu l'Id del node:   ex.6");
                    id = Integer.parseInt(in.next());
                    n.set_id(id);
                    break;
                default:
                    break;
            }

            System.out.println();
            System.out.println("Tria una opció:");
            System.out.println("0) Sortir:");
            System.out.println("1) Crear node:");
            System.out.println("2) Get_id:");
            System.out.println("3) Get_nom:");
            System.out.println("4) Get_tipus_node:");
            System.out.println("5) Set_nom:");
            System.out.println("6) Set_id:");
            System.out.println("Els 'sets' i 'gets' sempre es fan a partir de l'últim node creat");

            i = in.nextInt();

        }


    }

}