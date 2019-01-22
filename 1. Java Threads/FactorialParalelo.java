package mx.tec.tc3039;

import java.math.BigInteger;

public class FactorialParalelo implements Runnable {

    private int inicio;
    private int fin;
    private BigInteger acumulado;
    
    public FactorialParalelo(int inicio, int fin) {
        this.inicio = inicio;
        this.fin = fin;
        this.acumulado = BigInteger.ONE;
    }

    @Override
    public void run() {
        for (int i = inicio; i <= fin; i++) {
            acumulado = acumulado.multiply(BigInteger.valueOf(i));
        }
    }
    
    public static BigInteger factorial(int n) {
        var f1 = new FactorialParalelo(1, n / 2);
        var f2 = new FactorialParalelo(n / 2 + 1, n);
        
        Thread t1 = new Thread(f1);
        Thread t2 = new Thread(f2);
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            // Nunca pasa
        }
        
        return f1.acumulado.multiply(f2.acumulado);
    }

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        BigInteger r = factorial(170_000);
        long termino = System.nanoTime();
        System.out.println(r.bitCount());
        System.out.printf("T2 = %.4f%n", (termino-inicio) / 1E9);
    }
}
