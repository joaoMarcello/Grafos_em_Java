/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadegrafo;

import java.io.IOException;
import java.util.Scanner;


/**
 *  Classe que possui a main.
 * 
 * @author João Marcello
 */
public class AtividadeGrafo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Lendo as informacoes necessarias para gerar o grafo
        System.out.println("Informe o caminho do documento de texto que sera usado para gerar o grafo:");
        String docPath = input.nextLine();
        
        System.out.println("O grafo eh orientado? [s/n]:");
        String str = input.nextLine().toLowerCase();
        boolean oriented = str.equals("s");
        
        System.out.println("O grafo eh ponderado? [s/n]:");
        str = input.nextLine().toLowerCase();
        boolean weighted = str.equals("s");
        
        
        Grafo g;
        
        try{
            // tenta instanciar um objeto do tipo Grafo com as informacoes dadas pelo usuario
            g = new Grafo(docPath, oriented, weighted);
        }
        catch(IOException ex){
            System.err.println("Houve um problema ao abrir o arquivo ou na geracao do grafo.\nEncerrando");
            return;
        }
        
        System.out.println("Grafo carregado com sucesso.");
        
        int option = -1;
        Vertice v, u;
        String s1, s2;
        
        while(option != 7){
            // mostra o menu principal
            System.out.println("+------------------------------------------------+");
            System.out.println("|                   MENU                         |");
            System.out.println("+------------------------------------------------+");
            System.out.println("|     0- Mostrar lista de adjacencias            |");
            System.out.println("|     1- Informar a quantidade de ciclos         |");
            System.out.println("|     2- Realizar busca em largura               |");
            System.out.println("|     3- Realizar busca em profundidade          |");
            System.out.println("|     4- Mostrar matriz de distancias            |");
            System.out.println("|     5- Mostrar caminho mais curto entre        |");
            System.out.println("|        dois vertices                           |");
            System.out.println("|     6- Realizar algoritmo de Dijkstra          |");
            System.out.println("|     7- Sair do programa                        |");
            System.out.println("+------------------------------------------------+");
            
            System.out.println(" Informe a sua opcao:");
            // lendo a opcao do usuario
            option = input.nextInt();
            
            
            switch(option){
                // Mostrar o grafo
                case 0:
                    g.print();
                    break;
                
                // Informar a quantidade de ciclos
                case 1:
                    // contando a quantidade de ciclos do grafo
                    int ciclos = g.quantCiclos();
                    
                    if(ciclos == 0)  // se o grafo nao possui ciclos...
                        System.out.println("Grafo aciclico.\n");
                    else
                        System.out.println("A quantidade de ciclos do grafo eh " + ciclos + ".\n");
                    break;
                 
                // Realizar a busca em largura
                case 2:
                    System.out.println(" Informe o vertice inicial:");
                    s1 = input.next();
                    
                    // buscando no grafo o vertice informado
                    v = g.getNode(s1);
                    
                    if(v == null)  // se o vertice informado nao existe no grafo...
                        System.out.println("O vertice " + s1 + " nao faz parte do grafo!\n");
                    else{
                        // aplicando a busca em largura no grafo
                        g.BFS(v);
                        System.out.println("    RESULTADO DA BUSCA EM LARGURA   ");
                        // imprimindo o resultado
                        g.printBfsResult();
                    }
                    break;
                    
                // Realizar a busca em profundidade
                case 3:
                    System.out.println("Informe o vertice inicial:");
                    s1 = input.next();
                    
                    // buscando no grafo o vertice informado
                    v = g.getNode(s1);
                            
                    if(v == null)  // se o vertice informado nao existe no grafo...
                        System.out.println("O vertice " + s1 + " nao faz parte do grafo!\n");
                    else{
                        // aplicando a busca em profundidade no grafo
                        g.DFS(v);
                        System.out.println("    RESULTADO DA BUSCA EM PROFUNDIDADE   ");
                        // imprimindo o resultado
                        g.printDfsResult();
                    }
                    break;
                    
                // Mostrar matriz de distancias
                case 4:
                    System.out.println("         MATRIZ DE DISTANCIAS         ");
                    // gerando e imprimindo a matriz de distancia
                    g.printMatrizDeDistancias( g.getMatrizDeDistancias() );
                    break;
                    
                // Mostrar o caminho mais curto entre dois vertices
                case 5:
                    System.out.println(" Informe o vertice de partida:");
                    s1 = input.next();
                    
                    // buscando no grafo o vertice informado
                    v = g.getNode(s1);
                    
                    if(v == null){  // se o vertice informado nao existe no grafo...
                        System.out.println("O vertice " + s1 + " nao pertence ao grafo.");
                        break;
                    }
                    
                    System.out.println(" Informe o vertice de chegada:");
                    s2 = input.next();
                    
                    // buscando no grafo o segundo vertice informado
                    u = g.getNode(s2);
                    
                    if(u == null){  // se o segundo vertice informado nao existe no grafo...
                        System.out.println("O vertice " + s2 + " nao pertence ao grafo.");
                        break;
                    }
                    
                    // aplicando o algoritmo de Dijkstra no grafo
                    g.dijkstra(v);
                    
                    System.out.println(" O caminho mais curto de " + v.value + " ate " + u.value + " eh:" );
                    // imprimindo o menor caminho entre os vertices v e u
                    g.printPath(v, u);
                    System.out.println();
                    break;
                
                // Mostrar o resultado do algoritmo de Dijkstra
                case 6:
                    System.out.println(" Informe o vertice de partida:");
                    s1 = input.next();
                    
                    // buscando no grafo o vertice informado
                    v = g.getNode(s1);
                    
                    if(v == null){  // se o vertice informado nao existe no grafo...
                        System.out.println("O vertice " + s1 + " nao pertence ao grafo.");
                        break;
                    }
                    
                    // aplicando o algoritmo de Dijkstra no grafo
                    g.dijkstra(v);
                    System.out.println("      RESULTADO DO ALGORITMO DE DIJKSTRA     ");
                    // mostra o resultado do algoritmo de Dijkstra
                    g.printDijkstraResult();
                    System.out.println();
                    break;
                    
                // Sair do programa
                case 7:
                    break;
                    
                // Opcao invalida
                default:
                    System.out.println("Opcao invalida");
            }
        }


        System.out.println("Programa finalizado.");
        
    }
    
}
