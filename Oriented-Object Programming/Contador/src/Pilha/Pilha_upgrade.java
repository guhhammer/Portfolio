
package Pilha;

import java.util.Scanner;

public class Pilha_upgrade {

    //Nomes:  Gustavo Hammerschmidt.
    
    int topo;
    int[] dado;
    int max;
    
    // Uso: construtor de Pilha, define o tamanho. 
    public Pilha_upgrade(int m){
        this.topo = -1;
        this.max = m;
        this.dado = new int[max];
    }   // gemacht.
    
    // Uso: verificar se está vazia.
    boolean vazia(){
        if(topo == -1){ return true;}
        else{ return false;}
    }   // gemacht.
    
    // Uso: verificar se está cheia.
    boolean overflow(){
        if(topo == (max-1)){ return true;}
        else{ return false;}
    }   // gemacht.
    
    // Uso: adiciona um elemento ao topo da pilha.
    void empilhar(int n){
        if(!this.overflow()){ this.topo++; this.dado[topo] = n; }
        else{ System.out.println("Pilha cheia! \nDado não pilhado.\n"); }
    }   // gemacht.
    
    // Uso: remove o elemento do topo da pilha.
    int desempilhar(){
        if(!this.vazia()){ int aux = topo; topo--; return dado[aux]; }
        else{ return -1;}
    }   // gemacht.
    
    // Uso: vê o elemento do topo
    int topo(){  return dado[topo]; }   // gemacht.
    
    
    // Uso: retorna o oposto do sinal, utilizada no método validar.
    char reverse(char aux){
        if(aux == ')'){return '(';}
        else if(aux == ']'){return '[';}
        else if(aux == '}'){return '{';}
        else{return 0;}
    }   // gemacht.
     
    // Uso: valida a String de entrada.
    int validar(String n){
        System.out.println("\n\tExpressão: \t"+n+"\n");
        char saver = '#'; 
        for(int i = 0; i < max; i++){
            if(!this.overflow()){
              
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
                   if(this.vazia()){  return 0; }
                   // Se o oposto do aux for igual ao valor do topo.
                   else if(dado[topo] == reverse(aux) && reverse(aux) != 0){
                       int aux2 = topo;
                       topo--;
                       System.out.println("Pilha ["+aux2+"] <= removido <= "+aux);
                   }
                   else {  return 2;}  //Se o topo diferir do atual auxiliar.     
               }
               else{;}//Vai ignorar todo o char que não for: (, [, {, ), ] ou }.
               saver = aux;
            }
            else{System.out.println(this.overflow());} 
            /* 
            Não necessita já que não há como o número de 
            caractéres ser maior do que o próprio 
            comprimento da String e, portanto, não estoura.
            */            
        }
        // Se o loop for executado devidamente até aqui e o topo for -1, 
        // então, a fórmula foi bem feita. 
        if(this.vazia()){  return 1; }
        // Se não houver um sinal para fechar( ),],} ) os sinais ( (, [, { ),
        // flag de erro.  Não válida.
        else{ return 3;}
        
    }   // gemacht.
   
    
    public static String retur(int n){
        if(n == 1){ return "\n\n\tFórmula Bem-Feita";}
        else if(n==0){
            return "\n\tFórmula mal-feita.\n\t\t(Primeira Verificação).\n";}
        else if(n ==2){
            return "\n\tFórmula mal-feita.\n\t\t(Segunda Verificação).\n";}
        else{
            return "\n\tFórmula mal-feita.\n\t\t(Última Verificação).\n";
        }
    }
    
    
    // Execute o exerício 2.
    public static void execute(int modo, String n){
        Scanner lk = new Scanner(System.in);
        if(modo == 1){
            System.out.println("Digite uma expressão: ");
            String x = lk.next();
            Pilha_upgrade  x_p = new Pilha_upgrade(x.length());
            System.out.println(x_p.validar(x));
        }
        else{
            Pilha_upgrade  n_p = new Pilha_upgrade(n.length());
            System.out.println(n_p.validar(n));
        }
    }
    
    //End: Exercício 2.                                        -----------------
    
    public static void main(String[] args) {
        
        //Nomes:  Gustavo Hammerschmidt.
        
        
        //Exercício 1
        Pilha_upgrade x = new Pilha_upgrade(5);
        x.empilhar(2);
        System.out.println(x.topo());
        x.empilhar(3);
        x.empilhar(2);
        x.empilhar(2);
        x.empilhar(21);
        x.empilhar(234);  // retornará flag de excesso.
        System.out.println(x.desempilhar());
        
        // Exercício 2:
        
        //int a = execute(0, "{[(7*2)+4/7]*[(-2)/3]}+{[(A*5)/83}");
      
    }
}
