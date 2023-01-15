/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadegrafo;

/**
 *   Estrutura de dados Fila. Guarda um conjunto de Object. O primeiro Object a ser inserido eh o primeiro a ser removido.
 * 
 * @author JM
 */
public class Fila {
    // classe interna que representa um noh da fila
    private class Node{
        // --------------- ATRIBUTOS ----------------
        // ponteiro para o proximo elemento da fila
        Node next;
        // dados
        Object obj;
       
        // ------------- CONSTRUTOR -------------
        // Recebe:
        //      * obj: o objeto que queremos colocar na fila
        Node(Object obj){
            next = null;
            this.obj = obj;
        }
    }
    
    // ------------------- ATRIBUTOS ------------------------
    // primeiro elemento da fila
    Node first;
    // ultimo elemento da fila
    Node last;
    
    
    // ------------------ CONSTRUTOR -----------------------
    public Fila(){
        first = null;
        last = null;
    }
    
    
    // ------------------- METODOS ------------------------
    
    // Adiciona um elemento na fila.
    // Recebe:
    //      * obj: o objeto que queremos adicionar
    public void enqueue(Object obj){
        // se a fila estiver vazia...
        if(first == null){
            // cria um novo noh que sera o primeiro  e ultimo elemento da fila
            first = new Node(obj);
            last = first;
        }
        else{
            // cria um novo noh com o objeto
            Node newNode = new Node(obj);
            // adiciona o novo noh no fnal da fila
            last.next = newNode;
            // faz o ultimo da fila ser o novo noh
            last = newNode;
        }
    }
    
    
    // Remove o primeiro elemento da fila.
    // Retorna:
    //      - o primeiro elemento da fila. Caso a fila esteja vazia, retorna null
    public Object dequeue(){
        // se a fila estiver vazia...
        if(first == null)
            return null;
        else{
            // uma variavel auxiliar guarda o primeiro elemento (eh o que vai ser removido)
            Node aux = first;
            // faz o novo primeiro elemento da fila ser o proximo elemento da fila
            first = first.next;
            // retorna o antigo primeiro elemento da fila
            return aux.obj;
        }
    }
    
    
    // Diz se a fila esta vazia.
    // Retorna:
    //      - true se a fila estiver vazia ou false caso contrario.
    public boolean isEmpty(){
        return first == null;
    }
    
}
