
package _0_to_1_progression;

public class _0to1 {
    
    public static void progression(){
        
        float numerador = 0;
        float denominador = 1;
        int x = 1;
        
        do{
            try{
                System.out.println("( "+numerador+" / "+denominador
                        +" ) = "+numerador/denominador+
                        "\n \\\\\\");
                if(denominador < 1000000){;}
                else{Thread.sleep(1);}
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
            numerador++;
            denominador++;
           if(numerador/denominador == x){x = 0;}
           else{;}
        }while(x == 1);
        System.out.println("End");
    }
    
    public static void main(String[] args){
        progression();
    }

}
