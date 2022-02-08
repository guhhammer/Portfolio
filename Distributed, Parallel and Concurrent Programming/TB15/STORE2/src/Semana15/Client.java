package Semana15;

import java.rmi.Naming;
import java.util.Scanner;

    /*
    *
    *       CONSOLE DO CLIENTE.
    *
    */

public class Client {

    public static void printer(String s){ System.out.print(s); }

    public static void main(String[] args) {

        String IP_Servidor = "localhost:2522", nomeObjetoRemoto = "ClientConsole";

        try{

            IConsumer client = (IConsumer) Naming.lookup("rmi://"+IP_Servidor+"/"+nomeObjetoRemoto);
            printer("\n\nClient is connected.\n\n");

            String texto = "\n\nClient Console:\n"+
                           "\n\tOpção (1): Busca Por Produto."+
                           "\n\tOpção (2): Sair.\n\n";

            Scanner sc = new Scanner(System.in);
            Scanner busca_ = new Scanner(System.in);

            boolean on = true;
            while(on){

                printer(texto);
                printer("\nDigite a sua opção:\t");
                int choice = sc.nextInt();

                if(choice == 1){


                    printer("\nDigite a palavra da busca:\n");

                    String busca = busca_.nextLine().toString();

                    printer(client.buscaProduto(busca));

                    try{ Thread.sleep(2500); }catch (Exception e){ e.getSuppressed(); }

                }
                else{
                    printer("\n\nSaindo...\n\n");
                    try{ Thread.sleep(2000);}catch (Exception e){ e.getSuppressed(); }
                    on = false;
                }

            }

        }
        catch (Exception e){ e.getSuppressed(); }

    }


}
