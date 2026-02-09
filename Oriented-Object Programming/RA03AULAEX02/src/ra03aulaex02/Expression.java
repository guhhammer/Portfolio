
package ra03aulaex02;

public abstract class Expression {
    
    private  double ladoEsq;
    private  double ladoDir;
    private  double valor;
    
    private  String ladoEsqS;
    private  String ladoDirS;
    
    //Construtor double:
    public Expression(double e, double d){
        this.ladoEsq = e;
        this.ladoDir = d;
    }
    
    //Construtor Valor:
    public Expression(double v){
        this.valor = v;
    }
    
    //Construtor String:
    public Expression(String e, String d){
        this.ladoEsqS = e;
        this.ladoDirS = d;
    }
    
    // ltS => leftSide; rtS => rightSide
    public double ltS(){return this.ladoEsq;}
    public double rtS(){return this.ladoDir;}
    
    // ltSS => leftSide String; rtSS => rightSide String
    public String ltSS(){return this.ladoEsqS;}
    public String rtSS(){return this.ladoDirS;}
    
    // getValor
    public double getValor(){
        return this.valor;
    }
    
    
    //AVALIAR: DOUBLE
    public double avaliar(){return 0.0;}
    
    //TOSTRING: STRING
    public String toString(){return "";}
    
    //TOSTRING: VARIABLES
    public String toStringChanger(double v){return v+"";}
    
    //TOSTRING: OPERATORS
    public String toStringADD(String a,String b){
        return  "("+a+") + ("+b+")";}
    public String toStringSUB(String a, String b){
        return  "("+a+") - ("+b+")";}
    public String toStringMUL(String a, String b){
        return  "("+a+") * ("+b+")";}
    public String toStringDIV(String a,String b){
        return  "("+a+") / ("+b+")";}
}