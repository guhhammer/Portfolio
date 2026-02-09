
package Pilha;

import java.util.Scanner;

public class Pilha {

    //Nomes:  Gustavo Hammerschmidt,  João Capoani, Davi Leal.
    
    int topo;
    int[] dado;
    int max;
    
    // Uso: construtor de Pilha, define o tamanho. 
    public Pilha(int m){
        this.topo = -1;
        this.max = m;
        this.dado = new int[max];
    }   // gemacht.
    
    
    // Begin: Exercício 1.                                     -----------------
     
    // Uso: verificar se está vazia.
    public String vazia(){
        if(topo == -1){ return "Pilha vazia";}
        else{ return "Pilha não está vazia.";}
    }   // gemacht.
    
    // Uso: verificar se está cheia.
    public String overflow(){
        if(topo == (max-1)){ return "Pilha Cheia!";}
        else{ return "Pilha não Cheia!";}
    }   // gemacht.
    
    // Uso: adiciona um elemento ao topo da pilha.
    public void empilhar(int n){
        if(topo != (max-1)){
            this.topo++;
            this.dado[topo] = n;
        }
        else{
            System.out.println("Pilha cheia! \nDado não pilhado.\n");
        }
    }   // gemacht.
    
    // Uso: remove o elemento do topo da pilha.
    public int desempilhar(){
        if(topo != -1){
            int aux = topo;
            topo--;
            return dado[aux];
        }
        else{ System.out.println("Pilha está vazia!");
        return 0;}
    }   // gemacht.
    
    // Uso: vê o elemento do topo
    public int topo(){
        return dado[topo];
    }   // gemacht.
    
    // Uso: Exibe os exemplos e suas soluções. 
    public static void _1exemplos(){
        
        Pilha x = new Pilha(5);
        System.out.println("Exemplos exercício 1:");
        x.empilhar(10);
        System.out.println("a) "+x.topo()+" .emp");
        
        x.empilhar(-2);
        System.out.println("b) "+x.topo()+" .emp");
        
        x.empilhar(16);
        System.out.println("c) "+x.topo()+" .emp");
        
        System.out.println("d) "+x.topo()+" .emp");
        
        System.out.println("e) "+x.desempilhar()+" .des");
        
        x.empilhar(40);
        System.out.println("f) "+x.topo()+" .emp");
        
        System.out.println("g) "+x.desempilhar()+" .des");
        System.out.println("h) "+x.desempilhar()+" .des");
        System.out.println("i) "+x.desempilhar()+" .des");
        
        x.empilhar(5);
        System.out.println("j) "+x.topo()+" .emp");
        
    }   // gemacht.
    
    // End: Exercício 1.                                       -----------------
    
    
    // Begin: Exercício 2.                                     -----------------
    
    // Uso: verificar se está vazia.
    public String vaziaEX2(){
        if(topo == -1){ return "Pilha vazia";}
        else{ return "Pilha não está vazia.";}
    }   // gemacht.
    
    // Uso: verificar se está cheia.
    public String overflowEX2(){
        if(topo == (max-1)){ return "Pilha Cheia!";}
        else{ return "Pilha não Cheia!";}
    }   // gemacht.
    
    // Uso: retorna o oposto do sinal, utilizada no método validar.
    public char reverse(char aux){
        if(aux == ')'){return '(';}
        else if(aux == ']'){return '[';}
        else if(aux == '}'){return '{';}
        else{return 0;}
    }   // gemacht.
    
    // Uso: valida a String de entrada.
    public String validar(String n){
        System.out.println("\n\tExpressão: \t"+n+"\n");
        char saver = '#'; 
        // For:  Percorre a string de [0] a [String.length()].
        for(int i = 0; i < max; i++){
            // Se o topo não exceder o tamanho da String.
            if(topo != (max-1)){
               // aux  = auxiliar. 
               char aux = n.charAt(i);
               // Adiciona (, [ ou { à pilha. 
               if(aux == '(' || aux == '[' || aux == '{'){
                   this.topo++;
                   this.dado[topo] = aux;
                   System.out.println("Pilha ["+topo+"] <= adicionado <= "+aux);
               }
               // Se aux igual a ), ], ou }.
               else if(aux == ')' || aux == ']' || aux == '}'){
                   // Se o topo estiver vazio, 
                   //                 ou seja, ), ] ou } seja o primeiro.
                   if(topo == -1){ 
                       return "\n\tFórmula mal-feita. "
                               + "\tErro: Pilha["+topo+"]: "+aux
                            + "\n\t\t(Primeira Verificação). \n";
                   }
                   
                   // Se o oposto do aux for igual ao valor do topo.
                   else if(dado[topo] == reverse(aux) && reverse(aux) != 0){
                       int aux2 = topo;
                       topo--;
                       System.out.println("Pilha ["+aux2+"] <= removido <= "+aux);
                   }
  
                   //Se o topo diferir do atual auxiliar.
                   else {  return
                              "\n\tFórmula mal-feita. "
                           + "\tErro: Pilha["+topo+"]: "+aux
                            + "\n\t\t(Segunda Verificação). \n";}   
                    
               }
               //Vai ignorar todo o char que não for: (, [, {, ), ] ou } .
               else{;}
               saver = aux;
            }
            else{System.out.println(overflowEX2());} 
            /* 
            Não necessita já que não há como o número de 
            caractéres ser maior do que o próprio 
            comprimento da String e, portanto, não estoura.
            */            
        }
        // Se o loop for executado devidamente até aqui e o topo for -1, 
        // então, a fórmula foi bem feita. 
        if(topo == -1){  return "\n\t\tFórmula bem-feita\n"; }
        
        // Se não houver um sinal para fechar( ),],} ) os sinais ( (, [, { ),
        // flag de erro.  Não válida.
        else{ return "\n\tFórmula mal-feita. \tErro: Pilha["+topo+"]: "+saver
                + "\n\t\t(Última Verificação). \n";}
        
    }   // gemacht.
    
