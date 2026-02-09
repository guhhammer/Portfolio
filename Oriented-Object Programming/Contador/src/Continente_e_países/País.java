
package Continente_e_países;

import java.util.ArrayList;

public class País {
    
   
    public String ISO;
    public String name;
    public double pop;
    public double dimension;
    ArrayList<País> neighbour = new ArrayList<>();
    

    //a -> 100%
    public País(String i, String n, double d){
        ISO = i;
        name = n;
        dimension = d;
    }
    
    
    //b  -> 100%
    public void setISO(String s){ISO = s;}
    public void setName(String n){name = n;}
    public void setPop(double p){pop = p;}
    public void setDimension(double d){dimension = d;}
    public void setNeighbour(País n){neighbour.add(n);}
    public String getISO(){return ISO;}
    public String getName(){return name;}
    public double getPop(){return pop;}
    public double getDimension(){return dimension;}
    public ArrayList<País> getNeighbours(){
        if(neighbour.isEmpty()){return null;}
        else{return neighbour;}
    }
   
    
    //c  -> 100%
    public void comparar(País n){
        if(getISO() == n.getISO()){System.out.println("São o mesmo país.");}
        else{System.out.println("Não são o mesmo país.");}
    }
    
    //d  -> 100%
    public void isNeighbour(País x){
        int c = 0;
        for(int i = 0; i < getNeighbours().size(); i++){
            if(x.getName() == getNeighbours().get(i).getName()){
                System.out.println(getName()+" é vizinho de "+x.getName());
                c = 1;
                break;
            }
        }
        if(c == 0){System.out.println(getName()+" não é vizinho de "+x.getName());}
        
        
    }
    
    
    //e  -> 100%
    public double densidadePop(){return getPop()/getDimension();}
    
    
    //f -> 100%
    public void neighbourhood(País x){
        ArrayList<País> re = new ArrayList<País>();
        for(int i = 0; i < neighbour.size(); i++){
            for(int j = 0; j < x.neighbour.size(); j++){
                if(neighbour.get(i) == x.neighbour.get(j)){
                    re.add(neighbour.get(i));
                }
            }
        }
        System.out.println("Países em comum entre "+getName()+" e "
                + ""+x.getName()+" :\n");
        
        System.out.println("Países ["+re.size()+"] = {");
        for(int i = 0; i < re.size(); i++){
            if(i == (re.size()-1)){
                System.out.println(re.get(i).getName()+"} .");
            }
            else{System.out.println(re.get(i).getName()+", ");}
        }
        
    }
   
    
    public static void main(String[] args) {
        //a
        País Brasil = new País("BRA","Brasil",8516000);
        País Argentina = new País("ARG","Argentina",2780000);
        País Uruguay = new País("URY","Uruguay",176215);
        País Paraguay = new País("PRY","Paraguay",406752);
        
        //a
        Brasil.setPop(200000000);
        Argentina.setPop(40000000);
        Uruguay.setPop(3457000);
        Paraguay.setPop(6811000);
        
        //b
        Brasil.setNeighbour(Argentina);
        Brasil.setNeighbour(Uruguay);
        Brasil.setNeighbour(Paraguay);
        
        //b
        Argentina.setNeighbour(Brasil);
        Argentina.setNeighbour(Uruguay);
        Argentina.setNeighbour(Paraguay);
        
        //c
        Brasil.comparar(Brasil);
        
        //d
        Brasil.isNeighbour(Argentina);
        
        //d
        System.out.println(Brasil.densidadePop());
        
        //f
        Brasil.neighbourhood(Argentina);
        
        
    }
    
    
}
