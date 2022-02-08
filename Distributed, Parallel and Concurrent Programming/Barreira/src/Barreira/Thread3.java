
package Barreira;

import static Barreira.Main.funcionario;
import static Barreira.Main.parte1;
import static Barreira.Main.parte2;
import static Barreira.Main.parte3;
import static Barreira.Main.parte4;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.Semaphore;

public class Thread3 extends Thread{
    
    Semaphore mutex1, mutex2, mutex3, mutex4;
    
    public Thread3(Semaphore m1, Semaphore m2, Semaphore m3, Semaphore m4){
        this.mutex1 = m1;
        this.mutex2 = m2;
        this.mutex3 = m3;
        this.mutex4 = m4;
    }
    
    
    public void run(){
        try{
            
            mutex1.acquire();
            
                for(int i = parte3; i < parte4; i++){
                    funcionario[i].previdenciaPrivada();
                }
                System.out.println("\nPrevidência Privada (Parte 3): feito.\n");
                
            mutex1.release();
            
            mutex2.acquire();
                
                for(int i = parte4; i < funcionario.length; i++){
                    funcionario[i].previdenciaPrivada();
                }
                System.out.println("\nPrevidência Privada (Parte 4): feito.\n");
                
            mutex2.release();
            
            mutex3.acquire();
                
                for(int i = parte1; i < parte2; i++){
                    funcionario[i].previdenciaPrivada();
                }
                System.out.println("\nPrevidência Privada (Parte 1): feito.\n");
                
            mutex3.release();
            
            mutex4.acquire();
                
                for(int i = parte2; i < parte3; i++){
                    funcionario[i].previdenciaPrivada();
                }
                System.out.println("\nPrevidência Privada (Parte 2): feito.\n");
                
            mutex4.release();
            
            String adder = "";
            
            for(int i = parte3; i < parte4; i++){
                adder += "ID: "+funcionario[i].codigo+
                         "\nSalário Bruto: "+funcionario[i].salarioBruto+
                         "\nSalário Líquido: "+funcionario[i].salarioLiquido+
                         "\nDesconto (Imposto de renda): "+
                        funcionario[i].descontoDeImpostoDeRenda+
                         "\nDesconto (INSS): "+funcionario[i].descontoDeINSS+
                         "\nDesconto (Previdência Privada): "+
                         funcionario[i].descontoDePrevidenciaPrivada+
                         "\nDesconto (Plano de Saude): "+
                        funcionario[i].descontoDePlanoDeSaude+
                        "\nTotal de Descontos: "+funcionario[i].totalDeDescontos+
                        "\n\n\n";
                
            }
            
            String path = "src\\Barreira\\Parte 3.txt";
            File criar3 = new File(path);
            
            FileWriter file3 = new FileWriter(path);
            file3.write(adder);
            file3.close();
            
        }catch(Exception e){ e.printStackTrace(); }
        
    }
}
