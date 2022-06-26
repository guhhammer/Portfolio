
package MonitorBarbeariaDiagrama;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static MonitorBarbeariaDiagrama.CorCliente.*;
import static MonitorBarbeariaDiagrama.Main.NUMCADEIRAS;
import java.util.Random;
        
public class Barbearia extends Thread{

    private final static int TMIN = 1000;
    private final static int TMAX = 4000;
    private final static int INTERVALO = TMAX - TMIN;
    private static Random periodo = new Random();
    
    private int numeroCadeiras = NUMCADEIRAS; 
    private int clientesLaranja = NUMCADEIRAS;
        
    private boolean clienteVermelho = false;
    
    private Lock gate_M = new ReentrantLock();
    private Condition gate_A = gate_M.newCondition(),
                      gate_B = gate_M.newCondition(),
                      gate_C = gate_M.newCondition(),
                      gate_D = gate_M.newCondition(),
                      barbeiroDisponivel = gate_M.newCondition(),
                      cadeiraOcupada = gate_M.newCondition();
    
    public Barbearia(int num){  this.numeroCadeiras = num; }   

    public void corte_cabelo(Cliente cliente) throws InterruptedException{
        
        gate_M.lock();
        
        try{
 
            String aux = String.format("Cliente adentra a Barberia:\n\t "
                                          +"ID: %s  |  Cor: %s.\n\t",
                                           Thread.currentThread().getName(),
                                           Cliente.getColor().toString());
            System.out.print(aux);
                 
            switch(cliente.getColor()){
                case VERDE:

                    cliente.setColor(CorCliente.AMARELO);
                    System.out.println("=> cliente vira AMARELO.\n");
                    break;

                case AMARELO:
                    if(clienteVermelho == false &&
                       clientesLaranja == numeroCadeiras ){

                        gate_A.signal();

                        cadeiraOcupada.await();
                        cliente.setColor(CorCliente.VERMELHO);
                        clienteVermelho = true;
                        System.out.println("=> cliente vira Vermelho "
                                  +"(gate_A: signal; gate_B: await).\n");

                        gate_B.await();

                    }
                    else if(clientesLaranja > 0){

                        cliente.setColor(CorCliente.LARANJA);
                        System.out.println("=> cliente vira LARANJA "
                                          +"(gate_C: await).\n");
                        clientesLaranja--;
                        gate_C.await();

                    }
                    else{

                        cliente.setColor(CorCliente.AZUL);
                        System.out.println("=> cliente vira AZUL.");

                    }

                    break;

                case VERMELHO:


                    cadeiraOcupada.signal();

                    clienteVermelho = false;

                    System.out.println("cliente vira VERDE (gate_D: signal)");

                    cliente.setColor(CorCliente.VERDE);

                    gate_D.signal();

                    break;

                case LARANJA:
                    if(clienteVermelho == false){

                        gate_A.signal();

                        cadeiraOcupada.await();
                        clientesLaranja++;
                        System.out.println("cliente vira VERMELHO "
                                  +"(gate_A: signal; gate_B: await");
                        cliente.setColor(CorCliente.VERMELHO);
                        clienteVermelho = true;

                        gate_B.await();

                    }

                    break;

                case AZUL:

                    cliente.setColor(CorCliente.AMARELO);
                    Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
                    System.out.println("cliente vira AMARELO");

                    break;

            }

        }catch(InterruptedException e){ e.printStackTrace();} 
        finally{ gate_M.unlock(); }
      
    }
      

    public void pegue_proximo_cliente() throws InterruptedException{
        
        gate_M.lock();
        try{
            
            switch(Barbeiro.getColor()){

                case ROSAESCURO:

                    gate_B.signal();

                    System.out.println("\nbarbeiro está terminando o corte...\n");
                    System.out.println("\nbarbeiro está cobrando o corte...\n");
                    termine_corte();

                    gate_D.await();

                    System.out.println("\nbarbeiro vira ROXO.\n");
                    Barbeiro.setColor(CorBarbeiro.ROXO);

                    break;

                case ROSACLARO:


                    System.out.println("\nbarbeiro esta cortando o cabelo...\n");

                    Thread.sleep(TMIN+periodo.nextInt(INTERVALO));

                    System.out.println("\nbarbeiro vira ROSAESCURO.\n");
                    Barbeiro.setColor(CorBarbeiro.ROSAESCURO);

                    break;

                case ROXO:

                    gate_C.signal();

                    System.out.println("\nbarbeiro está descansando agora...\n");

                    gate_A.await();

                    System.out.println("\nbarbeiro vira ROSACLARO.\n");
                    Barbeiro.setColor(CorBarbeiro.ROSACLARO);

                    break;

            }            
            
        } 
        finally{ gate_M.unlock(); }
        
    }
    
    public void termine_corte() throws InterruptedException{
        
        gate_M.lock();
        
        try{
            
            Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
            System.out.println("\nCorte Terminado!\n");
            
        } finally{ gate_M.unlock(); }
    
    }
    
    
}
