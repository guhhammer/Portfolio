
package Mini_Trabalho1;


import Mini_Trabalho1.grafos.*;


public class desafio{
    
    public static void BubbleSort(int[] A){
      int n = A.length;
      int temp = 0;
      boolean flag = false;
      
      for(int i = 0; i < n; i++) {
         flag = true;
         for(int j=1; j < (n-i); j++) {
            if(A[j-1] > A[j]) {
               temp = A[j-1];
               A[j-1] = A[j];
               A[j] = temp;
               flag = false;
            }
         }
         if(flag == true){ break;}
      }
    }
    
    // a
    static int[] sended_more5(grafos g){
        int[] aux = new int[5];
        int[] aux2 = new int[5];
        
        int soma = 0;
        
        for(int i = 0; i < g.tamanho; i++){
            for(int j = 0; j < g.tamanho; j++){
                soma = soma + g.matriz[i][j];
            }
            for(int k = 0; k < aux.length; k++){                
                if(aux[k] < soma){
                    aux[k] = soma;
                    BubbleSort(aux);                    
                    break;
                }  
            }
            soma = 0;
        }
        
        int soma2 = 0, hold_last = 0;
        for(int i = 0; i < g.tamanho; i++){
            for(int j = 0; j < g.tamanho; j++){
                soma2 += g.matriz[i][j];
            }
            
            for(int k = 0; k < aux.length; k++){
                if(aux[k] == soma2){
                    if(aux[k] != hold_last){
                        aux2[k] = i;
                        hold_last = soma2;
                    }
                }
            }
            soma2 = 0;
        }
        
        
        int[] aux3 = new int[10];
        int x = 0;
        for(int i =0; i < aux.length; i++){
            aux3[i] = aux[i];
        }
        for(int i=aux.length; i < aux.length*2;i++){
            aux3[i] = aux2[x];
            x++;
        }
        return aux3;
    }
    
    // b
    static int[] received_more5(grafos g){
        int[] aux = new int[5];
        int[] aux2 = new int[5];
        
        int soma = 0;
       
        for(int i = 0; i < g.tamanho; i++){
            for(int j = 0; j < g.tamanho; j++){
                soma = soma + g.matriz[j][i];
            }
            for(int k = 0; k < aux.length; k++){                
                if(aux[k] < soma){
                    aux[k] = soma;
                    BubbleSort(aux);
                    break;
                }  
            }
            soma = 0;
        }
        
        int soma2 = 0, hold = 0, hold_last = 0;
        for(int i = 0; i < g.tamanho; i++){
            for(int j = 0; j < g.tamanho; j++){
                soma2 += g.matriz[j][i];
                hold = i;
            }
            
            for(int k = 0; k < aux.length; k++){
                if(aux[k] == soma2){
                    if(aux[k] != hold_last){
                        aux2[k] = hold;
                        hold_last = soma2;
                    }
                }
            }
           
            soma2 = 0; hold = 0;
        }
        
        int[] aux3 = new int[10];
        int x = 0;
        for(int i =0; i < aux.length; i++){
            aux3[i] = aux[i];
        }
        for(int i=aux.length; i < aux.length*2;i++){
            aux3[i] = aux2[x];
            x++;
        }
        return aux3;
    }
    
    
    // e
    static int[] strongest_relation(grafos g){
        
        int strong = -1, hold_i = -1, hold_j = -1;
        
        int[] aux = new int[3];
        
        for(int i = 0; i < g.tamanho; i++){
            for(int j = 0; j < g.tamanho; j++){
                if(strong < g.matriz[i][j]){
                    strong = g.matriz[i][j];
                    hold_i = i;
                    hold_j = j;
                }
            }
        }
        aux[0] = strong; aux[1] = hold_i; aux[2] = hold_j;
        return aux;
    }
    
