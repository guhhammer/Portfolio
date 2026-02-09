
package number;

public class Real {
    
    private static int esquerda = 0;
    private static double direita = 0;
    private static boolean positivo = true;
    
    public  Real(double n){
        esquerda = Esquerda(n);
        direita  = Direita(n);
        positivo = sign(n);
    }
    
    public boolean sign(double n){
        if(n < 0){return false;}
        else{return true;}
    }
    public int Esquerda(double n){return (int)n;}
    public double Direita(double n){
        int aux;
        aux = (int) n;
        return n - aux;     
    }
    
    public static boolean getPositivo(){return positivo;}
    public static int getEsquerda(){return esquerda;}
    public static double getDireita(){return direita;}
    
    
    public static String signR(){
        if(getPositivo() == true){return "positivo";}
        else{return "negativo";}
    }
    public static double n(){return (getEsquerda() + getDireita());}
    
    
    public static double[] Equation(double n){
         
        double auxE = (int)n;
        
        double aux = (int) n;
        double auxD = n - aux;
        
        int sign;
        if(n() < 0){sign = -1;
                auxE = -1*auxE;
                auxD = -1*auxD;
        }
        else{sign = 1;}
        
        double[] A = {sign, auxE, auxD};
        return A;
       
    }
    
    public static void clean(){
        positivo = true;
        esquerda = 0;
        direita = 0;
    }

    //Resposta item a.
    public  static void itemA(){
        double[] A = Real.Equation(n());
        System.out.println("Item A:"+"\nNúmero: "+n()+
                "\nParte inteira de "+n()+": "+getEsquerda()+" ."+
                "\nParte decimal de "+n()+": "+getDireita()+" ."+
                "\nNúmero "+n()+" é "+signR()+"."+
                "\n"+n()+" = ("+(int)A[0]+")*( "+A[1]+" + "+A[2]+" )");
        System.out.print("\n");
      }

    //Resposta item b.
    public static void itemB(){
        double[] A = Equation(n());
        System.out.println("Item B: \nRecebi do método Equation() o "
                + "vetor A = {positivo, esquerda, direita}; \n"
                + "portanto, se A = { "+(int)A[0]+", "+A[1]+", "+A[2]+" }, "
                + "então, o número digitado foi: "+((int)(A[0])*(A[1]+A[2])));
        System.out.print("\n");
    }
    
    //Resposta item c:
    public static void soma(double n){
        double[] A = Equation(n());
        
        Real l = new Real(n);
        double[] B = l.Equation(n());
        clean();
        
        System.out.print("(("+A[0]+")*("+B[0]+")) * "
             + "{ ("+A[1]+"+"+B[1]+") + ("+A[2]+"+"+B[2]+") } = "
             +((A[0])*(B[0]))*((A[1]+B[1])+(A[2]+B[2])));
    } 

    public static void subtrai(double n){
        double[] A = Equation(n());
        
        Real l = new Real(n);
        double[] B = l.Equation(n());
        clean();
        
        System.out.print("(("+A[0]+")*("+B[0]+")) * "
                + "{ ("+A[1]+"-"+B[1]+") + ("+A[2]+"-"+B[2]+") } = "
                +((A[0])*(B[0]))*((A[1]-B[1])+(A[2]-B[2])));
    }

    public static void multiplica(double n){
        double[] A = Equation(n());
        
        Real l = new Real(n);
        double[] B = l.Equation(n());
        clean();
        
        System.out.println("(("+A[0]+")*("+B[0]+")) * "
                + "{ ("+A[1]+"+"+A[2]+") * ("+B[1]+"+"+B[2]+") } = "
                +((A[0])*(B[0]))*((A[1]+A[2])*(B[1]+B[2])));
    }

    public static void divide(double n){
        double[] A = Equation(n());
        
        Real l = new Real(n);
        double[] B = l.Equation(n());
        clean();
        
        System.out.println("(("+A[0]+")*("+B[0]+")) * "
                + "{ ("+A[1]+"+"+A[2]+") / ("+B[1]+"+"+B[2]+") } = "
                +((A[0])*(B[0]))*((A[1]+A[2])/(B[1]+B[2])));
    }

    public static void all4(double n){
        System.out.print("Soma: ");
        soma(n);
        System.out.print("\nSubtrai: ");
        subtrai(n);
        System.out.println("\nMultiplica: ");
        multiplica(n);
        System.out.println("\nDivide: ");
        divide(n);
    }
    
    //erro no item c
    
    public static void main(String[] args) {
        Real k = new Real(-8.5);
        //k.itemA();
        //k.itemB();
        //k.soma(2.74);
        //k.subtrai(2.64);
        //k.multiplica(2.64);
        //k.divide(2.64);
        k.all4(2.54412); 
    }



  }
