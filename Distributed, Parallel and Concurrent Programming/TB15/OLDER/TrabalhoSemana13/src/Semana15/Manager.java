package Semana15;

import java.rmi.Naming;
import java.util.Scanner;

    /*
    *
    *       CONSOLE DO ADMINISTRADOR.
    *
    */

public class Manager {

    public static void printer(String s){ System.out.print(s); }

    public static void main(String[] args) {

        String IP_Servidor = "localhost:2522", nomeObjetoRemoto = "ManagerConsole";

        try{

            IManager manager = (IManager) Naming.lookup("rmi://"+IP_Servidor+"/"+nomeObjetoRemoto);
            printer("\n\nManager is connected.\n\n");

            Scanner sc = new Scanner(System.in);

            String texto = "Manager Console:\n"+
                           "\n\tOpção (1): Ver Histórico."+
                           "\n\tOpção (2): Ver TMAX."+
                           "\n\tOpção (3): Atualizar TMAX." +
                           "\n\tOpção (4): Sair.\n\n";

            boolean on = true;
            while (on){

                printer(texto);
                printer("\nDigite a sua opção:\t");
                int choice = sc.nextInt();

                if(choice == 1){

                    printer(manager.seeSearchHistory());
                    try{ Thread.sleep(2000);}catch (Exception e){ e.getSuppressed(); }

                }
                else if(choice == 2){

                    printer("\nTMAX: "+manager.verTMAX()+"\n\n\n");
                    try{ Thread.sleep(2000);}catch (Exception e){ e.getSuppressed(); }

                }
                else if(choice == 3){

                    printer("\nDigite novo valor para TMAX:\t");
                    manager.atualizarTMAX(sc.nextInt());
                    try{ Thread.sleep(2000);}catch (Exception e){ e.getSuppressed(); }

                }
                else{

                    printer("\n\nSaindo...\n\n");
                    try{ Thread.sleep(2000);}catch (Exception e){ e.getSuppressed(); }
                    on = false;

                }

            }

        }catch(Exception e){ e.getSuppressed(); }

    }


}
