
package ex2;
import java.util.ArrayList;


public class disciplina {
    
    ArrayList<professor> perProf;
    ArrayList<aluno> perAluno;
    
    private String nomeDIS;
    
    public disciplina(String nome){this.nomeDIS = nome;}
    
    public boolean cadastroP(professor p){return this.perProf.add(p);}
    public boolean cadastroA(aluno a){return this.perAluno.add(a);}
    
    public ArrayList<String> geraRelatorio(){
        ArrayList<String> L = new ArrayList(); //variavel local
        /*
        for(professor p: this.perProf;){
            L.add(p.toString());
        }*/
        
        return L;
    }
    
    
    public ArrayList<String> imprimeProf(){
        ArrayList<String> R = new ArrayList(); //variavel local
        for(professor f: this.perProf){
            R.add(f.toString());}
        return R;
    }
    
    public ArrayList<String> imprimeAluno(){
        ArrayList<String> P = new ArrayList();
        for(aluno g: this.perAluno){
            P.add(g.getHistorico());}
        return P;
    }
}