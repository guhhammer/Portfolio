package arq;

public class reg {
	private String 	nome;
	private String 	sobrenome;
	private int		ramal;
	
	protected reg() {
		this("","",0);
	}
	private reg(String nome, String sobrenome, int ramal) {
		this.setNome(nome);
		this.setSobrenome(sobrenome);
		this.setRamal(ramal);
	}
	
	protected String getNome() {return this.nome;}
	protected String getSobrenome() {return this.sobrenome;}
	protected int	 getRamal() {return this.ramal;}
	protected void setNome(String nome) {this.nome = nome;}
	protected void setSobrenome(String sobrenome) {this.sobrenome = sobrenome;}
	protected void setRamal(int ramal) {this.ramal = ramal;}
}