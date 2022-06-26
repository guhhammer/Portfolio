
package MonitorComCondição;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferLimitado {

    private int N;
    private int buffer[];
    private int front = 0;
    private int rear = 0;
    private int count = 0;

    final Lock mutex = new ReentrantLock();
    final Condition notFull = mutex.newCondition();
    final Condition notEmpty = mutex.newCondition();

    public BufferLimitado(int N) {
        this.N = N;
        buffer = new int[N];
    }

    public void depositar(int dado) throws InterruptedException {
        mutex.lock();
        try {
            while (count == N) {
                notFull.await();
            }
            buffer[rear] = dado;
            rear = (rear + 1) % N;
            count++;
            System.out.println("Produzido: " + dado + "[" + count + "]");
            notEmpty.signal();
        } finally {
            mutex.unlock();
        }
    }

    public int retirar() throws InterruptedException {
        mutex.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            int dado = buffer[front];
            front = (front + 1) % N;
            count--;
            System.out.println("Consumido: " + dado + "[" + count + "]");
            notFull.signal();
            return dado;
        } finally {
            mutex.unlock();
        }
    }

}
