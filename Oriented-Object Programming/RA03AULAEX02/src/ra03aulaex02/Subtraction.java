
package ra03aulaex02;

public class Subtraction extends OperadorPrefixado{

    public Subtraction(double e, double d){super(e, d);}
    public Subtraction(String es, String ds){super(es, ds);}
    
    @Override
    public double avaliar(){return (super.ltS() * 1.0) - (super.rtS() * 1.0);}
    
    @Override
    public String toString(){return "( "+(super.ltS())+" - "+(super.rtS())+" )";}
    
}