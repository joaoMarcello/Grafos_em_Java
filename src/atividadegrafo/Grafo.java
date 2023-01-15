/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadegrafo;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *      Representa um grafo que pode ser orientado ou nao orientado e ponderado ou nao ponderado.
 * 
 * @author João Marcello
 */
public class Grafo {
    
    // --------------------- ATRIBUTOS -------------------------------
    // quantidade de vertices no grafo
    private int n;
    // conjunto de vertices do grafo
    Vertice v[];
    // contador de tempo (usado na busca em profundidade)
    private int tempo = 0;
    // quantidade de ciclos do grafo
    private int ciclos = 0;
    // diz se o grafo eh ponderado
    private boolean weigthed;
    
    
    // ---------------------- CONSTRUTOR -----------------------------
    // Recebe:
    //      * docPath: o caminho do arquivo de texto que sera usado para carregar os vertices e arestas do grafo
    //      * oriented: diz se o grafo eh orientado (true) ou nao (false)
    //      * weighted: diz se o grafo eh ponderado (true) ou nao (false)
    public Grafo(String docPath, boolean oriented, boolean weighted) throws IOException, NumberFormatException{
        // leitor de entradas (mas aqui sera usado para abrir o arquivo)
        Scanner input;
        
        this.weigthed = weighted;
        
        try {
            // abrindo o arquivo de texto
            input = new Scanner(Paths.get(docPath));

            // atribuido a primeira linha do arquivo a variavel n que eh a quantidade de vertices do grafo
            n = input.nextInt();
            // criando o vetor de vertices de tamanho n
            v = new Vertice[n];
            
            
            // incializando todos os vertices como null
            for(int i = 0; i < n; i++)
                v[i] = null;

            
            // posicao para adicionar os novos vertices
            int indexAtual = 0;
            
            // enquanto houver dados no arquivo...
            while(input.hasNext()){
                // lendo o primeiro caractere (alfanumerico)
                String ch1 = input.next();
                
                
                // GRAFO NAO ORIENTADO
                if(!oriented){
                    // procura se jah existe esse vertice no grafo e atribui a variavel vr1
                    Vertice vr1 = getNode(ch1);
                    
                    // se nao encontrou esse vertice...
                    if(vr1 == null){
                        // cria o vertice no indiceAtual
                        v[indexAtual] = new Vertice(ch1);
                        // atribui o novo vertice a vr1
                        vr1 = v[indexAtual];
                        // incrementa o indice atual
                        indexAtual++;
                    }
                    
                    // lendo o segundo caractere alfanumerico
                    String ch2 = input.next();
                    // procura se jah existe esse vertice no grafo e atribui a variavel vr2
                    Vertice vr2 = getNode(ch2);

                    // se nao encontrou esse vertice...
                    if(vr2 == null){
                       // cria o vertice e atribui a vr2
                       vr2 = new Vertice(ch2);
                       // adiciona o novo vertice no grafo
                       v[indexAtual] = vr2;
                       // incrementa o indice atual
                       indexAtual++;
                    }
                    
                    
                    float w = 0;
                    // se o grafo eh ponderado
                    if(weighted)
                        try{
                            w = Float.parseFloat(input.next());  // atribuido o peso a variavel w
                        }
                        catch (NumberFormatException ex) {
                            throw ex;
                        }
                    
                    // adiciona uma aresta ligando vr1 a vr2
                    vr1.list.add( new Aresta( vr2, w) );
                    // adiciona uma aresta ligando vr2 a vr1
                    vr2.list.add( new Aresta(vr1, w));

                }
                else{  //  GRAFO ORIENTADO
                    
                    // guardando a orientacao da aresta na variavel str
                    String str = input.next();
                    // lendo o segundo caractere (alfanumerico)
                    String ch2 = input.next();
                    
                    float w = 0;
                    // se o grafo eh ponderado
                    if(weighted)
                        try{
                            w = Float.parseFloat(input.next());  // atribui o peso a variavel w
                        }
                        catch (NumberFormatException ex) {
                            throw ex;
                        }
                    
                    // procura o vertice que possui o valor ch1 no grafo e atribui a variavel vr2 
                    Vertice vr1 = getNode(ch1);
                    
                    // se nao encontrou...
                    if(vr1 == null){
                        // cria o vertice
                        vr1 = new Vertice(ch1);
                        // adiciona no grafo
                        v[indexAtual] = vr1;
                        // incrementando o indice atual 
                        indexAtual++;
                    }
                    
                    // procura o vertice que possui o valor ch2 no grafo e atribui a variavel vr2
                    Vertice vr2 = getNode(ch2);
                    
                    // se nao encontroou...
                    if(vr2 == null){
                        // cria o vertice
                        vr2 = new Vertice(ch2);
                        // adiciona no grafo
                        v[indexAtual] = vr2;
                        // incrementa o indice atual
                        indexAtual++;
                    }
                    
                    // se a orientacao eh no sentido esq-dir...
                    if(str.equals(">>")){
                        // adiciona em vr1 a aresta que vai ate vr2 
                        vr1.list.add( new Aresta(vr2, w) );
                    }
                    
                    // se a orientacao eh no sentido dir-esq
                    else if(str.equals("<<"))
                        // adiciona em vr2 a aresta que vai ate vr1
                        vr2.list.add( new Aresta(vr1, w) );
                    
                    // a orientacao eh no sentido duplo
                    else{
                        // adiciona em vr1 a aresta que vai ate vr2 
                        vr1.list.add( new Aresta(vr2, w) );
                        // adiciona em vr2 a aresta que vai ate vr1 
                        vr2.list.add( new Aresta(vr1, w) );
                    }
                    
                }
               
            }
            
            // fechando o arquivo
            input.close();
            
        } catch (IOException ex) {
            throw ex;
        }
        catch (NumberFormatException ex) {
            throw ex;
        }
    }
    
    
    // ------------------------------ MÈTODOS ----------------------------------------
    
