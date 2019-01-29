package mx.tec.tc3039;

import java.util.LinkedList;
import java.util.Queue;

class Flipper {

    private final Queue<Corredor> fila = new LinkedList<Corredor>();
    
    public synchronized void add(Corredor corredor) {
        fila.offer(corredor);
    }
    
    public synchronized void flip() {
        fila.offer(fila.poll());
    }
    
    public synchronized Corredor turnoActual() {
        return fila.peek();
    }

}

public class Corredor implements Runnable {
    
    private final Flipper flipper;
    
    public Corredor(Flipper flipper) {
        this.flipper = flipper;
        flipper.add(this);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            synchronized (flipper) {
                while (flipper.turnoActual() != this) {
                    try {
                        flipper.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.printf("%s: %2d%n",
                        Thread.currentThread().getName(), i);
                flipper.flip();
                flipper.notifyAll();
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {
        var flipper = new Flipper();
        var c1 = new Corredor(flipper);
        var c2 = new Corredor(flipper);
        var t1 = new Thread(c1, "Thread(0)");
        var t2 = new Thread(c2, "Thread(1)");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
