/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadegrafo;

import java.util.ArrayList;

/**
 *      Representa um vertice do grafo
 * @author João Marcello
 */
public class Vertice {
    // constantes para as cores
    public static int BLACK = 0;
    public static int WHITE = 1;
    public static int GRAY = 2;

    // ----------------- ATRIBUTOS --------------------------
    // valor
    String value;
    // cor
    int color = WHITE;
    // predecessor
    Vertice pred = null;
    // distancia ou tempo de descoberta
    float d = 0;
    // tempo de finalizacao
    int f = 0;
    // conjunto de arestas
    ArrayList<Aresta> list = new ArrayList<Aresta>();

    
    // -------------- CONSTRUTOR ----------------------------
    // Recebe:
    //      * value: o valor d vertice (alfanumerico)
    public Vertice(String value){
        this.value = value;
    }
}
