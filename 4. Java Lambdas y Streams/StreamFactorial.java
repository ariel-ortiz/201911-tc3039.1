package mx.tec.tc3039;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class StreamFactorial {

    public static void main(String[] args) {
        final int n = 250_000;

        long inicio = System.nanoTime();
        BigInteger resultado = IntStream.rangeClosed(1, n)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);
        long fin = System.nanoTime();

        System.out.printf("Resultado = %d, %.2f%n",
                resultado.bitCount(), (fin - inicio) / 1.0E9);
    }
}
