package arq;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class sist extends reg{
	private Formatter grava;
	private Scanner scan;
	sist() {
		String choice;
		do {
			choice = JOptionPane.showInputDialog(null, "Leitura(r) ou Gravação(w)?");
			if(choice.equalsIgnoreCase("w")) {openW();write();break;}
			else if(choice.equalsIgnoreCase("r")) {openR();read();break;}
			else JOptionPane.showMessageDialog(null, "Digitou Errado");
		}while(true);
		if(choice.equalsIgnoreCase("w"))close();
		else closeR();
	}
	private void openW() {
		try {
			grava = new Formatter(
					Main.diretorios.get(0)+File.pathSeparator
			+JOptionPane.showInputDialog(null, "De o nome do arquivo") + ".txt");
		} catch (HeadlessException | FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro, por favor, tente novamente!" + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
		}
	}
	private Path openR() {
		Path arq = Paths.get(
				 Main.diretorios.get(0)+
				JOptionPane.showInputDialog(null, "De o nome do arquivo") + ".txt");
		return arq;
	}
	private void write() {
		String option;
		label: do {
			this.setNome(JOptionPane.showInputDialog(null, "Nome: "));
			this.setSobrenome(JOptionPane.showInputDialog(null, "Sobrenome: "));
			this.setRamal(Integer.parseInt(JOptionPane.showInputDialog(null, "Num Ramal")));
			
			grava.format("%-20s%-20s%-20d%n", this.getNome(), this.getSobrenome(), this.getRamal());
			
			
			do {
				option = JOptionPane.showInputDialog(null, "Deseja mais um REGISTRO? (S/N)");
				if(option.equalsIgnoreCase("n")) break label;
				else if(option.equalsIgnoreCase("s")) continue label;
				else {JOptionPane.showMessageDialog(null, "Digitou Errado!!!");continue;}
			}while(true);
		}while(true);
	}
	private void read() {
		String option;
		Charset utf = StandardCharsets.UTF_8;
		label: do {
			Path arg = openR();
			try(BufferedReader arq = Files.newBufferedReader(arg,utf)) {
				String pessoa = arq.readLine();
				String[] dados = pessoa.split(" ");
				this.setNome(dados[0]);
				this.setSobrenome(dados[1]);
				this.setRamal(Integer.parseInt(dados[2]));
				
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro, por favor, tente novamente!" + e.getMessage(), "ERRO!", JOptionPane.ERROR_MESSAGE);
			}
			
			System.out.printf("%-20s%-20s%-20d", this.getNome(),this.getSobrenome(),this.getRamal());
			do {
				option = JOptionPane.showInputDialog(null, "Deseja mais um registro?");
				if(option.equalsIgnoreCase("n")) break label;
				else if(option.equalsIgnoreCase("n")) break;
				else {JOptionPane.showMessageDialog(null, "Digitou Errado!!!");continue;}
			}while(true);
		}while(true);
	}
	private void close() {
		if (!grava.toString().isEmpty()) {
			grava.close();
		}else JOptionPane.showMessageDialog(null, "Nada a ser fechado!", "ERRO!", JOptionPane.ERROR_MESSAGE);
	}
	private void closeR() {
		if(!scan.toString().isEmpty()) {
			scan.close();
		}else JOptionPane.showMessageDialog(null, "Nada a ser fechado!", "ERRO!", JOptionPane.ERROR_MESSAGE);
	}
}
