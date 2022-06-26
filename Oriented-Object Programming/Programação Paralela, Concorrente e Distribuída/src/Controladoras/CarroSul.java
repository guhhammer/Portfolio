
package Controladoras;

import java.util.concurrent.Semaphore;

public class CarroSul extends Carro{

    public CarroSul(int ID,Semaphore mutex, Semaphore Cheguei, Semaphore PIR){
        super(ID,mutex, Cheguei, PIR);
    }
    
}
