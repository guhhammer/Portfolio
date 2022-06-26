
package ra03aulaex02;

public class Division extends OperadorPrefixado{

    public Division(double e, double d){super(e, d);}
    public Division(String es, String ds){super(es, ds);}
    
    @Override
    public double avaliar(){return (super.ltS() * 1.0) / (super.rtS() * 1.0);}
    
    @Override
    public String toString(){return "( "+(super.ltS())+" / "+(super.rtS())+" )";}
       
}

