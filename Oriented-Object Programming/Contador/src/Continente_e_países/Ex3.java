
package Continente_e_países;

import java.util.ArrayList;


public class Ex3 {
    
    public static String name;
    ArrayList<País> continente = new ArrayList<>();
      
    //a
    public Ex3(String n){name = n;} 
    
    //b
    public void setContinente(País n){continente.add(n);}
    
    //c
    public double dimension(){
        double ContSize = 0;
        for(int i = 0; i < continente.size(); i++){
            ContSize += continente.get(i).dimension;
        }
        return ContSize;
    }
    
    //d
    public double population(){
        double pop = 0;
        for(int i = 0; i < continente.size(); i++){
            pop += continente.get(i).pop;
        }
        return pop;
    }
    
    //e
    public double density(){
        double ContSize = 0;
        for(int i = 0; i < continente.size(); i++){
            ContSize += continente.get(i).dimension;
        }
        double pop = 0;
        for(int i = 0; i < continente.size(); i++){
            pop += continente.get(i).pop;
        }
        return pop/ContSize;
    }
    
    //f
    public String higherPop(){
        double higher = continente.get(0).pop;
        String name = continente.get(0).name;
        for(int i = 0; i < continente.size(); i++){
            if(higher < continente.get(i).pop){
                higher = continente.get(i).pop;
                name = continente.get(i).name;
            }
        }
        return "Biggest Population ("+name+"): "+higher;
    }
    
    //g
    public String lowerPop(){
        double lower = continente.get(0).pop;
        String name = continente.get(0).name;
        for(int i = 0; i < continente.size(); i++){
            if(lower > continente.get(i).pop){
                lower = continente.get(i).pop;
                name = continente.get(i).name;
            }
        }
        return "Smallest Population ("+name+"): "+lower;
    }
    
    //h
    public String higherDimension(){
        double higher = continente.get(0).dimension;
        String name = continente.get(0).name;
        for(int i = 0; i < continente.size(); i++){
            if(higher < continente.get(i).dimension){
                higher = continente.get(i).dimension;
                name = continente.get(i).name;
            }
        }
        return "Biggest Dimension ("+name+"): "+higher;
    }

    //i
    public String lowerDimension(){
        double lower = continente.get(0).dimension;
        String name = continente.get(0).name;
        for(int i = 0; i < continente.size(); i++){
            if(lower > continente.get(i).dimension){
                lower = continente.get(i).dimension;
                name = continente.get(i).name;
            }
        }
        return "Smallest Dimension ("+name+"): "+lower;
    }

    //j 
    public String ratioBigtoSmall(){
        double higher = continente.get(0).dimension;
        String name1 = continente.get(0).name;
        for(int i = 0; i < continente.size(); i++){
            if(higher < continente.get(i).dimension){
                higher = continente.get(i).dimension;
                name1 = continente.get(i).name;
            }
        }
        double lower = continente.get(0).dimension;
        String name2 = continente.get(0).name;
        for(int i = 0; i < continente.size(); i++){
            if(lower > continente.get(i).dimension){
                lower = continente.get(i).dimension;
                name2 = continente.get(i).name;
            }
        }
        
        return "Ratio (Big/ Small)("+name1+"/ "+name2+"): "+(higher/lower);
    
    }
    
    
    
    public static void main(String[] args) {
        
        //a
        Ex3 AméricaDoSul = new Ex3("América do Sul");
        
        País Brasil = new País("BRA","Brasil",8516000);
        País Argentina = new País("ARG","Argentina",2780000);
        País Uruguay = new País("URY","Uruguay",176215);
        País Paraguay = new País("PRY","Paraguay",406752);
        Brasil.setPop(200000000);
        Argentina.setPop(40000000);
        Uruguay.setPop(3457000);
        Paraguay.setPop(6811000);
        
        
        //b
        AméricaDoSul.setContinente(Brasil);
        AméricaDoSul.setContinente(Argentina);
        AméricaDoSul.setContinente(Paraguay);
        AméricaDoSul.setContinente(Uruguay);
        
        //c
        System.out.println("Dimension ("+name+"): "+AméricaDoSul.dimension());
        
        //d
        System.out.println("Population ("+name+"): "+AméricaDoSul.population());
        
        //e
        System.out.println("Density ("+name+"): "+AméricaDoSul.density());
        
        //f
        System.out.println(AméricaDoSul.higherPop());
        
        //g
        System.out.println(AméricaDoSul.lowerPop());
        
        //h
        System.out.println(AméricaDoSul.higherDimension());
        
        //i
        System.out.println(AméricaDoSul.lowerDimension());
        
        //j    
        System.out.println(AméricaDoSul.ratioBigtoSmall());
    
    }
}
