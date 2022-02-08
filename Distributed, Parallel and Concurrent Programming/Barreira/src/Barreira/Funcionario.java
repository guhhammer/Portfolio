
package Barreira;

import java.util.Random;

public class Funcionario extends Thread{
    
    private Random periodo = new Random();
    
    public int codigo;
    public double salarioBruto,
                  descontoDeImpostoDeRenda = 0, 
                  descontoDeINSS = 0,
                  descontoDePrevidenciaPrivada = 0,
                  descontoDePlanoDeSaude = 0,
                  totalDeDescontos = 0,
                  salarioLiquido = 0;

    
    public Funcionario(int codigo){
        
        this.codigo = codigo;
        this.salarioBruto = 1000 + periodo.nextInt(5000-1000);
        this.salarioLiquido = salarioBruto;
    
    }
    
    public void impostoDeRendaRetidoNaFonte(){
        double valor = 0.2*(salarioBruto);
        
        this.descontoDeImpostoDeRenda = valor;
        this.totalDeDescontos += valor;
        this.salarioLiquido -= valor;
    
    }
    
    public void previdenciaObrigatoriaINSS(){
        double valor = 0.08*(salarioBruto);
        
        this.descontoDeINSS = valor;
        this.totalDeDescontos += valor;
        this.salarioLiquido -= valor;
    
    }
    
    public void previdenciaPrivada(){
        double valor = 0.04*(salarioBruto);
        
        this.descontoDePrevidenciaPrivada = valor;
        this.totalDeDescontos += valor;
        this.salarioLiquido -= valor;
    
    }
    
    protected void planoDeSaude(){
        double valor = 0.02*(salarioBruto);
        
        this.descontoDePlanoDeSaude = valor;
        this.totalDeDescontos += valor;
        this.salarioLiquido -= valor;
    }
    
}