    // Procura no grafo um vertice com o valor especificado e o retorna.
    // Recebe: 
    //      * vertice: valor que queremos procurar no grafo 
    // Retorna:
    //      - o vertice que possui o valor especificado, ou null caso contrario
    public Vertice getNode(String vertice){
        // percorrendo o vetor de vertices do grafo
        for(int i = 0; i < n; i++)
            // se o vertice em v[i] existe e o seu valor eh igual ao procurado... 
            if(v[i] != null && v[i].value.equals(vertice) )
                // retorna o vertice na posicao i
                return v[i];
        
        // nao encontrou o vertice procurado
        return null;
    }
    
    
    // Busca em largura
    // Recebe:
    //      * s: vertice inicial 
    public void BFS(Vertice s){
        // percorrendo os vertices do grafo e resetando seus atributos para o padrao
        for(int i = 0; i < v.length; i++){
            v[i].color = Vertice.WHITE;
            v[i].d = Integer.MAX_VALUE;
            v[i].pred = null;
        }
        
        // mudando a cor do vertice inicial para cinza
        s.color = Vertice.GRAY;
        // o tempo de inicio eh zero
        s.d = 0;
        // o predecessor do vertice inicial nao existe
        s.pred = null;
        
        // inicializando a fila
        Fila q = new Fila();
        // coloca o vertice inicial na fila
        q.enqueue(s);

        // enquanto a fila nao estiver vazia...
        while(!q.isEmpty()){
            // atribui o primeiro da fila a variavel u
            Vertice u = (Vertice)q.dequeue();
            
            // percorrendo as arestas do vertice u
            for(Aresta a : u.list){
                // a variavel v eh um vertice adjacente a u ligados pela aresta a 
                Vertice v = a.v;
                
                // se acor do vertice v for branca...
                if(v.color == Vertice.WHITE){
                    // faz o vertice v ficar cinza sinalizando que foi descoberto
                    v.color = Vertice.GRAY;
                    // distancia do vertice da origem
                    v.d = u.d + 1;
                    // o predecessor de v eh o vertice u
                    v.pred = u;
                    
                    // coloca o vertice v na fila
                    q.enqueue(v);
                }
            }

            // mudando a cor do vertice u para preto (todos os vertices adjacentes a u foram descobertos)
            u.color = Vertice.BLACK;
        }
    }
    
    
    // Busca em profundidade
    // Recebe:
    //      * v: o vertice inicial
    public void DFS(Vertice s){
        // resetando os atributos de todos os vertices do grafo
        for(Vertice u : v){
            u.color = Vertice.WHITE;
            u.pred = null;
        }
        
        // inicializando o tempo
        tempo = 0;
        // inicializando a quantidade de ciclos encontrados (quantidade de arestas do tipo retorno)
        ciclos = 0;
        

        // variavel que vai guardar o indice do vertice inicial
        int i = 0;
        for(i = 0; i < v.length; i++)
            // encontrou o vertice que eh igual a s
            if(v[i] == s){
                break;
            }
        // a partir daqui, i contem o indice do vertice s
        
        Vertice atual = v[i];
        
        do{
            // se a cor do vertice atual for branca...
            if(atual.color == Vertice.WHITE)
                // faz a visitacao no vertice
                visit(atual);
            
            // se i for maior do que o tamanho do grafo...
            if(i + 1 >= v.length)
                i = 0;   // i volta pro primeiro vertice do grafo
            else
                i++;  // senao, incrementa i
            
            // indo para o proximo vertice do grafo
            atual = v[i];
        }while(atual != s);  // enquanto nao visitou todos os vertices do grafo
        
        /*for(int i = pos;;){
            if(v[i].color == Vertice.WHITE)
                visit(v[i]);
            i++;
            
            if(i >= v.length)
                i = 0;
            if(v[i] == s)
                break;
        }*/
    }
    
    
    // Metodo usado na busca em profundidade.
    // Recebe:
    //      * u: um vertice ainda nao descoberto (branco)
    private void visit(Vertice u){
        // muda a cor do vertice para cinza
        u.color = Vertice.GRAY;
        // incrementa o tempo
        tempo++;
        // atribuido o tempo de descoberta do vertice u
        u.d = tempo;
        
        // percorrendo todas as arestas que ligam u a outro vertice
        for(Aresta a : u.list){
            // v recebe o vertice adjacente atual
            Vertice v = a.v;
            
            // se o vertice v ainda nao foi desoberto (sua cor eh branca)...
            if(v.color == Vertice.WHITE){
                // o predecessor de v recebe u
                v.pred = u;
                // chama a funcao visit recursivamente
                visit(v);
            }
            else if(v.color == Vertice.GRAY){  // se o vertice for cinza, temos um ciclo
                // o tipo dessa aresta eh retorno
                a.type = "ret";
                // incrementa a quantidade de ciclos do grafo
                ciclos++;  
            }
            else if (u.d < v.d)  // o vertice eh preto e o tempo de descoberta de u eh menor que o tempo de descoberta de v
                // o tipo da aresta eh avanco
                a.type = "avan";
            else            // o vertice eh preto e o tempo de descoberta de u eh maior que o tempo de descoberta de v
                // o tipo da aresta eh cruzamento
                a.type = "cruz";
        }
        
        // todos os vertices adjacentes a u foram descobertos, logo, mudamos a cor de u para preto 
        u.color = Vertice.BLACK;
        // incrementando o tempo
        tempo++;
        // atribuindo o tempo final de u (termino da busca no vertice u)
        u.f = tempo;
    }
    
    
    // Imprime os vertices do grafo e seus adjacentes
    public void print(){
        for(Vertice vr : v){
            System.out.print(vr.value + " ");
            
            for(Aresta a : vr.list){
                Vertice u = a.v;
                
                System.out.print(" --> " + u.value + (weigthed ? "/" + a.weigth : "") );
            }
            
            System.out.println();
        }
    }
    
    
    // Retorna a quantidade de ciclos do grafo. 
    public int quantCiclos(){
        // inicializando a quantidade de ciclos como zero
        ciclos = 0;
        // realizando a busca em profundidade
        DFS(v[0]);
        // retorna a quantidade de ciclos
        return ciclos;
    }
    
    
    // Imprime o resultado da busca em largura na forma de tabela 
    public void printBfsResult(){
        System.out.println("+-----------+-------------+---------------+");
        System.out.println("|  Vertice  |  Distancia  |  Predecessor  |");
        System.out.println("+-----------+-------------+---------------+");
        
        for(Vertice vr : v){
            // imprimido vertice
            if(vr.value.length() == 1)
                System.out.print("|    " + vr.value + "      |");
            else if(vr.value.length() == 2)
                System.out.print("|    " + vr.value + "     |");
            else
                System.out.print("|    " + vr.value + "    |");
            
            // imprimindo a distancia
            if(vr.d < 10)
                System.out.print("      " + (int)vr.d + "      |");
            else if(vr.d < 100)
                System.out.print("     " + (int)vr.d + "      |");
            else if(vr.d == Integer.MAX_VALUE)
                System.out.print("    -----    |");
            else 
                System.out.print("     " + (int)vr.d + "     |"); 
            
            // imprimindo predecessor
            if(vr.pred == null)
                System.out.print("      ---      |");
            else{
                if(vr.pred.value.length() == 1)
                    System.out.print("       " + vr.pred.value + "       |");
                else if(vr.pred.value.length() == 2)
                System.out.print("       " + vr.pred.value + "      |");
                else
                    System.out.print("       " + vr.pred.value + "     |");
            }
            
            System.out.println();
        }
        System.out.println("+-----------+-------------+---------------+");
    }
    
    
    // Imprime o resultado da busca em profundidade na forma de tabela
    public void printDfsResult(){
        System.out.println("+-----------+---------------+----------------+---------------+");
        System.out.println("|  Vertice  |  Tempo Desc.  |  Tempo Final.  |  Predecessor  |");
        System.out.println("+-----------+---------------+----------------+---------------+");
        for(Vertice vr : v){
            // imprimido vertice
            if(vr.value.length() == 1)
                System.out.print("|    " + vr.value + "      |");
            else if(vr.value.length() == 2)
                System.out.print("|    " + vr.value + "     |");
            else
                System.out.print("|    " + vr.value + "    |");
            
            // imprimindo tempo descoberta
            if(vr.d < 10)
                System.out.print("       " + (int)vr.d + "       |");
            else if(vr.d < 100)
                System.out.print("       " + (int)vr.d + "      |");
            else if(vr.d == Integer.MAX_VALUE)
                System.out.print("  Infinity   |");
            else 
                System.out.print("     " + (int)vr.d + "     |"); 
            
            
            // imprimindo tempo final
            if(vr.f < 10)
                System.out.print("       " + vr.f + "        |");
            else if(vr.f < 100)
                System.out.print("       " + vr.f + "       |");
            else 
                System.out.print("       " + vr.f + "      |"); 
            
            
            // imprimindo predecessor
            if(vr.pred == null)
                System.out.print("      ---      |");
            else{
                if(vr.pred.value.length() == 1)
                    System.out.print("       " + vr.pred.value + "       |");
                else if(vr.pred.value.length() == 2)
                System.out.print("       " + vr.pred.value + "      |");
                else
                    System.out.print("       " + vr.pred.value + "     |");
            }
            
            System.out.println();
        }
        System.out.println("+-----------+---------------+----------------+---------------+");
    }
    
    
    // Retorna a matriz de distancias do grafo (distancia entre todos os pares de vertice do grafo)
    public float[][] getMatrizDeDistancias(){
        // matriz de distancias (tamanho NxN sendo N = numero de vertices no grafo)
        float[][] m = new float[v.length][v.length];
        
        // para cada vertice no grafo...
        for(int i = 0; i < v.length; i++){
            // realiza a busca em largura para o vertice atual
            BFS(v[i]);
            // coloca as distancias na linha correspondente
            for(int j = 0; j < v.length; j++)
                m[i][j] = v[j].d;
        }
        
        // retorna a matriz de distancias
        return m;
    }
    
    
    // Imprime a matriz de distancias.
    // Recebe:
    //      * m: matriz de distancias a ser imprimida
    public void printMatrizDeDistancias(float [][] m){
        // imprimindo linha
        System.out.print("\n+-----+");
        for(int i = 0; i < v.length; i++){
            System.out.print("-----+");
        }
        System.out.println();
        
        // imprimindo linha com vertices
        System.out.print("|     |");
        for(int i = 0; i < v.length; i++){
            if(v[i].value.length() == 1)
                System.out.print("  " + v[i].value + "  |");
            else if(v[i].value.length() == 2)
                System.out.print("  " + v[i].value + " |");
            else 
                System.out.print(" " + v[i].value + " |");
        }
        
        // imprimindo linha
        System.out.print("\n+-----+");
        for(int i = 0; i < v.length; i++){
            System.out.print("-----+");
        }
        System.out.println();
        
        // imprimindo distancias 
        for(int i = 0; i < m.length; i++){
            // vertices
            if(v[i].value.length() == 1)
                System.out.print("| " + v[i].value + "   |");
            else if(v[i].value.length() == 2)
                System.out.print("| " + v[i].value + "  |");
            else
                System.out.print("| " + v[i].value + " |");
            
            // distancias
            for(int j = 0; j < m[i].length; j++){
                if(m[i][j] < 10)
                    System.out.printf("  %.0f  |", m[i][j]);
                else if(m[i][j] == Integer.MAX_VALUE)
                    System.out.print("  -  |");
                else
                    System.out.printf("  %.0f |", m[i][j]);
            }
            
            if(i < m.length - 1)
                System.out.println();
        }
        
        // imprimindo ultima linha
        System.out.print("\n+-----+");
        for(int k = 0; k < v.length; k++)
            System.out.print("-----+");
        System.out.println();
    }
    
    
    // ----------------- METODOS USADOS NO ALGORITMO DE DIJKSTRA ------------------------------
    
