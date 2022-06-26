
package Controladoras;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main{
    
    static Semaphore mutex = new Semaphore(1),
                     CS = new Semaphore(1),
                     CN = new Semaphore(0),
                     ChegueiNorte = new Semaphore(0),
                     PermissaoIrNorte = new Semaphore(0),
                     ChegueiSul = new Semaphore(0),
                     PermissaoIrSul = new Semaphore(0);

    public static int contador = 0;
    
    private static Random picker = new Random();
    private static int base = 20;
    private static int quantidadeCarrosN = base+ picker.nextInt(base*3);
    private static int quantidadeCarrosS = base+ picker.nextInt(base*3);
    public static int quantidadeNaPonte = 5;
    
    
    public static String CtrlAtual = "Sul";
    
    
    public static void main(String[] args) throws InterruptedException {
        
        ControladoraSul CtrlSul = new 
        ControladoraSul(mutex, ChegueiSul, PermissaoIrSul,CN,CS,contador);
        CtrlSul.start();
        
        for(int i = 0; i < quantidadeCarrosS; i++){

            CarroSul cs = new CarroSul(i, mutex, ChegueiSul, PermissaoIrSul);
            cs.start();
        }
        
        ControladoraNorte CtrlNorte = new 
        ControladoraNorte(mutex, ChegueiNorte, PermissaoIrNorte,CN,CS,contador);
        CtrlNorte.start();
        
        for(int i = 0; i < quantidadeCarrosN; i++){
            Thread.sleep(800);
            CarroNorte cn = new CarroNorte(i, mutex, ChegueiNorte,
                                                            PermissaoIrNorte);
            cn.start();
            Thread.sleep(800+picker.nextInt(6*base));
            
        }
        
        
    }
    
    
    
    
}
