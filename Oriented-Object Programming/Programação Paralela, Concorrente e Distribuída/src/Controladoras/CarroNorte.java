
package Controladoras;

import java.util.concurrent.Semaphore;

public class CarroNorte extends Carro{

    public CarroNorte(int ID, Semaphore mutex, Semaphore Cheguei, Semaphore PIR){
        super(ID, mutex, Cheguei, PIR);
    }
    
}