    // Inicializa as estimativas de caminho mais curtos e os predecessores. Esse metodo eh usado no slgoritmo de Dijkstra.
    // Recebe:
    //      * s: o vertice inicial
    private void initializeSingleSource(Vertice s){
        for(Vertice u : v){
            u.d = Integer.MAX_VALUE;
            u.pred = null;
        }
        
        s.d = 0;
    }
    
    
    // Testa se podemos melhorar o caminho mais curto para v encontrado ate agora pela passagem atraves de u. Se sim, 
    // atualiza v.d e v.pred.
    // Recebe:
    //      * u: vertice que talvez diminua a estimativa de caminho mais curto ate v
    //      * v: vertice que pode ter a sua estimativa de caminho mais curto diminuido
    //      * w: aresta que liga u e v
    private void relax(Vertice u, Vertice v, Aresta w){
        if(v.d > u.d + w.weigth){
            v.d = u.d + w.weigth;
            v.pred = u;
        }
    }
    
    
    // Extrai o vertice que possui a menor estimativa de caminho mais curto de uma colecao de vertices. Usado no algoritmo de Dijkstra.
    // Recebe:
    //      * q: uma colecao de vertices
    // Retorna:
    //      - o vertice com a menor estimativa de caminho mais curto
    private Vertice extractMin(ArrayList<Vertice> q){
        // inicializando o vertice com o menor caminho mais curto como null
        Vertice min = q.get(0);
        // inicializando a menor distancia como infinito (o maior numero inteiro)
        float lowestDistance = min.d;

        // percorrendo os vertices da colecao
        for(Vertice v : q){
            // se encontrou um vertice com estimativa de caminho mais curto menor...
            if(v.d < lowestDistance){
                // atualiza a menor estimativa de caminhomais curto encontrada
                lowestDistance = v.d;
                // atualiza o menor vertice encontrado
                min = v;
            }
        }
        
        // remove o vertice encontrado da colecao q
        q.remove(min);
        // retorna o vertice encontrado
        return min;
    }
    //------------------------------------------------------------------------------------------------------------
    
    
    
