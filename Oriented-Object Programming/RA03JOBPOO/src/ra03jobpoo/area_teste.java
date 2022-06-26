
package ra03jobpoo;

public class area_teste{

    private String dataDeAniversario;
    private int dia;
    private int mes;
    private int ano;
    private double taxaDeCorreçao;

    public area_teste
        (int dd, int mm,int aaaa, double tdCorr){
            this.dia = dd;
            this.mes = mm;
            this.ano = aaaa;
            this.taxaDeCorreçao = tdCorr;
            this.dataDeAniversario = dd+"/"+mm+"/"+aaaa;
    }  
        // Construtor Poupança
    
    
    //dia, mes, ano atual: assinatura
    public int timeCalculator(int ddA, int mmA, int aaaaA){
        //mI -> mes inicial e mA -> mes atual
        int ddI = dia;
        int mmI = mes;
        int aaaaI = ano;
        int somaDIA;
        int somaMES = 0;
        int somaANO;
        double somatorio;
        
        if(ddI == ddA){somaDIA = 0;}
        else if(ddI < ddA){somaDIA = (ddA-ddI);}
        else if(ddI > ddA){somaDIA = Math.abs(ddA-30)+(30-ddI);}
        else{somaDIA = 0;}
        
        if(mmI == mmA){somaMES = 0;}
        else if(mmI < mmA){somaMES = mmA - mmI;}
        else if(mmI > mmA){somaMES = Math.abs(mmA-12)+(12-mmI);}
        else{somaDIA = 0;}
        
        if(aaaaI == aaaaA){somaANO = 0;}
        else if(aaaaI < aaaaA){somaANO = aaaaA-aaaaI;}
        else{somaANO = 0;}//impossivel voltar no tempo
        
        somatorio = (somaDIA + (somaMES*30) + (somaANO*12))/30;
        
        return ((int)somatorio);
        
    }    
    
    public static void main(String[] args) {
    
    area_teste x = new area_teste(30,10,2010,2);
        System.out.println(x.timeCalculator(10, 05, 2016));
    
    }
}