    // e
    static void strongests(grafos g){
        grafos aux = g;
        int[] first = strongest_relation(aux);
        int hold_value = first[0];
        
        System.out.println("i: "+first[1]+" label: "+g.vertices[first[1]].getNome()+
                           "\nj: "+first[2]+" label: "+g.vertices[first[2]].getNome()+
                           "\nPeso: "+first[0]);
        
        
        aux.remove_adjacencia(first[1], first[2]);
        while(true){
            if(strongest_relation(aux)[0] == hold_value){

                int[] hold = strongest_relation(aux);

                System.out.println("\ni: "+hold[1]+" label: "+
                            g.vertices[hold[1]].getNome()+
                            "\nj: "+hold[2]+" label: "+
                            g.vertices[hold[2]].getNome()+
                            "\nPeso: "+hold[0]);
                
                aux.remove_adjacencia(hold[1], hold[2]);
                }
            else{break;}
       }
    }
    
    //c
    static float grau_entrada(grafos g){
    
        int count = 0;
        int count_vertice = 0;
        
        for(int i = 0; i < g.tamanho; i++){
            for(int j = 0; j < g.tamanho; j++){
                count += g.matriz[j][i];
                if(g.matriz[j][i] != 0){
                    count_vertice += 1;
                }
            }
        }
        
        return (float)(count/count_vertice*1.0);
    } 
    
    //d
    static float grau_saida(grafos g){
    
        int count = 0;
        int count_vertice = 0;
        
        for(int i = 0; i < g.tamanho; i++){
            for(int j = 0; j < g.tamanho; j++){
                count += g.matriz[i][j];
                if(g.matriz[i][j] != 0){
                    count_vertice += 1;
                }
            }
        }
        
        return (float)(count/count_vertice*1.0);
    } 
    
    public static void main(String[] args){
        
        grafos GR = new grafos(25);
        
        GR.cria_adjacencia(0, 12, 5);
        GR.cria_adjacencia(0, 22, 2);
        GR.cria_adjacencia(0,  9, 7);
        
        GR.cria_adjacencia(4, 10, 4);
        GR.cria_adjacencia(4,  5, 5);
        GR.cria_adjacencia(4,  1, 2);
        
        GR.cria_adjacencia(7, 15, 10);
        GR.cria_adjacencia(7, 1, 10);
        GR.cria_adjacencia(7,  8, 8);
        
        GR.cria_adjacencia(12, 1, 5);
        GR.cria_adjacencia(12, 2, 5);
        GR.cria_adjacencia(12, 3, 5);
        
        GR.cria_adjacencia(15, 6,  4);
        GR.cria_adjacencia(15, 14, 4);
        GR.cria_adjacencia(15, 16, 4);
        
        GR.cria_adjacencia(19, 11, 6);
        GR.cria_adjacencia(19, 13, 5);
        GR.cria_adjacencia(19, 17, 4);
        
        GR.cria_adjacencia(23, 21, 15);
        GR.cria_adjacencia(23, 12, 20);
        GR.cria_adjacencia(23,  2, 20);
        
               
        System.out.println("Os 5 que mais enviam:");
        for(int i = 0; i < sended_more5(GR).length/2; i++){
            System.out.print("i: "+sended_more5(GR)[i+5]+
                    "  Peso: "+sended_more5(GR)[i]+" \n");
            if(i == 5){break;}
        }
        
        System.out.println("\n\nOs 5 que mais recebem:");
        for(int i = 0; i < received_more5(GR).length/2; i++){
            System.out.print("i: "+received_more5(GR)[i+5]+
                             "  Peso: "+received_more5(GR)[i]+" \n");
        }
       
        GR.seta_afirmacao(23, "o melhor");
        GR.seta_afirmacao(12, "ff");
        GR.seta_afirmacao(2,  "1st");
        
        System.out.println("\nO(s) mais ativo(s):");
        strongests(GR);
        
        
        System.out.println("\nGrau de Entrada: "+grau_entrada(GR));
        System.out.println("Grau de Saida: "+grau_saida(GR)+"\n");
    }
}