    // Algoritmo de Dijkstra. Eh usado para clacular o caminho mais curto entre vertices partindo de s.
    // Assume-se que o grafo eh ponderado e que seus pesos sao positivos.
    // Recebe:
    //      * s: o vertice inicial
    public void dijkstra(Vertice s){
        // inicializando as estimativas de caminho mais curto e predecessores de todos os vertices do grafo
        initializeSingleSource(s);
        
        // colecao que contem todos os vertices do grafo que ainda nao tiveram suas arestas relaxadas
        ArrayList<Vertice> Q = new ArrayList<Vertice>();
        // olocando todos os vertices do grafo na colecao
        for(Vertice vr : v)
            Q.add(vr);
        
        // enquanto houver vertices na colecao Q...
        while(Q.size() != 0){
            // extrai de Q o vertice com a menor estimativa de caminho mais curto
            Vertice u = extractMin(Q);
            
            // percorrendo as arestas do vertice u
            for(Aresta a : u.list){
                // atribuindo o vertice ate onde leva a aresta atual a variavel vr 
                Vertice vr = a.v;
                
                // relaxando a aresta a
                relax(u, vr, a);
            }
            
        }
    }
    
    
    // Imprime o resultado do algoritmo de Dijkstra na forma de tabela.
    public void printDijkstraResult(){
        System.out.println("+-----------+------------------------+---------------+");
        System.out.println("|  Vertice  |  Distancia ate origem  |  Predecessor  |");
        System.out.println("+-----------+------------------------+---------------+");
        
        for(Vertice vr : v){
            if(vr.value.length() == 1)
                System.out.print("|    " + vr.value + "      |");
            else if(vr.value.length() == 2)
                System.out.print("|    " + vr.value + "     |");
            else
                System.out.print("|    " + vr.value + "    |");
            
            if(vr.d < 10)
                System.out.print("           " + vr.d + "          |");
            else if(vr.d < 100)
                System.out.print("           " + vr.d + "         |");
            else if(vr.d == Integer.MAX_VALUE)
                System.out.print("        -------         |");
            else
                System.out.print("           " + vr.d + "        |");
            
            
            if(vr.pred == null)
                System.out.print("     ----      |");
            else if(vr.pred.value.length() == 1)
                System.out.print("      " + vr.pred.value + "        |");
            else if(vr.pred.value.length() == 2)
                System.out.print("      " + vr.pred.value + "       |");
            else
                System.out.print("      " + vr.pred.value + "      |");
            
            System.out.println();
        }
        
        System.out.println("+-----------+------------------------+---------------+");
    }
    
    
    // Imprime o caminho mais curto entre dois vertices. Antes de usar esse metodo, realizar a busca em largura ou Dijkstra.
    // Recebe:
    //      * s: o vertice de partida
    //      * v: o vertice de chegada
    public void printPath(Vertice s, Vertice v){
        if(v == s)
            System.out.print(s.value + "  " );
        else if(v.pred == null)
            System.out.println("Nenhum caminho de " + s.value + " para " + v.value + " existente.");
        else{
            printPath(s, v.pred);
            System.out.print(v.value + "  ");
        }
    }
}