    // Uso: Exibe os exemplos e suas soluções. 
    public static void _2exemplos(){
        
        //a
        System.out.println("Exemplo A:\n");
        String a = "(A+B})";
        Pilha a_p = new Pilha(a.length());
        System.out.println(a_p.validar(a));
        
        //b
        System.out.println("Exemplo B:\n");
        String b = "{[A + B] – [(C - D)]";
        Pilha b_p = new Pilha(b.length());
        System.out.println(b_p.validar(b));
        
        //c
        System.out.println("Exemplo C:\n");
        String c = "(A + B) – {C + D} – [F + G]";
        Pilha c_p = new Pilha(c.length());
        System.out.println(c_p.validar(c));
        
        //d
        System.out.println("Exemplo D:\n");
        String d = "((H) * {([J + K])}) ";
        Pilha d_p = new Pilha(d.length());
        System.out.println(d_p.validar(d));
        
        //e
        System.out.println("Exemplo E:\n");
        String e = "(((A)))";
        Pilha e_p = new Pilha(e.length());
        System.out.println(e_p.validar(e));
        
    }   // gemacht.
    
    // Execute o exerício 2.
    public static void execute(int modo, String n){
        Scanner lk = new Scanner(System.in);
        if(modo == 1){
            System.out.println("Digite uma expressão: ");
            String x = lk.next();
            Pilha  x_p = new Pilha(x.length());
            System.out.println(x_p.validar(x));
        }
        else{
            Pilha  n_p = new Pilha(n.length());
            System.out.println(n_p.validar(n));
        }
    }
    
    //End: Exercício 2.                                        -----------------
    
    // Uso: explicativo.
    public static void ajuda(){
        System.out.println(
        "Begin: Método ajuda ------------------------------------------- \n\n"+        
        "\tNomes:  Gustavo Hammerschmidt,  João Capoani, Davi Leal.\n"+
        "\n Funções: \n\n"+
        "    Exercício 1: vazia(), overflow(), empilhar(), desempilhar(), "
        + "topo() e"+
        "\n\t\t\t\t\t\t\t_1exemplos()."+        
        "\n    Exercício 2: vaziaEX2(), overflowEX2(), reverse(), validar() e"+
        "\n\t\t\t\t\t\t\t_2exemplos()."+
        "\n\n Funções: _1exemplos e _2exemplos retornam as respostas dos "
        + "exemplos \n\t   postados no PDF da 2ª aula."+
        "\n\n\tEm relação ao método execute(modo, String);:\n\tse o modo for"
        + " igual a 1, o scanner analisará\n\to input do usuário. "
        + "Caso prefira escrever a\n\texpressão na função, "
        + "defina o modo como 0 (padrão). "+
          "\n\n\tObservação:  \n\t  -método cria_pilha(n) é o meu construtor Pilha"
                + "\n\t  -método cheia() é o meu método overflow() ou,"
                + "\n\t  no caso do exercício 2, overflowEx2()"
                + "\n\t  -o método reverse é uma função pertencente à função "
                + "\n\t  validar que, por motivos de comodidade, coloquei fora "
                + "\n\t  da função e, consequentemente, tornando-a mais legível."+
        "\n\nEnd: Método ajuda ---------------------------------------------\n\n"
        );
    }    //gemacht.
    
    
    public static void main(String[] args) {
        
        //Nomes:  Gustavo Hammerschmidt,  João Capoani, Davi Leal.
        
        ajuda();
        
        //Exercício 1:
        //_1exemplos();
        Pilha x = new Pilha(5);
        x.empilhar(2);
        System.out.println(x.topo());
        x.empilhar(3);
        x.empilhar(2);
        x.empilhar(2);
        x.empilhar(21);
        x.empilhar(234);  // retornará flag de excesso.
        System.out.println(x.desempilhar());
        
        // Exercício 2:
        //_2exemplos();
        //execute(modo,  String) modo ==1: Scanner; Ou: use string.
        execute(0, "{[(7*2)+4/7]*[(-2)/3]}+{[(A*5)/8]*3}");
      
    }
}