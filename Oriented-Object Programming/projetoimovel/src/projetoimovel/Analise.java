
package projetoimovel;

public class Analise {
    
    public static double analise(Imovel i, double p){
       if(i instanceof Casa) { 
           System.out.println("Casa : ");
           return i.valorize(p);
       }
       else if(i instanceof Apartamento){
           System.out.println("Apartamento : ");
           return i.desvalorize(p);
       }
       else{
           System.out.println("Imovel : ");
           return i.valorize(p);
       }
    }
    
    public static void main(String[] args) {
        Casa x = new Casa(100.0);
        Apartamento z = new Apartamento(400.0);
        
        System.out.println("L1: "+x.valorize(20));
        System.out.println("L2: "+x.desvalorize(20));
        double u = z.valorize(20) + z.desvalorize(20);
        System.out.println("L3: "+u);
        
        System.out.println("L4: "+analise(x, 10));
        System.out.println("L5: "+analise(z, 30));
        Imovel w = new Casa(200.0);
        System.out.println("L6: "+analise(w, 50));
        
        Imovel y = new Apartamento(200.0);
        System.out.println("L7: "+analise(y, 50));
        
        w = x;
        System.out.println("L8: "+analise(w, 50));
        
        w = z;
        System.out.println("L9: "+analise(w, 50));
        
        w = y;
        System.out.println("L10: "+analise(w, 50));
        
    }
    
}
