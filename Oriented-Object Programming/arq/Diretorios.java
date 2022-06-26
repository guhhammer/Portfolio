package arq;

import java.io.File;

import javax.swing.JOptionPane;

public class Diretorios {
	Diretorios() {
		tipoDocumentacao();
	}
	
	void tipoDocumentacao() {
		int cargo = Integer.parseInt(JOptionPane.showInputDialog(null, "Entre com:%n1 Diretor(a)%n2 Secret�rio(a)%n3 Portaria", "Indique o tipo de diret�rio!"));
		do { switch(cargo) {
		case 1:
			criacao("Diretor(a)");
			break;
		case 2:
			criacao("Secret�rio(a)");
			break;
		case 3:
			criacao("Portaria");
			break;
			default:
				JOptionPane.showMessageDialog(null, "Por favor, entre com 1, 2 ou 3.", "Valor Incorreto!", JOptionPane.ERROR_MESSAGE);
		}}while(true);
	}
	
	void criacao(String tipo) {
		int qnt = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantos diretorios desse tipo criar?"));
		for(int i=0; i<qnt; i++) {
			File past = new File(Main.caminho+File.pathSeparator+tipo+File.pathSeparator+i);
			past.mkdir();
			Main.diretorios.add(0, past.toString());
		}
	}
}
