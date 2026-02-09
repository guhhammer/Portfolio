
package calculator;


import java.util.Scanner;

public class Calculator {
    
    static Scanner lk = new Scanner(System.in);
    
    public static void mediaA(int[] a){
        double soma = 0;    
        for(int i = 0; i < a.length; i++){
            soma = soma + a[i];
        }
        System.out.println("\n\nEssa é a média aritmética do vetor:"
                                                     +soma/a.length);
    }
    
    public static void moda(int[] a){
        int[] contador = new int[a.length];
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length; j++){
                if(a[i] == a[j]){
                    contador[i] = contador[i] + 1;}
   
            }}
        
        
        System.out.println("\n");
        System.out.println("Esse é o vetor VetorRead: ");
        System.out.print("VetorRead[] = { ");
        for(int c =0; c < a.length; c++){
            System.out.print(a[c]+", ");
        }
        System.out.print("} "+"\n");
        System.out.println("");
        
        System.out.println("Esse é o vetor de ocorrências (repetições): ");
        System.out.print("Ocorrências[] = { ");
        for(int d = 0; d < a.length; d++){
            System.out.print(contador[d]+", ");
        }
        System.out.print("} "+"\n");
        System.out.println(" ");
        
        
        
        System.out.println("Agora, a lista de ocorrências: ");
        System.out.println("VetorRead[]          ||         Ocorrências: ");
        for(int e = 0; e < a.length; e++){
            System.out.println("Elemento VetorRead["+e+"] = "+a[e]+
                    " repete-se "+(contador[e]-1)+" vezes. ");
        }
        System.out.print("\n"+"\n"+"    Fim"+"\n");
        
        
        int maiorvalor = contador[0];
        for(int i = 0; i < contador.length; i++){
            if(maiorvalor < contador[i]){
                maiorvalor = contador[i];
            }
        }    
        
        int indice = 0;
        
        for(int i = 0; i < contador.length; i++){
            if(contador[i] == maiorvalor){
                indice = i;
                break;
            }
        }

        
        System.out.println("\n\nEsse é a moda do vetor: "+ a[indice]);
        
        
    }
    
    public static void main(String[] args) {
        
        
        
        
        int tamanho = 10;
        int[] VetorRead = new int[tamanho];
        for(int i = 0; i < VetorRead.length; i++){
            System.out.print("Defina o elemento "+i+" do VetorRead: ");
            VetorRead[i] = lk.nextInt();
        }
        
        //mediaA(VetorRead);
        moda(VetorRead);
    }
    
}
