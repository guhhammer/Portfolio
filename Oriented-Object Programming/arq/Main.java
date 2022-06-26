package arq;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Main {
	public static String caminho ;
	public static ArrayList<String> diretorios = new ArrayList<String>();
	public static void main(String[] args) {
		String choice;
		label: do {
			caminho = JOptionPane.showInputDialog(null, "Dar o endereço de criação de pasta:");
			new Diretorios();
			new sist();
			do {
				choice = JOptionPane.showInputDialog(null, "Deseja mais um Arquivo?(s/n)");
				if(choice.equalsIgnoreCase("n")) {
					JOptionPane.showMessageDialog(null, "Obrigado por Utilizar nosso SISTEMA!");
					break label;
				}else if(choice.equalsIgnoreCase("s"))break;
			}while(true);
		}while(true);
	}
}