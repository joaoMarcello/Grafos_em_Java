/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadegrafo;

/**
 *      Representa uma aresta que conecta dois vertices.
 * 
 * @author João Marcello
 */
public class Aresta {
    // -------------- ATRIBUTOS ---------------------
    
    // o vertice ate onde vai a aresta
    Vertice v;
    // peso da aresta
    float weigth;
    // tipo da aresta (pode ser "arv", "ret", "avan" ou "cruz"). Eh alterado quando se realiza um busca em profundidade
    String type = "arv";

    
    // -------------- CONSTRUTOR 1 ---------------------
    // Recebe:
    //      * v: o vertice ate onde vai a aresta
    //      * w: o peso da aresta
    public Aresta(Vertice v, float w){
        this.v = v;
        weigth = w;
    }

    // -------------- CONSTRUTOR 2 ---------------------
    // Recebe:
    //      * v: o vertice ate onde vai a aresta
    public Aresta(Vertice v){
        this.v = v;
        weigth = 0;
    }
}
