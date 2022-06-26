
package Paralelismo2;

import java.io.PrintWriter;
import java.util.Random;

public class Matrizes {
    
    private String nome = "null";
    public int linhas = 0, colunas = 0, matriz[][], boundary = 10000;
    
    public Matrizes(int i, int j){
        
        this.linhas = i;
        this.colunas = j;
        matriz = new int[linhas][colunas];
        
    }
    
    void setNome(String n){ this.nome = n;}
    
    String getNome(){ return this.nome; }
    
    void setBoundary(int n){ this.boundary = n; }
    
    void preencherRandom(){
        
        Random aux = new Random();
        
        for(int i = 0; i < this.linhas; i++){
            for(int j = 0; j < this.colunas; j++){
                this.matriz[i][j] = Math.abs(aux.nextInt(boundary));
            }
        }
    
        System.out.println("\nMatriz foi preenchida!!\n\n");
        
    }
     
    private static Matrizes multiplica(Matrizes a, Matrizes b){
        
        if(a.colunas == b.linhas){
        
            Matrizes c = new Matrizes(a.linhas, b.colunas);
            
            for(int i = 0; i < c.linhas; i++){
                for(int j = 0; j < c.colunas; j++){
                    int soma = 0;
                    for(int k = 0; k < a.colunas; k++){
                        for(int l = 0; l < b.linhas; l++){
                            if(k == l){
                                soma += a.matriz[i][k]*b.matriz[l][j];
                            }
                        }
                    }
                    c.matriz[i][j] = soma;
                }
            }
            
            return c;
            
        }
        else{
        
            return null;
            
        }
        
    }
    
    public static Matrizes multiplicar(Matrizes a, Matrizes b){
        return multiplica(a, b);  
    }
    
    
    void escreverMatriz(PrintWriter x){
        for(int i = 0; i < this.linhas; i++){
            for(int j = 0; j < this.colunas; j++){
                x.print(this.matriz[i][j]+"  ");
                if( j == this.colunas-1){ x.println("");}
            }
        }
    }
    
    void printMatriz(){
        
        System.out.print(nome+":\n\n");
        for(int i = 0; i < this.linhas; i++){
            for(int j = 0; j < this.colunas; j++){
                System.out.print(this.matriz[i][j]+"  ");
                if( j == this.colunas-1){ System.out.print("\n");}
            }
        }
        System.out.print("\n\n");
        
    }
    
    
    
}
