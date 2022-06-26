package pooaula8;

public class POOaula8 {

    public static void main(String[] args) {
        
        Professor ed = new Professor("Ed", "Scar");
        Disciplina Poo = new Disciplina("Poo", ed);
        
        Poo.cadastra(new Aluno("Gustavo", "Hammer", 9,9,9,9));
        Poo.cadastra(new Aluno("André", "Wlodka", 9,9,9,8));
        Poo.cadastra(new Aluno("Vinicius", "Abáde", 8,7,6,8));
        
        System.out.println(Poo.getRelatorio());
        System.out.println("Média da Sala: "+Poo.mediaSala());
    }  
    
}