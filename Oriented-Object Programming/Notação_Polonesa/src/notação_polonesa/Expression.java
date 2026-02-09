
package notação_polonesa;

public abstract class Expression {
    
    private  double valor;
    public double[] valores;
    
    public String[] valoresString;
    
    //Construtor double:
    public Expression(double[] ind){
        this.valores = ind;
    }
    
    //Construtor Valor:
    public Expression(double v){
        this.valor = v;
    }
    
    
    //Construtor String:
    public Expression(String[] ind){
        this.valoresString = ind;
    }
    
    
    //tamanho vetor double
    public double tamanho(){return valores.length;}
    
    
    // getValor
    public double getValor(){return this.valor;}
    
    
    //AVALIAR: DOUBLE
    public double avaliar(){return 0.0;}
    
    //TOSTRING: STRING
    public String toString(){return "";}
   // public String[] toStringVetor(){return null;}
    public String toStringPolishNotation(){return "";}
    
    //TOSTRING: VARIABLES
    public String toStringChanger(double v){return v+"";}
    public String toStringChanger(double[] v){return v+"";}
    
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