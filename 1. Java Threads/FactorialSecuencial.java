package mx.tec.tc3039;

import java.math.BigInteger;

public class FactorialSecuencial {
    
    public static BigInteger factorial(int n) {
        BigInteger resultado = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        BigInteger r = factorial(170_000);
        long termino = System.nanoTime();
        System.out.println(r.bitCount());
        System.out.printf("T1 = %.4f%n", (termino-inicio) / 1E9);
    }
}
