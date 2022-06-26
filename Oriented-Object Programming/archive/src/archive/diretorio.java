
package archive;


import java.io.File;

import javax.swing.JOptionPane;

public class diretorio {
	diretorio() {
		tipoDocumentacao();
	}
	
	void tipoDocumentacao() {
		int cargo = Integer.parseInt(JOptionPane.showInputDialog(null, "Entre com:%n1 Diretor(a)%n2 Secretário(a)%n3 Portaria", "Indique o tipo de diretório!"));
		do { switch(cargo) {
		case 1:
			criacao("Diretor(a)");
			break;
		case 2:
			criacao("Secretário(a)");
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
			File past = new File(Archive.caminho+File.pathSeparator+tipo+File.pathSeparator+i);
			past.mkdir();
			Archive.diretorios.add(0, past.toString());
		}
	}
}
