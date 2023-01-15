/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadegrafo;

/**
 *  Pilha
 * @author JM
 */
public class Pilha {
    // classe interna que representa um noh da pilha
    private class Node{
        // --------------- ATRIBUTOS ----------------
        // ponteiro para predecessor do noh
        Node prev;
        // dados
        Object obj;
        
        // ------------- CONSTRUTOR -------------
        // Recebe:
        //      * obj: o objeto que queremos colocar na pilha
        Node(Object obj){
            prev = null;
            this.obj = obj;
        }
    }
    
    
    // ------------------- ATRIBUTOS ------------------------
    // ultimo elemento da pilha
    Node last;
    
    
    // -------------------- CONSTRUTOR ----------------------
    public Pilha(){
        last = null;
    }
    
    
    // ------------------- METODOS ------------------------
    
    // Adiciona um elemento na pilha.
    // Recebe:
    //      * obj: o objeto que queremos adicionar
    public void push(Object obj){
        // se a pilha estiver vazia...
        if(last == null){
            last = new Node(obj);
        }
        else{
            Node newNode = new Node(obj);
            newNode.prev = last;
            last = newNode;
        }
    }
    
    
    // Remove o ultimo elemento adicionado na pilha.
    // Retorna:
    //      - o ultimo elemento da pilha. Caso a pílha esteja vazia, retorna null
    public Object pop(){
        if(last == null)
            return null;
        else{
            Node aux = last;
            last = last.prev;
            return aux.obj;
        }
    }
    
    
    // Diz se a fila esta vazia.
    // Retorna:
    //      - true se a pliha estiver vazia ou false caso contrario.
    public boolean isEmpty(){
        return last == null;
    }
}
